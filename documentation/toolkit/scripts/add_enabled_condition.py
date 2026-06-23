import os
import json
import argparse

_SKIP_DIRS = {'.git', 'node_modules', '__pycache__', 'venv', '.venv'}

_CONDITION_TYPE = "createfood:enabled"


def _createfood_path(s):
    if isinstance(s, str) and s.startswith("createfood:"):
        return s.split(":", 1)[1]
    return None


def _find_outputs(data):
    items = []
    result = data.get("result")
    if isinstance(result, dict):
        p = _createfood_path(result.get("item", ""))
        if p:
            items.append(p)
    elif isinstance(result, str):
        p = _createfood_path(result)
        if p:
            items.append(p)
    for r in data.get("results", []):
        if not isinstance(r, dict):
            continue
        for key in ("item", "fluid"):
            p = _createfood_path(r.get(key, ""))
            if p:
                items.append(p)
    return list(dict.fromkeys(items))


def _has_condition(data):
    return any(c.get("type") == _CONDITION_TYPE for c in data.get("conditions", []))


def process_file(path, dry_run=False):
    try:
        with open(path, 'r', encoding='utf-8-sig') as f:
            data = json.load(f)
    except Exception as e:
        print(f"❌ Error reading {path}: {e}")
        return 'error'

    if _has_condition(data):
        return 'skipped'

    items = _find_outputs(data)
    if not items:
        return 'no_output'

    if dry_run:
        print(f"[dry-run] Would add condition to: {path}")
        return 'modified'

    cond = (
        {"type": _CONDITION_TYPE, "id": items[0]}
        if len(items) == 1
        else {"type": _CONDITION_TYPE, "ids": items}
    )
    conditions = data.get("conditions", [])
    conditions.insert(0, cond)
    data["conditions"] = conditions

    ordered = {"conditions": data["conditions"]}
    for k, v in data.items():
        if k != "conditions":
            ordered[k] = v

    try:
        with open(path, 'w', encoding='utf-8-sig') as f:
            json.dump(ordered, f, indent=4)
            f.write('\n')
        print(f"✅ Updated: {path}")
        return 'modified'
    except Exception as e:
        print(f"❌ Error writing {path}: {e}")
        return 'error'


def dry_scan(directory):
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
                if not _has_condition(data) and _find_outputs(data):
                    rel = os.path.relpath(root, directory).replace(os.sep, '/')
                    counts[rel] = counts.get(rel, 0) + 1
            except Exception:
                pass
    return counts


def run(directory, dry_run=False, yes=False):
    if not os.path.isdir(directory):
        print(f"❌ Directory not found: {directory}")
        return

    abs_dir = os.path.abspath(directory)

    if not dry_run:
        counts = dry_scan(directory)
        total = sum(counts.values())
        print(f"This will add '{_CONDITION_TYPE}' conditions to {total} file(s):")
        for subdir, n in sorted(counts.items()):
            label = '(root)' if subdir == '.' else subdir + '/'
            print(f"  {label}  {n} file(s)")
        if not yes:
            answer = input("Continue? [y/N]: ").strip().lower()
            if answer != 'y':
                print("Aborted.")
                return

    modified = skipped = no_output = errors = 0

    print("\n--- Processing Files ---")
    for root, dirs, files in os.walk(directory):
        dirs[:] = [d for d in dirs if d not in _SKIP_DIRS]
        for fname in files:
            if not fname.endswith('.json'):
                continue
            result = process_file(os.path.join(root, fname), dry_run)
            if result == 'modified':
                modified += 1
            elif result == 'skipped':
                skipped += 1
            elif result == 'no_output':
                no_output += 1
            elif result == 'error':
                errors += 1

    print("\n--- Summary ---")
    if dry_run:
        print("(dry-run — no files were modified)")
    print(f"✅ Updated:                       {modified}")
    print(f"   Skipped (already had condition): {skipped}")
    print(f"   Skipped (no createfood output):  {no_output}")
    print(f"❌ Errors:                          {errors}")


def main():
    parser = argparse.ArgumentParser(
        description="Add createfood:enabled conditions to recipe JSON files."
    )
    parser.add_argument('directory', nargs='?',
                        help="Directory to process (default: current directory)")
    parser.add_argument('-n', '--dry-run', action='store_true',
                        help="Show what would change without modifying anything")
    parser.add_argument('-y', '--yes', action='store_true',
                        help="Skip confirmation prompt")
    args = parser.parse_args()

    directory = args.directory if args.directory is not None else '.'

    run(directory, dry_run=args.dry_run, yes=args.yes)


if __name__ == '__main__':
    main()
    input("\nPress Enter to exit...")
