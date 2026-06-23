import os
import json
import argparse

_SKIP_DIRS = {'.git', 'node_modules', '__pycache__', 'venv', '.venv'}


def make_condition(condition_type, modid):
    return {"type": condition_type, "modid": modid}


def already_has_condition(conditions, condition_type, modid):
    return any(
        c.get("type") == condition_type and c.get("modid") == modid
        for c in conditions
    )


def _get_item_ids(data):
    """Extracts all output item/fluid IDs from standard recipe formats."""
    items = []
    result = data.get("result")

    if isinstance(result, dict):
        items.append(result.get("item", ""))
        items.append(result.get("id", ""))
    elif isinstance(result, str):
        items.append(result)

    for r in data.get("results", []):
        if not isinstance(r, dict):
            continue
        for key in ("item", "id", "fluid"):
            if key in r:
                items.append(r.get(key, ""))

    return [i for i in items if isinstance(i, str) and i]


def matches_scope(data, modid):
    """Returns True if the recipe type or any result item matches the modid."""
    mod_prefix = f"{modid}:"

    recipe_type = data.get("type", "")
    if isinstance(recipe_type, str) and recipe_type.startswith(mod_prefix):
        return True

    for item_id in _get_item_ids(data):
        if item_id.startswith(mod_prefix):
            return True

    return False


def process_file(path, modid, condition_type, dry_run=False):
    try:
        with open(path, 'r', encoding='utf-8-sig') as f:
            data = json.load(f)
    except Exception as e:
        print(f"❌ Error reading {path}: {e}")
        return 'error'

    # Automatically enforce scope
    if not matches_scope(data, modid):
        return 'skipped_scope'

    conditions = data.get("conditions", [])
    if already_has_condition(conditions, condition_type, modid):
        return 'skipped'

    if dry_run:
        print(f"[dry-run] Would add condition to: {path}")
        return 'modified'

    conditions.insert(0, make_condition(condition_type, modid))
    data["conditions"] = conditions

    ordered = {"conditions": data["conditions"]}
    for k, v in data.items():
        if k != "conditions":
            ordered[k] = v

    try:
        with open(path, 'w', encoding='utf-8', newline='\n') as f:
            json.dump(ordered, f, indent=4)
            f.write('\n')
        print(f"✅ Updated: {path}")
        return 'modified'
    except Exception as e:
        print(f"❌ Error writing {path}: {e}")
        return 'error'


def dry_scan(directory, modid, condition_type):
    counts = {}  # relative dir path -> count
    for root, dirs, files in os.walk(directory):
        dirs[:] = [d for d in dirs if d not in _SKIP_DIRS]
        for fname in files:
            if not fname.endswith('.json'):
                continue
            path = os.path.join(root, fname)
            try:
                with open(path, 'r', encoding='utf-8-sig') as f:
                    data = json.load(f)

                if not matches_scope(data, modid):
                    continue

                if not already_has_condition(data.get("conditions", []), condition_type, modid):
                    rel = os.path.relpath(root, directory).replace(os.sep, '/')
                    counts[rel] = counts.get(rel, 0) + 1
            except Exception:
                pass
    return counts


def run(directory, modid, condition_type, dry_run=False, yes=False):
    if not os.path.isdir(directory):
        print(f"❌ Directory not found: {directory}")
        return

    abs_dir = os.path.abspath(directory)

    if not dry_run:
        counts = dry_scan(directory, modid, condition_type)
        total = sum(counts.values())
        print(f"This will add '{condition_type}' (modid={modid!r}) to {total} scoped file(s) in {abs_dir}:")
        for subdir, n in sorted(counts.items()):
            label = '(root)' if subdir == '.' else subdir + '/'
            print(f"  {label}  {n} file(s)")
        if not yes:
            answer = input("Continue? [y/N]: ").strip().lower()
            if answer != 'y':
                print("Aborted.")
                return

    modified = skipped = skipped_scope = errors = 0

    print("\n--- Processing Files ---")
    for root, dirs, files in os.walk(directory):
        dirs[:] = [d for d in dirs if d not in _SKIP_DIRS]
        for fname in files:
            if not fname.endswith('.json'):
                continue
            result = process_file(os.path.join(root, fname), modid, condition_type, dry_run)
            if result == 'modified':
                modified += 1
            elif result == 'skipped':
                skipped += 1
            elif result == 'skipped_scope':
                skipped_scope += 1
            elif result == 'error':
                errors += 1

    print("\n--- Summary ---")
    if dry_run:
        print("(dry-run — no files were modified)")
    print(f"✅ Updated:                       {modified}")
    print(f"   Skipped (already had condition): {skipped}")
    print(f"   Skipped (out of scope):          {skipped_scope}")
    print(f"❌ Errors:                          {errors}")


def main():
    parser = argparse.ArgumentParser(
        description="Add a mod-loaded condition to matching recipe JSON files."
    )
    parser.add_argument('directory', nargs='?', default='.',
                        help="Directory to process (default: current directory)")
    parser.add_argument('modid', nargs='?',
                        help="Mod ID to require (e.g. 'create', 'farmersdelight')")
    parser.add_argument('--type', dest='condition_type', default='forge:mod_loaded',
                        help="Condition type (default: forge:mod_loaded)")
    parser.add_argument('-n', '--dry-run', action='store_true',
                        help="Show what would change without modifying anything")
    parser.add_argument('-y', '--yes', action='store_true',
                        help="Skip confirmation prompt")
    args = parser.parse_args()

    directory = args.directory
    modid = args.modid

    # Smart parsing: if only one positional argument is given and it's not a directory,
    # assume it is the modid and default the directory to '.'
    if modid is None:
        if directory != '.' and not os.path.isdir(directory):
            modid = directory
            directory = '.'
        else:
            modid = input("Enter mod ID: ").strip()

    run(directory, modid, args.condition_type, dry_run=args.dry_run, yes=args.yes)


if __name__ == '__main__':
    main()
    input("\nPress Enter to exit...")