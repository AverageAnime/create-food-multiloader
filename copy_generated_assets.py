#!/usr/bin/env python3
"""
Copy pre-generated loot tables and block models from 1.21.1 to 1.20.1-forge.
"""
import subprocess
import os
import json

REPO = r"C:\Users\nkarm\Documents\modding\create-food-multiloader-1.21.1"
TARGET = os.path.join(REPO, "src", "main", "resources")

def git_show(path):
    """Get file content from 1.21.1 branch."""
    result = subprocess.run(
        ["git", "show", f"1.21.1:{path}"],
        cwd=REPO, capture_output=True
    )
    return result.stdout if result.returncode == 0 else None

def git_ls_tree(prefix):
    """List files in 1.21.1 branch matching prefix."""
    result = subprocess.run(
        ["git", "ls-tree", "-r", "1.21.1", "--name-only"],
        cwd=REPO, capture_output=True, text=True
    )
    return [f for f in result.stdout.splitlines() if f.startswith(prefix)]

# ── Loot tables ───────────────────────────────────────────────────────────────
print("Copying loot tables from 1.21.1...")
loot_src_prefix = "common/src/generated/resources/data/createfood/loot_table/"
loot_dst_base = os.path.join(TARGET, "data", "createfood", "loot_table")

loot_files = git_ls_tree(loot_src_prefix)
print(f"  Found {len(loot_files)} loot table files")

copied_loot = 0
skipped_loot = 0
for src_path in loot_files:
    # Map: common/src/generated/resources/data/createfood/loot_table/X -> data/createfood/loot_table/X
    rel = src_path[len(loot_src_prefix):]
    dst = os.path.join(loot_dst_base, rel.replace("/", os.sep))

    os.makedirs(os.path.dirname(dst), exist_ok=True)

    content = git_show(src_path)
    if content is None:
        print(f"  WARNING: Could not read {src_path}")
        continue

    with open(dst, "wb") as f:
        f.write(content)
    copied_loot += 1

print(f"  Copied {copied_loot} loot tables, skipped {skipped_loot}")

# ── Block models ──────────────────────────────────────────────────────────────
print("\nCopying block models from 1.21.1...")
model_src_prefix = "common/src/generated/resources/assets/createfood/models/block/"
model_dst_base = os.path.join(TARGET, "assets", "createfood", "models", "block")

model_files = git_ls_tree(model_src_prefix)
print(f"  Found {len(model_files)} block model files")

copied_models = 0
for src_path in model_files:
    rel = src_path[len(model_src_prefix):]
    dst = os.path.join(model_dst_base, rel.replace("/", os.sep))

    os.makedirs(os.path.dirname(dst), exist_ok=True)

    content = git_show(src_path)
    if content is None:
        print(f"  WARNING: Could not read {src_path}")
        continue

    with open(dst, "wb") as f:
        f.write(content)
    copied_models += 1

print(f"  Copied {copied_models} block models")

# ── Blockstates ───────────────────────────────────────────────────────────────
print("\nCopying blockstates from 1.21.1 generated resources...")
bs_src_prefix = "common/src/generated/resources/assets/createfood/blockstates/"
bs_dst_base = os.path.join(TARGET, "assets", "createfood", "blockstates")

bs_files = git_ls_tree(bs_src_prefix)
print(f"  Found {len(bs_files)} blockstate files")

copied_bs = 0
for src_path in bs_files:
    rel = src_path[len(bs_src_prefix):]
    dst = os.path.join(bs_dst_base, rel.replace("/", os.sep))

    # Don't overwrite existing hand-crafted blockstates (empty_plate, generic_display_plate)
    if os.path.exists(dst):
        skipped_loot += 1
        continue

    os.makedirs(os.path.dirname(dst), exist_ok=True)

    content = git_show(src_path)
    if content is None:
        continue

    with open(dst, "wb") as f:
        f.write(content)
    copied_bs += 1

print(f"  Copied {copied_bs} blockstates")

print(f"\nDone! Copied {copied_loot} loot tables, {copied_models} block models, {copied_bs} blockstates.")
