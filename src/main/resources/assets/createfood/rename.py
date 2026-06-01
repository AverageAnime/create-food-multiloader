import os
import json
import re

def replace_in_filename(filepath, search_string, replace_string):
    """Replaces the search string with the replace string in the filename."""
    root, filename = os.path.split(filepath)
    new_filename = re.sub(re.escape(search_string), replace_string, filename) # Escaped search_string for literal matching
    new_filepath = os.path.join(root, new_filename)
    
    if new_filepath != filepath: # Only rename if there's a change
        try:
            os.rename(filepath, new_filepath)
            print(f"✅ Renamed: {filename} -> {os.path.basename(new_filepath)}")
            return True
        except OSError as e:
            print(f"❌ Error renaming {filename}: {e}")
            return False
    return False # No change made

def replace_in_json(filepath, search_string, replace_string):
    """
    Replaces all occurrences of the search string with the replace string in the given JSON file.
    Handles both standard JSON objects and simpler key-value pair structures (like language files).
    """
    changes_made = False
    try:
        with open(filepath, 'r', encoding='utf-8') as f:
            content = f.read()

        # Use re.escape for literal search string matching in content
        updated_content, num_substitutions = re.subn(re.escape(search_string), replace_string, content)

        if num_substitutions > 0:
            with open(filepath, 'w', encoding='utf-8') as f:
                f.write(updated_content)
            changes_made = True
        
        if changes_made:
            print(f"✅ Updated content in: {filepath}")
        return changes_made

    except Exception as e:
        print(f"❌ Error processing content of {filepath}: {e}")
        return False

def run_replacement():
    """Gets the strings from the console and runs the replacement process."""
    search_string = input("Enter the string to search for: ")
    replace_string = input("Enter the string to replace with: ")

    renamed_files_count = 0
    updated_content_files_count = 0
    skipped_files_count = 0
    error_files_count = 0
    
    all_files_for_rename = []
    
    for root, _, files in os.walk('.'):
        for file in files:
            if file.endswith('.json') or file.endswith('.png'):
                all_files_for_rename.append(os.path.join(root, file))

    print("\n--- Renaming Files ---")
    files_to_process_content = [] # This will only contain JSON files
    for file_path in all_files_for_rename:
        original_filepath = file_path 
        try:
            if replace_in_filename(file_path, search_string, replace_string):
                renamed_files_count += 1
                # If renamed, add the NEW path to the list for content processing IF it's a JSON
                if original_filepath.endswith('.json'):
                    root, filename = os.path.split(file_path)
                    new_filename = re.sub(re.escape(search_string), replace_string, filename)
                    files_to_process_content.append(os.path.join(root, new_filename))
            else:
                skipped_files_count += 1 
                # If not renamed, still add original path for content processing IF it's a JSON
                if original_filepath.endswith('.json'):
                    files_to_process_content.append(original_filepath)
        except Exception as e:
            print(f"❌ Error during filename processing of {file_path}: {e}")
            error_files_count += 1
            skipped_files_count += 1 

    print("\n--- Updating JSON File Content ---")
    for file_path in files_to_process_content:
        # Only process content for JSON files
        if file_path.endswith('.json'):
            try:
                if replace_in_json(file_path, search_string, replace_string):
                    updated_content_files_count += 1
            except Exception as e:
                print(f"❌ Error during content processing of {file_path}: {e}")
                error_files_count += 1

    print("\n--- Replacement Summary ---")
    print(f"✅ Successfully renamed files (including .json and .png): {renamed_files_count}")
    print(f"✅ Successfully updated content in .json files: {updated_content_files_count}")
    print(f"   Skipped files (no filename changes or content changes): {skipped_files_count - renamed_files_count}") 
    print(f"❌ Files with errors: {error_files_count}")
    print("\nReplacement complete!")

if __name__ == "__main__":
    run_replacement()
    input("\nPress Enter to exit...")