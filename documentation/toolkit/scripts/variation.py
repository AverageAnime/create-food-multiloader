import os
import re
import argparse

_SKIP_DIRS = {'.git', 'node_modules', '__pycache__', 'venv', '.venv'}


def collect_files():
    result = []
    for root, dirs, files in os.walk('.'):
        dirs[:] = [d for d in dirs if d not in _SKIP_DIRS]
        for file in files:
            if file.endswith('.json'):
                result.append(os.path.join(root, file))
    return result


def dry_scan(all_files, pattern, replacements):
    counts = {}
    for replace_string in replacements:
        count = 0
        for fp in all_files:
            _, filename = os.path.split(fp)
            if pattern.search(filename):
                count += 1
        counts[replace_string] = count
    return counts


def create_variant(filepath, pattern, replace_string, dry_run=False):
    root, filename = os.path.split(filepath)
    new_filename = pattern.sub(replace_string, filename)

    if new_filename == filename:
        return 'skipped'

    new_filepath = os.path.join(root, new_filename)

    if os.path.exists(new_filepath):
        print(f"⚠️  Collision: {new_filename} already exists, skipping")
        return 'collision'

    if dry_run:
        print(f"[dry-run] Would create: {new_filename}")
        return 'created'

    try:
        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()
        updated_content = pattern.sub(replace_string, content)
        with open(new_filepath, 'w', encoding='utf-8') as f:
            f.write(updated_content)
        print(f"✅ Created: {new_filename}")
        return 'created'
    except Exception as e:
        print(f"❌ Error creating {new_filename}: {e}")
        return 'error'


def run_variations(search_string, replacements, dry_run=False, yes=False):
    if not search_string:
        print("❌ Search string cannot be empty.")
        return
    if not replacements:
        print("❌ Replacements list cannot be empty.")
        return

    pattern = re.compile(re.escape(search_string))
    all_files = collect_files()

    if not dry_run:
        counts = dry_scan(all_files, pattern, replacements)
        total = sum(counts.values())
        print(f"This will create {total} file(s) across {len(replacements)} variant(s):")
        for variant, count in counts.items():
            print(f"  {search_string!r} → {variant!r}: {count} file(s)")
        if not yes:
            answer = input("Continue? [y/N]: ").strip().lower()
            if answer != 'y':
                print("Aborted.")
                return

    created_count = 0
    skipped_count = 0
    collision_count = 0
    error_count = 0

    for replace_string in replacements:
        print(f"\n--- Variant: {replace_string!r} ---")
        for fp in all_files:
            status = create_variant(fp, pattern, replace_string, dry_run)
            if status == 'created':
                created_count += 1
            elif status == 'skipped':
                skipped_count += 1
            elif status == 'collision':
                collision_count += 1
            elif status == 'error':
                error_count += 1

    print("\n--- Summary ---")
    if dry_run:
        print("(dry-run — no files were modified)")
    print(f"✅ Created: {created_count}")
    print(f"   Skipped (no match in filename): {skipped_count}")
    print(f"⚠️  Skipped (collision): {collision_count}")
    print(f"❌ Errors: {error_count}")


def main():
    parser = argparse.ArgumentParser(
        description="Create file variants by replacing a search string with multiple alternatives."
    )
    parser.add_argument('search', nargs='?', help="String to search for in filenames and content")
    parser.add_argument('replacements', nargs='*', help="One or more replacement strings")
    parser.add_argument('--from-file', metavar='FILE',
                        help="Read replacement strings from a file (one per line)")
    parser.add_argument('-n', '--dry-run', action='store_true',
                        help="Show what would be created without modifying anything")
    parser.add_argument('-y', '--yes', action='store_true',
                        help="Skip confirmation prompt")
    args = parser.parse_args()

    if args.search is None:
        search_string = input("Enter the string to search for: ")
    else:
        search_string = args.search

    replacements = list(args.replacements)

    if args.from_file:
        try:
            with open(args.from_file, 'r', encoding='utf-8') as f:
                replacements.extend(line.strip() for line in f if line.strip())
        except Exception as e:
            print(f"❌ Could not read replacements file: {e}")
            return

    if not replacements:
        raw = input("Enter replacement strings (comma-separated): ")
        replacements = [r.strip() for r in raw.split(',') if r.strip()]

    run_variations(search_string, replacements, dry_run=args.dry_run, yes=args.yes)


if __name__ == "__main__":
    main()
    input("\nPress Enter to exit...")
