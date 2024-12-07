import os
import json
import re

def replace_in_filename(filepath, search_string, replace_string):
    """Replaces the search string with the replace string in the filename."""
    root, filename = os.path.split(filepath)
    new_filename = re.sub(search_string, replace_string, filename)
    new_filepath = os.path.join(root, new_filename)
    os.rename(filepath, new_filepath)

def replace_in_json(filepath, search_string, replace_string):
    """Replaces all occurrences of the search string with the replace string in the given JSON file."""
    with open(filepath, 'r') as f:
        try:
            file_content = json.load(f)
        except json.JSONDecodeError:
            print(f"Skipping file {filepath} due to invalid JSON format.")
            return

    content_str = json.dumps(file_content, indent=4)
    updated_content = re.sub(search_string, replace_string, content_str, count=0)

    with open(filepath, 'w') as f:
        f.write(updated_content)

def run_replacement():
    """Gets the strings from the console and runs the replacement process."""
    search_string = input("Enter the string to search for: ")
    replace_string = input("Enter the string to replace with: ")

    # 1. Gather all files
    all_files = []
    for root, _, files in os.walk('.'):
        for file in files:
            if file.endswith('.json'):
                all_files.append(os.path.join(root, file))

    # 2. Rename files
    for file in all_files:
        replace_in_filename(file, search_string, replace_string)

    # 3. Update file content (this needs to happen AFTER renaming)
    for root, _, files in os.walk('.'):
        for file in files:
            if file.endswith('.json'):
                replace_in_json(os.path.join(root, file), search_string, replace_string)

    print(f"Replaced '{search_string}' with '{replace_string}' in all .json filenames and file content in the directory and subfolders.")

if __name__ == "__main__":
    run_replacement()