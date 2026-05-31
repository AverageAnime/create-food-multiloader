"""
Transforms ModItems.java from CreateRegistrate to DeferredRegister<Item>.

Patterns handled:
  A) single-line no-props:
       ItemEntry<X> NAME = REGISTRATE.item("id", X::new).register();
     → RegistryObject<X> NAME = ITEMS.register("id", () -> new X(new Item.Properties()));

  B) single-line inline props (prop -> prop.CHAIN):
       ItemEntry<X> NAME = REGISTRATE.item("id", X::new).properties(prop -> prop.CHAIN).register();
     → RegistryObject<X> NAME = ITEMS.register("id", () -> new X(new Item.Properties().CHAIN));

  C) multi-line block props ({ prop.METHOD1(...); prop.METHOD2(...); return prop; }):
       ItemEntry<X> NAME = REGISTRATE.item("id", X::new)
           .properties(prop -> {
               prop.METHOD1(...);
               return prop;
           })
           .register();
     → RegistryObject<X> NAME = ITEMS.register("id", () -> new X(new Item.Properties()
               .METHOD1(...)));
"""

import re

SRC = r"src\main\java\net\averageanime\createfood\item\ModItems.java"

with open(SRC, encoding="utf-8") as f:
    lines = f.readlines()   # keep \n


# ---- helpers ----------------------------------------------------------------

def extract_block_statements(chunk_str):
    """
    Given a multi-line chunk, extract each 'prop.METHOD(ARGS);' line.
    Returns list of (method, args_string) pairs, preserving order.
    Ignores 'return prop;'.
    """
    results = []
    for line in chunk_str.split('\n'):
        stripped = line.strip()
        if not stripped.startswith('prop.'):
            continue
        if 'return' in stripped:
            continue
        if not stripped.endswith(';'):
            continue
        # strip leading 'prop.' and trailing ';'
        inner = stripped[len('prop.'):-1]
        paren_idx = inner.index('(')
        method = inner[:paren_idx]
        # everything after '(' up to matching ')'
        args_with_close = inner[paren_idx + 1:]
        # find matching close paren
        depth = 0
        args = args_with_close
        for ci, ch in enumerate(args_with_close):
            if ch == '(':
                depth += 1
            elif ch == ')':
                if depth == 0:
                    args = args_with_close[:ci]
                    break
                depth -= 1
        results.append((method, args))
    return results


def transform_chunk(chunk_str):
    """Transform one complete item declaration chunk."""
    header_re = re.match(
        r'\s*public static final ItemEntry<(\w+)> (\w+) = REGISTRATE\.item\("([^"]+)", (\w+)::new\)(.*)',
        chunk_str,
        re.DOTALL,
    )
    if not header_re:
        return chunk_str.rstrip('\n')

    item_type  = header_re.group(1)   # Item, BottleFoodItem, StickFoodItem, BowlFoodItem
    field_name = header_re.group(2)
    item_id    = header_re.group(3)
    ctor_class = header_re.group(4)
    indent     = "    "

    # Pattern C: multi-line block form
    if ".properties(prop -> {" in chunk_str:
        stmts = extract_block_statements(chunk_str)
        if stmts:
            chain_parts = "\n                ".join(
                f".{m}({a})" for m, a in stmts
            )
            return (
                f"{indent}public static final RegistryObject<{item_type}> {field_name} = "
                f"ITEMS.register(\"{item_id}\", () -> new {ctor_class}(new Item.Properties()\n"
                f"                {chain_parts}));"
            )
        # No prop.X statements found in block — fallthrough to empty properties
        return (
            f"{indent}public static final RegistryObject<{item_type}> {field_name} = "
            f"ITEMS.register(\"{item_id}\", () -> new {ctor_class}(new Item.Properties()));"
        )

    # Pattern B: single-line inline props
    if ".properties(prop -> " in chunk_str and ".register();" in chunk_str:
        marker = ".properties(prop -> "
        start = chunk_str.index(marker) + len(marker)
        end = chunk_str.rindex(").register();")
        chain = chunk_str[start:end]  # starts with "prop...."
        chain = chain.replace("prop.", "new Item.Properties().", 1)
        return (
            f"{indent}public static final RegistryObject<{item_type}> {field_name} = "
            f"ITEMS.register(\"{item_id}\", () -> new {ctor_class}({chain}));"
        )

    # Pattern A: no properties
    return (
        f"{indent}public static final RegistryObject<{item_type}> {field_name} = "
        f"ITEMS.register(\"{item_id}\", () -> new {ctor_class}(new Item.Properties()));"
    )


# ---- imports to remove / add -----------------------------------------------

REMOVE_LINES_CONTAINING = [
    "import com.simibubi.create.foundation.data.CreateRegistrate;",
    "import com.tterrag.registrate.util.entry.ItemEntry;",
    "import vectorwing.farmersdelight.common.item.ConsumableItem;",
    # duplicate imports the original already had
    "import net.minecraft.world.food.FoodProperties;\n",   # second occurrence
    "import net.minecraft.world.item.Item;\n",             # standalone (covered by *)
]

DEFERRED_IMPORT      = "import net.minecraftforge.registries.DeferredRegister;"
REGISTRY_OBJ_IMPORT  = "import net.minecraftforge.registries.RegistryObject;\n"

# ---- main pass --------------------------------------------------------------

output = []
i = 0
n = len(lines)
registry_object_added = False
# Track how many times we've seen the duplicate imports
seen_food_import = 0
seen_item_import = 0

while i < n:
    line = lines[i]
    stripped = line.strip()

    # Remove the private REGISTRATE field (single line)
    if "private static final CreateRegistrate REGISTRATE = CreateFood.registrate();" in line:
        i += 1
        continue

    # Remove specific imports
    if "import com.simibubi.create.foundation.data.CreateRegistrate;" in line:
        i += 1
        continue
    if "import com.tterrag.registrate.util.entry.ItemEntry;" in line:
        i += 1
        continue
    if "import vectorwing.farmersdelight.common.item.ConsumableItem;" in line:
        i += 1
        continue

    # The file has duplicate FoodProperties and Item imports — keep first, skip second
    if line.strip() == "import net.minecraft.world.food.FoodProperties;":
        seen_food_import += 1
        if seen_food_import > 1:
            i += 1
            continue
    if line.strip() == "import net.minecraft.world.item.Item;":
        seen_item_import += 1
        if seen_item_import > 1:
            i += 1
            continue

    # Add RegistryObject import right after DeferredRegister import
    if DEFERRED_IMPORT in line and not registry_object_added:
        output.append(line)
        output.append(REGISTRY_OBJ_IMPORT)
        registry_object_added = True
        i += 1
        continue

    # Detect start of an item entry declaration
    if "public static final ItemEntry<" in line and "REGISTRATE.item(" in line:
        # Determine single-line vs multi-line.
        # Single-line: the current line itself ends with '.register();'
        if line.rstrip().endswith(".register();"):
            # Single-line declaration
            transformed = transform_chunk(line)
            output.append(transformed + "\n")
        else:
            # Multi-line: collect until a line ends with '.register();'
            chunk_lines = [line]
            while not chunk_lines[-1].rstrip().endswith(".register();"):
                i += 1
                chunk_lines.append(lines[i])
            chunk_str = "".join(chunk_lines)
            transformed = transform_chunk(chunk_str)
            output.append(transformed + "\n")
        i += 1
        continue

    output.append(line)
    i += 1

result = "".join(output)

with open(SRC, "w", encoding="utf-8") as f:
    f.write(result)

print("Done.")
