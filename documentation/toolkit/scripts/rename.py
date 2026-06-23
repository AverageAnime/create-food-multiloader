import os
import json
import re
import argparse

_SKIP_DIRS = {'.git', 'node_modules', '__pycache__', 'venv', '.venv'}


def _make_pattern(search_string, ignore_case):
    flags = re.IGNORECASE if ignore_case else 0
    return re.compile(re.escape(search_string), flags)


def replace_in_filename(filepath, pattern, replace_string, dry_run=False):
    """
    Returns (new_filepath, status) where status is 'renamed', 'skipped',
    'collision', or 'error'. On dry_run, no filesystem changes are made.
    """
    root, filename = os.path.split(filepath)
    new_filename = pattern.sub(replace_string, filename)
    new_filepath = os.path.join(root, new_filename)

    if new_filepath == filepath:
        return filepath, 'skipped'

    if os.path.exists(new_filepath):
        print(f"⚠️  Collision: {filename} → {new_filename} already exists, skipping")
        return filepath, 'collision'

    if dry_run:
        print(f"[dry-run] Would rename: {filename} → {new_filename}")
        return new_filepath, 'renamed'

    try:
        os.rename(filepath, new_filepath)
        print(f"✅ Renamed: {filename} → {new_filename}")
        return new_filepath, 'renamed'
    except OSError as e:
        print(f"❌ Error renaming {filename}: {e}")
        return filepath, 'error'


def replace_in_json(filepath, pattern, replace_string, dry_run=False):
    """
    Returns True if content was (or would be) changed.
    Warns if the result is no longer valid JSON.
    """
    try:
        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()

        updated_content, num_substitutions = pattern.subn(replace_string, content)

        if num_substitutions == 0:
            return False

        if dry_run:
            print(f"[dry-run] Would update content in: {filepath}")
            return True

        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(updated_content)

        try:
            json.loads(updated_content)
        except json.JSONDecodeError:
            print(f"⚠️  Warning: {filepath} may no longer be valid JSON after replacement")

        print(f"✅ Updated content in: {filepath}")
        return True

    except Exception as e:
        print(f"❌ Error processing content of {filepath}: {e}")
        return False


def collect_files():
    """Walk cwd, skipping junk dirs, returning all .json and .png paths."""
    result = []
    for root, dirs, files in os.walk('.'):
        dirs[:] = [d for d in dirs if d not in _SKIP_DIRS]
        for file in files:
            if file.endswith('.json') or file.endswith('.png'):
                result.append(os.path.join(root, file))
    return result


def dry_scan(all_files, pattern, replace_string):
    """Count how many files would be renamed / have content updated."""
    rename_count = 0
    content_count = 0
    for fp in all_files:
        _, filename = os.path.split(fp)
        if pattern.search(filename):
            rename_count += 1
        if fp.endswith('.json'):
            try:
                with open(fp, 'r', encoding='utf-8') as f:
                    content = f.read()
                if pattern.search(content):
                    content_count += 1
            except Exception:
                pass
    return rename_count, content_count


def run_replacement(search_string, replace_string, dry_run=False, yes=False, ignore_case=False):
    if not search_string:
        print("❌ Search string cannot be empty.")
        return

    pattern = _make_pattern(search_string, ignore_case)
    all_files = collect_files()

    if not dry_run:
        rename_count, content_count = dry_scan(all_files, pattern, replace_string)
        print(f"This will rename {rename_count} file(s) and update content in {content_count} JSON file(s).")
        if not yes:
            answer = input("Continue? [y/N]: ").strip().lower()
            if answer != 'y':
                print("Aborted.")
                return

    renamed_count = 0
    collision_count = 0
    skipped_count = 0
    error_count = 0
    content_updated_count = 0

    print("\n--- Renaming Files ---")
    files_for_content = []
    for fp in all_files:
        new_fp, status = replace_in_filename(fp, pattern, replace_string, dry_run)
        if status == 'renamed':
            renamed_count += 1
            if fp.endswith('.json'):
                files_for_content.append(new_fp)
        elif status == 'skipped':
            skipped_count += 1
            if fp.endswith('.json'):
                files_for_content.append(fp)
        elif status == 'collision':
            collision_count += 1
        elif status == 'error':
            error_count += 1

    print("\n--- Updating JSON File Content ---")
    for fp in files_for_content:
        if replace_in_json(fp, pattern, replace_string, dry_run):
            content_updated_count += 1

    print("\n--- Summary ---")
    if dry_run:
        print("(dry-run — no files were modified)")
    print(f"✅ Renamed files: {renamed_count}")
    print(f"✅ JSON files with content updated: {content_updated_count}")
    print(f"   Skipped (no changes): {skipped_count}")
    print(f"⚠️  Skipped (collision): {collision_count}")
    print(f"❌ Errors: {error_count}")


def main():
    parser = argparse.ArgumentParser(
        description="Batch rename files and replace text inside JSON files."
    )
    parser.add_argument('search', nargs='?', help="String to search for")
    parser.add_argument('replace', nargs='?', help="String to replace with")
    parser.add_argument('-n', '--dry-run', action='store_true',
                        help="Show what would change without modifying anything")
    parser.add_argument('-y', '--yes', action='store_true',
                        help="Skip confirmation prompt")
    parser.add_argument('-i', '--ignore-case', action='store_true',
                        help="Case-insensitive matching")
    args = parser.parse_args()

    if args.search is None:
        search_string = input("Enter the string to search for: ")
        replace_string = input("Enter the string to replace with: ")
    else:
        search_string = args.search
        replace_string = args.replace if args.replace is not None else ''

    run_replacement(
        search_string,
        replace_string,
        dry_run=args.dry_run,
        yes=args.yes,
        ignore_case=args.ignore_case,
    )


if __name__ == "__main__":
    main()
    input("\nPress Enter to exit...")
