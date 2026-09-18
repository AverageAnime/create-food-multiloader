package dev.averageanime.forge.datagen.provider;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.forge.block.BlockRegistration;
import dev.averageanime.forge.block.FluidRegistration;
import dev.averageanime.forge.block.type.fluid.FluidBlock;
import dev.averageanime.forge.item.ItemRegistration;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ItemTagProvider extends TagsProvider<Item> {

    /**
     * Every {@code tag|entry} pair already appended, so a repeat is dropped instead of emitting a
     * duplicate line into the generated JSON.
     *
     * <p>Two things make duplicates easy to produce here: {@link #addTags} walks {@code ITEMS} and
     * then {@code BLOCKS}, and every block's BlockItem is registered in both — and several of the
     * hardcoded lines below re-add an id the automatic loops (or the {@code _bottle} suffix rule)
     * already covered. Deduping at the append site fixes both causes at once and keeps future
     * hardcoded lines from reintroducing the problem.
     */
    private final Set<String> emitted = new HashSet<>();

    public ItemTagProvider(PackOutput output,
                           CompletableFuture<HolderLookup.Provider> lookupProvider,
                           ExistingFileHelper existingFileHelper) {
        super(output, Registries.ITEM, lookupProvider, CreateFoodCommon.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        ItemRegistration.ITEMS.getEntries().forEach(holder -> {
            String id = holder.getId().getPath();
            t(cTag(id)).addOptional(new ResourceLocation(CreateFoodCommon.MOD_ID, id));
            if (id.endsWith("_bottle")) {
                String baseId = id.substring(0, id.length() - "_bottle".length());
                t(cTag(baseId)).addOptional(new ResourceLocation(CreateFoodCommon.MOD_ID, id));
            }
        });

        BlockRegistration.BLOCKS.getEntries().forEach(holder -> {
            String id = holder.getId().getPath();
            t(cTag(id)).addOptional(new ResourceLocation(CreateFoodCommon.MOD_ID, id));
        });

        for (Field field : FluidRegistration.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            try {
                Object value = field.get(null);
                if (value instanceof FluidBlock.FluidType fluidType) {
                    RegistryObject<?> bucket = fluidType.BUCKET;
                    String id = bucket.getId().getPath();
                    t(cTag(id)).addOptional(new ResourceLocation(CreateFoodCommon.MOD_ID, id));
                }
            } catch (IllegalAccessException ignored) {}
        }

        t(cTag("apple")).addOptional(new ResourceLocation("minecraft:apple")).addOptional(new ResourceLocation("createfood:apple_slice"));
        t(cTag("apple_jam")).addOptional(new ResourceLocation("hearthandharvest:apple_jam"));
        t(cTag("apple_jam_bottle")).addOptional(new ResourceLocation("bakery:apple_jam")).addOptional(new ResourceLocation("fruitsdelight:apple_jam"));
        t(cTag("apple_juice_bottle")).addOptional(new ResourceLocation("expandeddelight:apple_juice"));
        t(cTag("apple_juice_bottle_compat")).addOptional(new ResourceLocation("createfood:apple_juice_bottle"));
        t(cTag("apple_slice")).addOptional(new ResourceLocation("create_deepfried:apple_slices")).addOptional(new ResourceLocation("ratatouille_fried_delights:apple_slices"));
        t(cTag("asparagus")).addOptional(new ResourceLocation("expandeddelight:asparagus"));
        t(cTag("bacon_sandwich")).addOptional(new ResourceLocation("delightfulcreators:incomplete_bacon_sandwich"));
        t(cTag("bar_of_chocolate")).addOptional(new ResourceLocation("create:bar_of_chocolate")).addOptional(new ResourceLocation("candlelight:chocolate")).addOptional(new ResourceLocation("hearthandharvest:chocolate_bar"));
        t(cTag("bar_of_dark_chocolate")).addOptional(new ResourceLocation("create_confectionery:bar_of_black_chocolate"));
        t(cTag("bar_of_white_chocolate")).addOptional(new ResourceLocation("create_confectionery:bar_of_white_chocolate"));
        t(cTag("batter_bowl")).addOptional(new ResourceLocation("hearthandharvest:batter")).addOptional(new ResourceLocation("rusticdelight:batter"));
        t(cTag("beef_meatball_stick")).addOptional(new ResourceLocation("createfood:beef_meatball_stick_1")).addOptional(new ResourceLocation("createfood:beef_meatball_stick_2")).addOptional(new ResourceLocation("createfood:beef_meatball_stick_3"));
        t(cTag("beetroot")).addOptional(new ResourceLocation("minecraft:beetroot")).addOptional(new ResourceLocation("createfood:sliced_beetroot")).addOptional(new ResourceLocation("createfood:shredded_beetroot"));
        t(cTag("bell_pepper")).addOptionalTag(cTag("crops/bell_pepper")).addOptionalTag(cTag("crops/bellpepper")).addOptional(new ResourceLocation("rusticdelight:bell_pepper_slice_black")).addOptional(new ResourceLocation("rusticdelight:bell_pepper_slice_blue")).addOptional(new ResourceLocation("rusticdelight:bell_pepper_slice_green")).addOptional(new ResourceLocation("rusticdelight:bell_pepper_slice_orange")).addOptional(new ResourceLocation("rusticdelight:bell_pepper_slice_pink")).addOptional(new ResourceLocation("rusticdelight:bell_pepper_slice_purple")).addOptional(new ResourceLocation("rusticdelight:bell_pepper_slice_red")).addOptional(new ResourceLocation("rusticdelight:bell_pepper_slice_white")).addOptional(new ResourceLocation("rusticdelight:bell_pepper_slice_yellow")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_black")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_blue")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_green")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_orange")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_pink")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_purple")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_red")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_white")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_yellow")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_slice_black")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_slice_blue")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_slice_green")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_slice_orange")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_slice_pink")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_slice_purple")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_slice_red")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_slice_white")).addOptional(new ResourceLocation("rusticdelight:roasted_bell_pepper_slice_yellow")).addOptional(new ResourceLocation("veggiesdelight:bellpepper")).addOptional(new ResourceLocation("veggiesdelight:smoked_bellpepper"));
        t(cTag("berry_jam")).addOptional(new ResourceLocation("hearthandharvest:sweet_berry_jam"));
        t(cTag("berry_jam_bottle")).addOptional(new ResourceLocation("bakery:sweetberry_jam")).addOptional(new ResourceLocation("expandeddelight:sweet_berry_jelly")).addOptional(new ResourceLocation("fruitsdelight:sweetberry_jam"));
        t(cTag("berry_jam_bottle_compat")).addOptional(new ResourceLocation("createfood:berry_jam_bottle"));
        t(cTag("berry_juice_bottle")).addOptional(new ResourceLocation("expandeddelight:sweet_berry_juice")).addOptional(new ResourceLocation("hearthandharvest:sweet_berry_juice"));
        t(cTag("berry_juice_bottle_compat")).addOptional(new ResourceLocation("createfood:berry_juice_bottle"));
        t(cTag("berry_milkshake_bottle")).addOptional(new ResourceLocation("beachparty:sweetberry_milkshake")).addOptional(new ResourceLocation("create_dd:strawberry_milkshake"));
        t(cTag("berry_milkshake_bucket")).addOptional(new ResourceLocation("create_dd:strawberry_milkshake_bucket"));
        t(cTag("avocado")).addOptionalTag(modTag("culturaldelights", "avocados"));
        t(cTag("bowl")).addOptional(new ResourceLocation("minecraft:bowl"));
        t(cTag("bread")).addOptional(new ResourceLocation("minecraft:bread")).addOptionalTag(cTag("foods/bread"));
        t(cTag("bread_crumbs")).addOptional(new ResourceLocation("ratatouille_fried_delights:breadcrumb"));
        t(cTag("bread_fried_egg")).addOptional(new ResourceLocation("delightfulcreators:incomplete_egg_sandwich"));
        t(cTag("bread_slice")).addOptional(new ResourceLocation("moredelight:bread_slice"));
        t(cTag("broccoli")).addOptional(new ResourceLocation("veggiesdelight:broccoli"));
        t(cTag("brown_mushroom")).addOptional(new ResourceLocation("minecraft:brown_mushroom")).addOptional(new ResourceLocation("createfood:sliced_brown_mushroom"));
        t(cTag("brown_sugar")).addOptional(new ResourceLocation("ubesdelight:brown_sugar"));
        t(cTag("bun")).addOptional(new ResourceLocation("bakery:bun")).addOptional(new ResourceLocation("ratatouille_fried_delights:burger_bun")).addOptional(new ResourceLocation("ratatouille_fried_delights:top_burger_bun")).addOptional(new ResourceLocation("ratatouille_fried_delights:bottom_burger_bun"));
        t(cTag("butter")).addOptional(new ResourceLocation("croptopia:butter")).addOptional(new ResourceLocation("ratatouille_fried_delights:butter")).addOptional(new ResourceLocation("hearthandharvest:butter"));
        t(cTag("butter_compat")).addOptional(new ResourceLocation("createfood:butter"));
        t(cTag("cacao_butter")).addOptional(new ResourceLocation("create_confectionery:cocoa_butter")).addOptional(new ResourceLocation("ratatouille:cocoa_butter"));
        t(cTag("cacao_mass_bucket")).addOptional(new ResourceLocation("ratatouille:cocoa_liquor_bucket"));
        t(cTag("cacao_nibs")).addOptional(new ResourceLocation("ratatouille:dried_cocoa_nibs"));
        t(cTag("cake_base")).addOptional(new ResourceLocation("createadditions:cake_base_baked")).addOptional(new ResourceLocation("bakery:blank_cake")).addOptional(new ResourceLocation("ratatouille:cake_base"));
        t(cTag("cake_batter_bucket")).addOptional(new ResourceLocation("ratatouille:cake_batter_bucket"));
        t(cTag("calamari")).addOptionalTag(cTag("foods/raw_calamari")).addOptionalTag(cTag("foods/cooked_calamari")).addOptional(new ResourceLocation("rusticdelight:calamari")).addOptional(new ResourceLocation("rusticdelight:calamari_slice")).addOptional(new ResourceLocation("rusticdelight:cooked_calamari")).addOptional(new ResourceLocation("rusticdelight:cooked_calamari_slice")).addOptional(new ResourceLocation("culturaldelights:raw_calamari")).addOptional(new ResourceLocation("culturaldelights:cooked_calamari"));
        t(cTag("cane_syrup")).addOptional(new ResourceLocation("rusticdelight:syrup"));
        t(cTag("cane_syrup_bottle")).addOptional(new ResourceLocation("rusticdelight:syrup"));
        t(cTag("caramel")).addOptional(new ResourceLocation("hearthandharvest:caramel"));
        t(cTag("caramel_berries")).addOptional(new ResourceLocation("create_confectionery:caramel_glazed_berries"));
        t(cTag("caramel_bucket")).addOptional(new ResourceLocation("create_dd:caramel_bucket")).addOptional(new ResourceLocation("create_confectionery:caramel_bucket"));
        t(cTag("carrot")).addOptional(new ResourceLocation("createfood:sliced_carrot")).addOptional(new ResourceLocation("minecraft:carrot")).addOptional(new ResourceLocation("createfood:shredded_carrot"));
        t(cTag("cauliflower")).addOptional(new ResourceLocation("veggiesdelight:cauliflower")).addOptional(new ResourceLocation("veggiesdelight:cauliflower_floret")).addOptional(new ResourceLocation("veggiesdelight:roasted_cauliflower_floret"));
        t(cTag("cheese_block")).addOptional(new ResourceLocation("meadow:cheese_block")).addOptional(new ResourceLocation("casualnessdelight:cheese_wheel")).addOptional(new ResourceLocation("hearthandharvest:cheddar_cheese_wheel")).addOptional(new ResourceLocation("hearthandharvest:goat_cheese_wheel")).addOptional(new ResourceLocation("ratatouille_fried_delights:cheese")).addOptional(new ResourceLocation("expandeddelight:cheese_wheel")).addOptional(new ResourceLocation("expandeddelight:goat_cheese_wheel"));
        t(cTag("cheese_sandwich")).addOptional(new ResourceLocation("expandeddelight:cheese_sandwich"));
        t(cTag("cheese_slice")).addOptional(new ResourceLocation("meadow:piece_of_cheese")).addOptional(new ResourceLocation("casualnessdelight:cheese_wheel_slice")).addOptional(new ResourceLocation("hearthandharvest:cheddar_cheese_slice")).addOptional(new ResourceLocation("hearthandharvest:goat_cheese_slice")).addOptional(new ResourceLocation("ratatouille_fried_delights:cheese_slice")).addOptional(new ResourceLocation("expandeddelight:goat_cheese_slice"));
        t(cTag("cheeses")).addOptional(new ResourceLocation("createfood:cheese_slice")).addOptional(new ResourceLocation("brewinandchewin:flaxen_cheese_wedge")).addOptional(new ResourceLocation("expandeddelight:cheese_slice")).addOptional(new ResourceLocation("meadow:piece_of_cheese")).addOptional(new ResourceLocation("casualnessdelight:cheese_wheel_slice")).addOptional(new ResourceLocation("create_bic_bit:unripe_cheese_wedge")).addOptional(new ResourceLocation("create_bic_bit:young_cheese_wedge")).addOptional(new ResourceLocation("create_bic_bit:aged_cheese_wedge")).addOptional(new ResourceLocation("hearthandharvest:cheddar_cheese_slice")).addOptional(new ResourceLocation("hearthandharvest:goat_cheese_slice")).addOptional(new ResourceLocation("ratatouille_fried_delights:cheese")).addOptional(new ResourceLocation("expandeddelight:goat_cheese_slice"));
        t(cTag("chicken_nuggets")).addOptional(new ResourceLocation("create_deepfried:chicken_nuggets")).addOptional(new ResourceLocation("ratatouille_fried_delights:chicken_nuggets"));
        t(cTag("chorus_fruit_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:chorus_jam"));
        t(cTag("garlic")).addOptional(new ResourceLocation("veggiesdelight:garlic")).addOptional(new ResourceLocation("veggiesdelight:garlic_clove")).addOptional(new ResourceLocation("veggiesdelight:roasted_garlic_clove"));
        t(cTag("grilled_cheese_sandwich")).addOptional(new ResourceLocation("expandeddelight:grilled_cheese"));
        t(cTag("ice_cream_bucket")).addOptional(new ResourceLocation("ratatouille_fried_delights:ice_cream_base_bucket"));
        t(cTag("melon_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:melon_jam"));
        t(cTag("pepper")).addOptionalTag(cTag("bell_pepper")).addOptional(new ResourceLocation("expandeddelight:chili_pepper")).addOptional(new ResourceLocation("croptopia:chile_pepper"));
        t(cTag("chocolate_cake_base"));
        t(cTag("chocolate_chip_chocolate_cookie")).addOptional(new ResourceLocation("expandeddelight:chocolate_chip_chocolate_cookie"));
        t(cTag("chocolate_milk_bottle")).addOptional(new ResourceLocation("hearthandharvest:chocolate_milk_bottle"));
        t(cTag("chocolate_milkshake_bottle")).addOptional(new ResourceLocation("beachparty:chocolate_milkshake")).addOptional(new ResourceLocation("create_dd:chocolate_milkshake"));
        t(cTag("chocolate_milkshake_bucket")).addOptional(new ResourceLocation("create_dd:chocolate_milkshake_bucket"));
        t(cTag("chocolate_sweet_dough")).addOptional(new ResourceLocation("createfood:chocolate_sugar_dough"));
        t(cTag("chocolate_toast")).addOptional(new ResourceLocation("moredelight:chocolate_toast"));
        t(cTag("chorus_cookie")).addOptional(new ResourceLocation("ends_delight:chorus_cookie"));
        t(cTag("chorus_fruit")).addOptional(new ResourceLocation("minecraft:chorus_fruit")).addOptional(new ResourceLocation("createfood:chorus_fruit_slice"));
        t(cTag("chorus_fruit_juice_bottle")).addOptional(new ResourceLocation("endersdelight:chorus_juice"));
        t(cTag("cinnamon_sweet_roll")).addOptional(new ResourceLocation("expandeddelight:sweet_roll"));
        t(cTag("cinnamon_sweet_roll_berry")).addOptional(new ResourceLocation("expandeddelight:berry_sweet_roll"));
        t(cTag("cinnamon_sweet_roll_glow_berry")).addOptional(new ResourceLocation("expandeddelight:glow_berry_sweet_roll"));
        t(cTag("cocoa_powder")).addOptional(new ResourceLocation("create_confectionery:cocoa_powder")).addOptional(new ResourceLocation("ratatouille:cocoa_powder"));
        t(cTag("coffee_beans")).addOptional(new ResourceLocation("rusticdelight:coffee_beans"));
        t(cTag("condensed_milk_bottle")).addOptional(new ResourceLocation("ubesdelight:condensed_milk_bottle"));
        t(cTag("condensed_milk_bucket")).addOptional(new ResourceLocation("create_dd:condense_milk_bucket"));
        t(cTag("container")).addOptional(new ResourceLocation("minecraft:bucket")).addOptional(new ResourceLocation("minecraft:stick"));
        t(cTag("cooked_bacon")).addOptional(new ResourceLocation("farmersdelight:cooked_bacon"));
        t(cTag("cooked_beef")).addOptional(new ResourceLocation("minecraft:cooked_beef")).addOptional(new ResourceLocation("farmersdelight:beef_patty")).addOptional(new ResourceLocation("vegandelight:tofu_patty")).addOptional(new ResourceLocation("ratatouille_fried_delights:hamburger_patty"));
        t(cTag("cooked_chicken")).addOptional(new ResourceLocation("farmersdelight:cooked_chicken_cuts")).addOptional(new ResourceLocation("minecraft:cooked_chicken"));
        t(cTag("cooked_corn")).addOptional(new ResourceLocation("culturaldelights:smoked_corn"));
        t(cTag("cooked_eggplant")).addOptionalTag(modTag("culturaldelights", "smoked_regular_eggplants")).addOptional(new ResourceLocation("culturaldelights:smoked_white_eggplant"));
        t(cTag("cooked_eggs")).addOptional(new ResourceLocation("farmersdelight:fried_egg"));
        t(cTag("cooked_fishes")).addOptional(new ResourceLocation("minecraft:cooked_salmon")).addOptional(new ResourceLocation("farmersdelight:cooked_salmon_slice")).addOptional(new ResourceLocation("minecraft:cooked_cod")).addOptional(new ResourceLocation("farmersdelight:cooked_cod_slice")).addOptional(new ResourceLocation("createfood:cooked_tropical_fish")).addOptional(new ResourceLocation("createfood:cooked_tropical_fish_slice"));
        t(cTag("cooked_mutton")).addOptional(new ResourceLocation("minecraft:cooked_mutton")).addOptional(new ResourceLocation("farmersdelight:cooked_mutton_chops"));
        t(cTag("cooked_pasta")).addOptional(new ResourceLocation("createfood:pasta"));
        t(cTag("cooked_pork")).addOptional(new ResourceLocation("croptopia:cooked_bacon")).addOptional(new ResourceLocation("farmersdelight:cooked_bacon")).addOptional(new ResourceLocation("minecraft:cooked_porkchop")).addOptional(new ResourceLocation("createfood:bacon_bits"));
        t(cTag("cooked_rabbit")).addOptional(new ResourceLocation("minecraft:cooked_rabbit")).addOptional(new ResourceLocation("createfood:cooked_rabbit_cuts")).addOptional(new ResourceLocation("createfood:rabbit_jerky"));
        t(cTag("cooked_rice")).addOptional(new ResourceLocation("farmersdelight:cooked_rice"));
        t(cTag("corn")).addOptional(new ResourceLocation("culturaldelights:corn_cob")).addOptional(new ResourceLocation("hearthandharvest:corn"));
        t(cTag("corn_dough")).addOptional(new ResourceLocation("culturaldelights:corn_dough"));
        t(cTag("cucumber")).addOptionalTag(modTag("culturaldelights", "cucumbers"));
        t(cTag("eggplant")).addOptionalTag(modTag("culturaldelights", "regular_eggplants"));
        t(cTag("corn_flour")).addOptional(new ResourceLocation("hearthandharvest:corn_meal"));
        t(cTag("corn_kernel")).addOptional(new ResourceLocation("culturaldelights:corn_kernels")).addOptional(new ResourceLocation("hearthandharvest:corn_kernels"));
        t(cTag("corn_kernels")).addOptionalTag(cTag("corn_kernel"));
        t(cTag("cotton_candy_stick")).addOptional(new ResourceLocation("hearthandharvest:cotton_candy"));
        t(cTag("cream_donut")).addOptional(new ResourceLocation("create_snt:sweet_donut")).addOptional(new ResourceLocation("ratatouille_fried_delights:creamy_donut"));
        t(cTag("crimson_fungus")).addOptional(new ResourceLocation("minecraft:crimson_fungus")).addOptional(new ResourceLocation("createfood:sliced_crimson_fungus"));
        t(cTag("crushable_cookies")).addOptional(new ResourceLocation("minecraft:cookie")).addOptional(new ResourceLocation("createfood:butterscotch_chip_cookie")).addOptional(new ResourceLocation("createfood:caramel_chip_cookie")).addOptional(new ResourceLocation("createfood:dark_chocolate_chip_cookie")).addOptional(new ResourceLocation("createfood:toffee_chip_cookie")).addOptional(new ResourceLocation("createfood:white_chocolate_chip_cookie")).addOptional(new ResourceLocation("farmersdelight:honey_cookie"));
        t(cTag("donut_base")).addOptional(new ResourceLocation("create_snt:donut")).addOptional(new ResourceLocation("ratatouille_fried_delights:fried_donut"));
        t(cTag("dough")).addOptional(new ResourceLocation("farmersdelight:wheat_dough"));
        t(cTag("dried_coffee_beans")).addOptional(new ResourceLocation("rusticdelight:roasted_coffee_beans"));
        t(cTag("dried_coffee_beans_compat")).addOptional(new ResourceLocation("createfood:dried_coffee_beans"));
        t(cTag("dumpling_ingredients")).addOptionalTag(cTag("foods/raw_pork")).addOptionalTag(cTag("foods/raw_chicken")).addOptionalTag(cTag("foods/raw_beef")).addOptionalTag(cTag("foods/raw_mutton")).addOptionalTag(cTag("mushrooms"));
        t(cTag("dumplings")).addOptional(new ResourceLocation("brewery:dumplings")).addOptional(new ResourceLocation("farmersdelight:dumplings")).addOptional(new ResourceLocation("delightfulcreators:incomplete_dumplings"));
        t(cTag("egg_burrito_ingredients")).addOptional(new ResourceLocation("createfood:boiled_egg_peeled")).addOptional(new ResourceLocation("farmersdelight:fried_egg"));
        t(cTag("egg_yolk")).addOptional(new ResourceLocation("ratatouille:egg_yolk"));
        t(cTag("eggplant_burger")).addOptional(new ResourceLocation("culturalcreators:incomplete_eggplant_burger"));
        t(cTag("eggs")).addOptional(new ResourceLocation("createfood:egg_powder"));
        t(cTag("eggshell")).addOptional(new ResourceLocation("ratatouille:egg_shell"));
        t(cTag("endermite_meatball_stick")).addOptional(new ResourceLocation("createfood:endermite_meatball_stick_1")).addOptional(new ResourceLocation("createfood:endermite_meatball_stick_2")).addOptional(new ResourceLocation("createfood:endermite_meatball_stick_3"));
        t(cTag("flesh_cookie")).addOptional(new ResourceLocation("frightsdelight:cookie_flesh"));
        t(cTag("flours/wheat")).addOptional(new ResourceLocation("farm_and_charm:flour")).addOptional(new ResourceLocation("hearthandharvest:flour")).addOptional(new ResourceLocation("kaleidoscope_cookery:flour"));
        t(cTag("food_plates")).addOptional(new ResourceLocation("displaydelight:food_plate")).addOptional(new ResourceLocation("displaydelight:small_food_plate"));
        t(cTag("foods/cooked_fish")).addOptional(new ResourceLocation("createfood:cooked_tropical_fish")).addOptional(new ResourceLocation("createfood:cooked_tropical_fish_slice"));
        t(cTag("foods/cooked_meats/cooked_rabbit")).addOptional(new ResourceLocation("minecraft:cooked_rabbit")).addOptional(new ResourceLocation("createfood:cooked_rabbit_cuts"));
        t(cTag("foods/cooked_tropical_fish")).addOptional(new ResourceLocation("createfood:cooked_tropical_fish")).addOptional(new ResourceLocation("createfood:cooked_tropical_fish_slice"));
        t(cTag("foods/doughs")).addOptional(new ResourceLocation("create:dough"));
        t(cTag("foods/raw_bacon")).addOptional(new ResourceLocation("farmersdelight:bacon"));
        t(cTag("foods/raw_beef")).addOptional(new ResourceLocation("createfood:ground_beef"));
        t(cTag("foods/raw_chicken")).addOptional(new ResourceLocation("createfood:ground_chicken"));
        t(cTag("foods/raw_fish")).addOptional(new ResourceLocation("createfood:tropical_fish_slice"));
        t(cTag("foods/raw_meats/ground")).addOptional(new ResourceLocation("createfood:ground_beef")).addOptional(new ResourceLocation("createfood:ground_chicken")).addOptional(new ResourceLocation("createfood:ground_mutton")).addOptional(new ResourceLocation("createfood:ground_pork")).addOptional(new ResourceLocation("createfood:ground_rabbit")).addOptional(new ResourceLocation("ratatouille:mince_meat"));
        t(cTag("foods/raw_meats/raw_bacon")).addOptional(new ResourceLocation("farmersdelight:bacon"));
        t(cTag("foods/raw_meats/raw_beef")).addOptional(new ResourceLocation("createfood:ground_beef"));
        t(cTag("foods/raw_meats/raw_chicken")).addOptional(new ResourceLocation("createfood:ground_chicken"));
        t(cTag("foods/raw_meats/raw_mutton")).addOptional(new ResourceLocation("createfood:ground_mutton"));
        t(cTag("foods/raw_meats/raw_pork")).addOptional(new ResourceLocation("createfood:ground_pork"));
        t(cTag("foods/raw_meats/raw_rabbit")).addOptional(new ResourceLocation("minecraft:rabbit")).addOptional(new ResourceLocation("createfood:rabbit_cuts")).addOptional(new ResourceLocation("createfood:ground_rabbit"));
        t(cTag("foods/raw_mutton")).addOptional(new ResourceLocation("createfood:ground_mutton"));
        t(cTag("foods/raw_pork")).addOptional(new ResourceLocation("createfood:ground_pork"));
        t(cTag("foods/raw_rabbit")).addOptional(new ResourceLocation("minecraft:rabbit")).addOptional(new ResourceLocation("createfood:rabbit_cuts")).addOptional(new ResourceLocation("createfood:ground_rabbit"));
        t(cTag("foods/raw_tropical_fish")).addOptional(new ResourceLocation("minecraft:tropical_fish")).addOptional(new ResourceLocation("createfood:tropical_fish_slice"));
        t(cTag("foods/safe_raw_fish")).addOptional(new ResourceLocation("createfood:tropical_fish_slice"));
        t(cTag("frosting_ingredients")).addOptionalTag(cTag("cream_cheese")).addOptionalTag(cTag("butter"));
        t(cTag("fruits")).addOptionalTag(cTag("apple")).addOptionalTag(cTag("melon")).addOptionalTag(cTag("chorus_fruit")).addOptionalTag(cTag("foods/berries")).addOptional(new ResourceLocation("hearthandharvest:blueberries")).addOptional(new ResourceLocation("hearthandharvest:raspberry")).addOptional(new ResourceLocation("hearthandharvest:red_grapes")).addOptional(new ResourceLocation("hearthandharvest:green_grapes")).addOptional(new ResourceLocation("hearthandharvest:cherry")).addOptional(new ResourceLocation("fruitsdelight:bayberry")).addOptional(new ResourceLocation("fruitsdelight:blueberry")).addOptional(new ResourceLocation("fruitsdelight:cranberry")).addOptional(new ResourceLocation("fruitsdelight:durian_flesh")).addOptional(new ResourceLocation("fruitsdelight:fig")).addOptional(new ResourceLocation("fruitsdelight:hamimelon")).addOptional(new ResourceLocation("fruitsdelight:hamimelon_slice")).addOptional(new ResourceLocation("fruitsdelight:hawberry")).addOptional(new ResourceLocation("fruitsdelight:kiwi")).addOptional(new ResourceLocation("fruitsdelight:lemon")).addOptional(new ResourceLocation("fruitsdelight:lemon_slice")).addOptional(new ResourceLocation("fruitsdelight:lychee")).addOptional(new ResourceLocation("fruitsdelight:mango")).addOptional(new ResourceLocation("fruitsdelight:mangosteen")).addOptional(new ResourceLocation("fruitsdelight:orange")).addOptional(new ResourceLocation("fruitsdelight:orange_slice")).addOptional(new ResourceLocation("fruitsdelight:peach")).addOptional(new ResourceLocation("fruitsdelight:pear")).addOptional(new ResourceLocation("fruitsdelight:persimmon")).addOptional(new ResourceLocation("fruitsdelight:pineapple")).addOptional(new ResourceLocation("fruitsdelight:pineapple_slice"));
        t(cTag("gelatin_dessert_block")).addOptional(new ResourceLocation("createfood:gelatin_dessert_block")).addOptional(new ResourceLocation("createfood:black_gelatin_dessert_block")).addOptional(new ResourceLocation("createfood:blue_gelatin_dessert_block")).addOptional(new ResourceLocation("createfood:brown_gelatin_dessert_block")).addOptional(new ResourceLocation("createfood:cyan_gelatin_dessert_block")).addOptional(new ResourceLocation("createfood:gray_gelatin_dessert_block")).addOptional(new ResourceLocation("createfood:green_gelatin_dessert_block")).addOptional(new ResourceLocation("createfood:light_gray_gelatin_dessert_block")).addOptional(new ResourceLocation("createfood:lime_gelatin_dessert_block")).addOptional(new ResourceLocation("createfood:magenta_gelatin_dessert_block")).addOptional(new ResourceLocation("createfood:orange_gelatin_dessert_block")).addOptional(new ResourceLocation("createfood:pink_gelatin_dessert_block")).addOptional(new ResourceLocation("createfood:purple_gelatin_dessert_block")).addOptional(new ResourceLocation("createfood:red_gelatin_dessert_block")).addOptional(new ResourceLocation("createfood:yellow_gelatin_dessert_block"));
        t(cTag("gelatin_dessert_slice")).addOptional(new ResourceLocation("createfood:gelatin_dessert_slice")).addOptional(new ResourceLocation("createfood:black_gelatin_dessert_slice")).addOptional(new ResourceLocation("createfood:blue_gelatin_dessert_slice")).addOptional(new ResourceLocation("createfood:brown_gelatin_dessert_slice")).addOptional(new ResourceLocation("createfood:cyan_gelatin_dessert_slice")).addOptional(new ResourceLocation("createfood:gray_gelatin_dessert_slice")).addOptional(new ResourceLocation("createfood:green_gelatin_dessert_slice")).addOptional(new ResourceLocation("createfood:light_gray_gelatin_dessert_slice")).addOptional(new ResourceLocation("createfood:lime_gelatin_dessert_slice")).addOptional(new ResourceLocation("createfood:magenta_gelatin_dessert_slice")).addOptional(new ResourceLocation("createfood:orange_gelatin_dessert_slice")).addOptional(new ResourceLocation("createfood:pink_gelatin_dessert_slice")).addOptional(new ResourceLocation("createfood:purple_gelatin_dessert_slice")).addOptional(new ResourceLocation("createfood:red_gelatin_dessert_slice")).addOptional(new ResourceLocation("createfood:yellow_gelatin_dessert_slice"));
        t(cTag("gelatin_mix_bucket")).addOptional(new ResourceLocation("createfood:gelatin_mix_bucket")).addOptional(new ResourceLocation("createfood:black_gelatin_mix_bucket")).addOptional(new ResourceLocation("createfood:blue_gelatin_mix_bucket")).addOptional(new ResourceLocation("createfood:brown_gelatin_mix_bucket")).addOptional(new ResourceLocation("createfood:cyan_gelatin_mix_bucket")).addOptional(new ResourceLocation("createfood:gray_gelatin_mix_bucket")).addOptional(new ResourceLocation("createfood:green_gelatin_mix_bucket")).addOptional(new ResourceLocation("createfood:light_gray_gelatin_mix_bucket")).addOptional(new ResourceLocation("createfood:lime_gelatin_mix_bucket")).addOptional(new ResourceLocation("createfood:magenta_gelatin_mix_bucket")).addOptional(new ResourceLocation("createfood:orange_gelatin_mix_bucket")).addOptional(new ResourceLocation("createfood:pink_gelatin_mix_bucket")).addOptional(new ResourceLocation("createfood:purple_gelatin_mix_bucket")).addOptional(new ResourceLocation("createfood:red_gelatin_mix_bucket")).addOptional(new ResourceLocation("createfood:yellow_gelatin_mix_bucket"));
        t(cTag("ginger_cookie")).addOptional(new ResourceLocation("ubesdelight:cookie_ginger"));
        t(cTag("glow_berry_jam")).addOptional(new ResourceLocation("hearthandharvest:glow_berry_jam"));
        t(cTag("glow_berry_jam_bottle")).addOptional(new ResourceLocation("bakery:glowberry_jam")).addOptional(new ResourceLocation("expandeddelight:glow_berry_jelly")).addOptional(new ResourceLocation("fruitsdelight:glowberry_jam"));
        t(cTag("glow_berry_jam_bottle_compat")).addOptional(new ResourceLocation("createfood:glow_berry_jam_bottle"));
        t(cTag("glow_berry_juice_bottle")).addOptional(new ResourceLocation("expandeddelight:glow_berry_juice")).addOptional(new ResourceLocation("hearthandharvest:glow_berry_juice"));
        t(cTag("glow_berry_juice_bottle_compat")).addOptional(new ResourceLocation("createfood:glow_berry_juice_bottle"));
        t(cTag("glow_berry_milkshake_bottle")).addOptional(new ResourceLocation("createfood:glow_berry_milkshake_bottle")).addOptional(new ResourceLocation("create_dd:glow_berry_milkshake"));
        t(cTag("glow_berry_milkshake_bucket")).addOptional(new ResourceLocation("createfood:glow_berry_milkshake_bucket")).addOptional(new ResourceLocation("create_dd:glow_berry_milkshake_bucket"));
        t(cTag("green_tea_cookie")).addOptional(new ResourceLocation("farmersrespite:green_tea_cookie"));
        t(cTag("ground_strider")).addOptional(new ResourceLocation("nethersdelight:ground_strider")).addOptional(new ResourceLocation("mynethersdelight:minced_strider"));
        t(cTag("gyro_meat_ingredients")).addOptionalTag(cTag("foods/raw_meats/raw_beef")).addOptionalTag(cTag("foods/raw_meats/raw_pork")).addOptionalTag(cTag("foods/raw_meats/raw_chicken")).addOptionalTag(cTag("foods/raw_meats/raw_rabbit"));
        t(cTag("gyro_mutton_ingredient")).addOptionalTag(cTag("foods/cooked_mutton")).addOptional(new ResourceLocation("createfood:gyro_meat_slice"));
        t(cTag("hamburger")).addOptional(new ResourceLocation("createfood:hamburger")).addOptional(new ResourceLocation("delightfulcreators:incomplete_hamburger"));
        t(cTag("hamburger_onion_lettuce_tomato")).addOptional(new ResourceLocation("farmersdelight:hamburger"));
        t(cTag("hidden_from_recipe_viewers")).addOptional(new ResourceLocation("createfood:icon"));
        t(cTag("honey_cookie")).addOptional(new ResourceLocation("farmersdelight:honey_cookie"));
        t(cTag("honeyed_apple")).addOptional(new ResourceLocation("create:honeyed_apple"));
        t(cTag("honeyed_donut")).addOptional(new ResourceLocation("createfood:honeyed_donut")).addOptional(new ResourceLocation("create_snt:honey_donut"));
        t(cTag("hot_chocolate")).addOptional(new ResourceLocation("createfood:hot_chocolate_bottle"));
        t(cTag("hot_chocolate_bottle")).addOptional(new ResourceLocation("createfood:hot_chocolate_bottle")).addOptional(new ResourceLocation("create_dd:hot_chocolate")).addOptional(new ResourceLocation("create_confectionery:hot_chocolate_bottle"));
        t(cTag("hot_chocolate_bucket")).addOptional(new ResourceLocation("createfood:hot_chocolate_bucket")).addOptional(new ResourceLocation("create_dd:hot_chocolate_bucket")).addOptional(new ResourceLocation("create_confectionery:hot_chocolate_bucket"));
        t(cTag("hot_dark_chocolate")).addOptional(new ResourceLocation("createfood:hot_dark_chocolate_bottle"));
        t(cTag("hot_white_chocolate")).addOptional(new ResourceLocation("createfood:hot_white_chocolate_bottle"));
        t(cTag("ice_blocks")).addOptional(new ResourceLocation("minecraft:ice")).addOptional(new ResourceLocation("minecraft:packed_ice")).addOptional(new ResourceLocation("minecraft:blue_ice"));
        t(cTag("ice_cream_cone")).addOptional(new ResourceLocation("ratatouille_fried_delights:vanilla_cone"));
        t(cTag("macaroni_bowl_cheese")).addOptional(new ResourceLocation("hearthandharvest:macaroni_and_cheese"));
        t(cTag("magma_gelatin")).addOptional(new ResourceLocation("nethersdelight:magma_gelatin")).addOptional(new ResourceLocation("mynethersdelight:hot_cream"));
        t(cTag("marshmallow_dark_chocolate_fudge")).addOptional(new ResourceLocation("createfood:marshmallow_chocolate_fudge"));
        t(cTag("marshmallow_stick")).addOptional(new ResourceLocation("hearthandharvest:marshmallow_stick"));
        t(cTag("marshmallow_white_chocolate_fudge")).addOptional(new ResourceLocation("createfood:marshmallow_chocolate_fudge"));
        t(cTag("mashed_potatoes_bowl")).addOptional(new ResourceLocation("hearthandharvest:mashed_potatoes"));
        t(cTag("melon")).addOptional(new ResourceLocation("minecraft:melon")).addOptional(new ResourceLocation("minecraft:melon_slice"));
        t(cTag("melon_cream_frosting")).addOptional(new ResourceLocation("createfood:melon_cream_frosting_bottle"));
        t(cTag("melon_jam")).addOptional(new ResourceLocation("hearthandharvest:melon_jam"));
        t(cTag("milk")).addOptional(new ResourceLocation("minecraft:milk_bucket"));
        t(cTag("milk_bottle")).addOptional(new ResourceLocation("farmersdelight:milk_bottle")).addOptional(new ResourceLocation("hearthandharvest:goat_milk_bottle")).addOptional(new ResourceLocation("expandeddelight:goat_milk_bottle"));
        t(cTag("milk_buckets")).addOptional(new ResourceLocation("meadow:wooden_milk_bucket")).addOptional(new ResourceLocation("createfood:milk_powder")).addOptional(new ResourceLocation("ubesdelight:milk_powder"));
        t(cTag("milk_powder")).addOptional(new ResourceLocation("createfood:milk_powder")).addOptional(new ResourceLocation("ubesdelight:milk_powder"));
        t(cTag("milk_powder_compat")).addOptional(new ResourceLocation("createfood:milk_powder"));
        t(cTag("milks")).addOptional(new ResourceLocation("createfood:milk_powder")).addOptional(new ResourceLocation("ubesdelight:milk_powder"));
        t(cTag("milkshake")).addOptional(new ResourceLocation("createfood:milkshake_bottle"));
        t(cTag("milkshake_bottle")).addOptional(new ResourceLocation("createfood:milkshake_bottle")).addOptional(new ResourceLocation("create_dd:vanilla_milkshake"));
        t(cTag("milkshake_bucket")).addOptional(new ResourceLocation("createfood:milkshake_bucket")).addOptional(new ResourceLocation("create_dd:vanilla_milkshake_bucket"));
        t(cTag("minced_beef")).addOptional(new ResourceLocation("farmersdelight:minced_beef")).addOptional(new ResourceLocation("farm_and_charm:minced_beef"));
        t(cTag("muffin_base")).addOptional(new ResourceLocation("createfood:muffin_base")).addOptional(new ResourceLocation("create_snt:muffin"));
        t(cTag("mushrooms")).addOptional(new ResourceLocation("createfood:sliced_brown_mushroom")).addOptional(new ResourceLocation("createfood:sliced_red_mushroom")).addOptional(new ResourceLocation("minecraft:brown_mushroom")).addOptional(new ResourceLocation("minecraft:red_mushroom"));
        t(cTag("mutton_sandwich")).addOptional(new ResourceLocation("createfood:mutton_sandwich")).addOptional(new ResourceLocation("culturalcreators:incomplete_mutton_sandwich"));
        t(cTag("onion")).addOptional(new ResourceLocation("createfood:sliced_onion")).addOptional(new ResourceLocation("createfood:diced_onion")).addOptional(new ResourceLocation("farmersdelight:onion"));
        t(cTag("onion_rings")).addOptional(new ResourceLocation("ratatouille_fried_delights:onion_rings"));
        t(cTag("bayberry")).addOptional(new ResourceLocation("fruitsdelight:bayberry"));
        t(cTag("bayberry_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:bayberry_jam"));
        t(cTag("blueberry")).addOptional(new ResourceLocation("fruitsdelight:blueberry"));
        t(cTag("blueberry_custard_bottle")).addOptional(new ResourceLocation("fruitsdelight:blueberry_custard"));
        t(cTag("blueberry_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:blueberry_jam"));
        t(cTag("blueberry_juice_bottle")).addOptional(new ResourceLocation("hearthandharvest:blueberry_juice"));
        t(cTag("blueberry_pie")).addOptional(new ResourceLocation("hearthandharvest:blueberry_pie"));
        t(cTag("cranberry")).addOptional(new ResourceLocation("fruitsdelight:cranberry"));
        t(cTag("cranberry_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:cranberry_jam"));
        t(cTag("cranberry_juice_bottle")).addOptional(new ResourceLocation("expandeddelight:cranberry_juice"));
        t(cTag("durian")).addOptional(new ResourceLocation("fruitsdelight:durian_flesh"));
        t(cTag("durian_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:durian_jam"));
        t(cTag("durian_pie")).addOptional(new ResourceLocation("fruitsdelight:durian_pie"));
        t(cTag("fig")).addOptional(new ResourceLocation("fruitsdelight:fig"));
        t(cTag("fig_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:fig_jam"));
        t(cTag("hamimelon")).addOptional(new ResourceLocation("fruitsdelight:hamimelon")).addOptionalTag(cTag("hamimelon_slice"));
        t(cTag("hamimelon_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:hamimelon_jam"));
        t(cTag("hamimelon_juice_bottle")).addOptional(new ResourceLocation("fruitsdelight:hamimelon_juice"));
        t(cTag("hamimelon_popsicle")).addOptional(new ResourceLocation("fruitsdelight:hamimelon_popsicle"));
        t(cTag("hamimelon_slice")).addOptional(new ResourceLocation("fruitsdelight:hamimelon_slice"));
        t(cTag("hawberry")).addOptional(new ResourceLocation("fruitsdelight:hawberry"));
        t(cTag("hawberry_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:hawberry_jam"));
        t(cTag("kiwi")).addOptional(new ResourceLocation("fruitsdelight:kiwi"));
        t(cTag("kiwi_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:kiwi_jam"));
        t(cTag("kiwi_juice_bottle")).addOptional(new ResourceLocation("fruitsdelight:kiwi_juice"));
        t(cTag("kiwi_popsicle")).addOptional(new ResourceLocation("fruitsdelight:kiwi_popsicle"));
        t(cTag("lemon")).addOptional(new ResourceLocation("fruitsdelight:lemon")).addOptionalTag(cTag("lemon_slice"));
        t(cTag("lemon_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:lemon_jam"));
        t(cTag("lemon_juice_bottle")).addOptional(new ResourceLocation("fruitsdelight:lemon_juice"));
        t(cTag("lemon_slice")).addOptional(new ResourceLocation("fruitsdelight:lemon_slice"));
        t(cTag("lychee")).addOptional(new ResourceLocation("fruitsdelight:lychee"));
        t(cTag("lychee_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:lychee_jam"));
        t(cTag("mango")).addOptional(new ResourceLocation("fruitsdelight:mango"));
        t(cTag("mango_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:mango_jam"));
        t(cTag("mango_milkshake_bottle")).addOptional(new ResourceLocation("fruitsdelight:mango_milkshake"));
        t(cTag("mangosteen")).addOptional(new ResourceLocation("fruitsdelight:mangosteen"));
        t(cTag("mangosteen_cream_cake")).addOptional(new ResourceLocation("fruitsdelight:mangosteen_cake"));
        t(cTag("mangosteen_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:mangosteen_jam"));
        t(cTag("peach")).addOptional(new ResourceLocation("fruitsdelight:peach"));
        t(cTag("peach_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:peach_jam"));
        t(cTag("pear")).addOptional(new ResourceLocation("fruitsdelight:pear"));
        t(cTag("pear_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:pear_jam"));
        t(cTag("pear_juice_bottle")).addOptional(new ResourceLocation("fruitsdelight:pear_juice"));
        t(cTag("persimmon")).addOptional(new ResourceLocation("fruitsdelight:persimmon"));
        t(cTag("persimmon_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:persimmon_jam"));
        t(cTag("pineapple")).addOptional(new ResourceLocation("fruitsdelight:pineapple")).addOptionalTag(cTag("pineapple_slice"));
        t(cTag("pineapple_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:pineapple_jam"));
        t(cTag("pineapple_pie")).addOptional(new ResourceLocation("fruitsdelight:pineapple_pie"));
        t(cTag("pineapple_slice")).addOptional(new ResourceLocation("fruitsdelight:pineapple_slice"));
        t(cTag("orange")).addOptional(new ResourceLocation("fruitsdelight:orange")).addOptionalTag(cTag("orange_slice"));
        t(cTag("orange_jam_bottle")).addOptional(new ResourceLocation("fruitsdelight:orange_jam"));
        t(cTag("orange_juice_bottle")).addOptional(new ResourceLocation("fruitsdelight:orange_juice"));
        t(cTag("orange_slice")).addOptional(new ResourceLocation("fruitsdelight:orange_slice"));
        t(cTag("paprika")).addOptional(new ResourceLocation("createfood:paprika")).addOptional(new ResourceLocation("create:cinder_flour"));
        t(cTag("paprika_compat")).addOptional(new ResourceLocation("createfood:paprika"));
        t(cTag("paprika_ingredient")).addOptionalTag(cTag("pepper")).addOptional(new ResourceLocation("minecraft:nether_wart"));
        t(cTag("pasta_plate")).addOptional(new ResourceLocation("createfood:pasta_plate")).addOptional(new ResourceLocation("delightfulcreators:incomplete_pasta_dish"));
        t(cTag("pasta_plate_beef_meatballs")).addOptional(new ResourceLocation("createfood:pasta_plate_beef_meatballs")).addOptional(new ResourceLocation("delightfulcreators:incomplete_pasta_with_meatballs"));
        t(cTag("pasta_plate_eggplant")).addOptional(new ResourceLocation("createfood:pasta_plate_eggplant")).addOptional(new ResourceLocation("culturalcreators:incomplete_fried_eggplant_pasta"));
        t(cTag("pasta_plate_mutton_chop")).addOptional(new ResourceLocation("createfood:pasta_plate_mutton_chop")).addOptional(new ResourceLocation("delightfulcreators:incomplete_pasta_with_mutton_chop"));
        t(cTag("pasta_plate_squid_ink")).addOptional(new ResourceLocation("createfood:pasta_plate_squid_ink")).addOptional(new ResourceLocation("delightfulcreators:incomplete_squid_ink_pasta"));
        t(cTag("pasta_with_slimeballs")).addOptional(new ResourceLocation("frightsdelight:pasta_with_slimeballs"));
        t(cTag("peanut")).addOptional(new ResourceLocation("expandeddelight:peanut")).addOptional(new ResourceLocation("hearthandharvest:peanut")).addOptional(new ResourceLocation("hearthandharvest:roasted_peanuts"));
        t(cTag("peanut_butter")).addOptional(new ResourceLocation("expandeddelight:peanut_butter")).addOptional(new ResourceLocation("croptopia:peanut_butter")).addOptional(new ResourceLocation("hearthandharvest:peanut_butter"));
        t(cTag("peanut_butter_sandwich")).addOptional(new ResourceLocation("expandeddelight:peanut_butter_sandwich"));
        t(cTag("pita_dough_ingredients")).addOptional(new ResourceLocation("createfood:salt_dough_small")).addOptional(new ResourceLocation("createfood:wheat_dough_small"));
        t(cTag("plain_gelatin_dessert_block")).addOptional(new ResourceLocation("createfood:gelatin_dessert_block"));
        t(cTag("popcorn")).addOptional(new ResourceLocation("culturaldelights:popcorn")).addOptional(new ResourceLocation("hearthandharvest:popcorn"));
        t(cTag("pork_meatball_stick")).addOptional(new ResourceLocation("createfood:pork_meatball_stick_1")).addOptional(new ResourceLocation("createfood:pork_meatball_stick_2")).addOptional(new ResourceLocation("createfood:pork_meatball_stick_3"));
        t(cTag("potato")).addOptional(new ResourceLocation("createfood:shredded_potato")).addOptional(new ResourceLocation("createfood:sliced_potato")).addOptional(new ResourceLocation("minecraft:potato")).addOptional(new ResourceLocation("ratatouille_fried_delights:peeled_potato"));
        t(cTag("potato_chips")).addOptional(new ResourceLocation("createfood:potato_chips")).addOptional(new ResourceLocation("casualnessdelight:potato_chip"));
        t(cTag("powderable_eggs")).addOptional(new ResourceLocation("createfood:boiled_egg_peeled")).addOptional(new ResourceLocation("farmersdelight:fried_egg"));
        t(cTag("pressed_cocoa")).addOptional(new ResourceLocation("ratatouille:cocoa_solids"));
        t(cTag("pumpkin")).addOptional(new ResourceLocation("minecraft:pumpkin")).addOptional(new ResourceLocation("farmersdelight:pumpkin_slice"));
        t(cTag("pumpkin_pie")).addOptional(new ResourceLocation("minecraft:pumpkin_pie")).addOptional(new ResourceLocation("delightfulcreators:incomplete_pumpkin_pie"));
        t(cTag("rabbit_meatball_stick")).addOptional(new ResourceLocation("createfood:rabbit_meatball_stick_1")).addOptional(new ResourceLocation("createfood:rabbit_meatball_stick_2")).addOptional(new ResourceLocation("createfood:rabbit_meatball_stick_3"));
        t(cTag("raw_dragon_meat_cuts")).addOptional(new ResourceLocation("ends_delight:raw_dragon_meat_cuts"));
        t(cTag("raw_onion_rings")).addOptional(new ResourceLocation("ratatouille_fried_delights:breaded_onion_rings"));
        t(cTag("raw_pasta")).addOptional(new ResourceLocation("farmersdelight:raw_pasta")).addOptional(new ResourceLocation("farm_and_charm:raw_pasta"));
        t(cTag("raw_sausages")).addOptional(new ResourceLocation("ratatouille:raw_sausage")).addOptional(new ResourceLocation("hearthandharvest:raw_sausage"));
        t(cTag("red_mushroom")).addOptional(new ResourceLocation("minecraft:red_mushroom")).addOptional(new ResourceLocation("createfood:sliced_red_mushroom"));
        t(cTag("salad_ingredients")).addOptionalTag(cTag("foods/leafy_green")).addOptional(new ResourceLocation("croptopia:lettuce")).addOptional(new ResourceLocation("candlelight:lettuce"));
        t(cTag("salt")).addOptionalTag(cTag("dusts/salt")).addOptional(new ResourceLocation("hearthandharvest:salt")).addOptional(new ResourceLocation("meadow:alpine_salt")).addOptional(new ResourceLocation("vegandelight:salt")).addOptional(new ResourceLocation("ratatouille:salt"));
        t(cTag("salt_compat")).addOptional(new ResourceLocation("createfood:salt"));
        t(cTag("salt_dough")).addOptional(new ResourceLocation("ratatouille:salty_dough"));
        t(cTag("sausage")).addOptional(new ResourceLocation("createfood:sausages"));
        t(cTag("sausages")).addOptional(new ResourceLocation("createfood:sausages")).addOptional(new ResourceLocation("createfood:sausage_bits")).addOptional(new ResourceLocation("hearthandharvest:cooked_sausage")).addOptional(new ResourceLocation("ratatouille:sausage"));
        t(cTag("shakshuka_bowl")).addOptional(new ResourceLocation("veggiesdelight:shakshouka"));
        t(cTag("shredded_potato")).addOptional(new ResourceLocation("createfood:shredded_potato")).addOptional(new ResourceLocation("moredelight:diced_potatoes"));
        t(cTag("sliced_potato")).addOptional(new ResourceLocation("createfood:sliced_potato")).addOptional(new ResourceLocation("casualnessdelight:potato_slice")).addOptional(new ResourceLocation("rusticdelight:potato_slices"));
        t(cTag("sliced_tomato")).addOptional(new ResourceLocation("ratatouille_fried_delights:tomato_slices"));
        t(cTag("smore")).addOptional(new ResourceLocation("hearthandharvest:smore"));
        t(cTag("snickerdoodle")).addOptional(new ResourceLocation("expandeddelight:snickerdoodle"));
        t(cTag("soul_berry_cookie")).addOptional(new ResourceLocation("frightsdelight:cookie_soul_berry"));
        t(cTag("spider_eye_cookie")).addOptional(new ResourceLocation("frightsdelight:cookie_spidereye"));
        t(cTag("sugar")).addOptional(new ResourceLocation("minecraft:sugar")).addOptional(new ResourceLocation("createfood:powdered_sugar")).addOptional(new ResourceLocation("hearthandharvest:sugar_cubes"));
        t(cTag("sugar_cane")).addOptional(new ResourceLocation("minecraft:sugar_cane"));
        t(cTag("sugar_cookie")).addOptional(new ResourceLocation("expandeddelight:sugar_cookie"));
        t(cTag("sweet_berry_cookie")).addOptional(new ResourceLocation("farmersdelight:sweet_berry_cookie"));
        t(cTag("sweet_dough")).addOptional(new ResourceLocation("createfood:sugar_dough")).addOptional(new ResourceLocation("bakery:sweet_dough"));
        t(cTag("sweet_potato")).addOptional(new ResourceLocation("veggiesdelight:sweet_potato")).addOptional(new ResourceLocation("expandeddelight:sweet_potato"));
        t(cTag("sweet_roll")).addOptional(new ResourceLocation("create:sweet_roll"));
        t(cTag("syrup_cookie")).addOptional(new ResourceLocation("rusticdelight:syrup_cookie")).addOptional(new ResourceLocation("hearthandharvest:maple_cookie"));
        t(cTag("taco_shell_ingredient")).addOptionalTag(cTag("pita_bread")).addOptionalTag(cTag("tortilla"));
        t(cTag("toast")).addOptional(new ResourceLocation("createfood:toast_slice")).addOptional(new ResourceLocation("moredelight:toast"));
        t(cTag("toast_slice")).addOptional(new ResourceLocation("createfood:toast_slice")).addOptional(new ResourceLocation("moredelight:toast"));
        t(cTag("tomato")).addOptional(new ResourceLocation("createfood:diced_tomato")).addOptional(new ResourceLocation("createfood:sliced_tomato")).addOptional(new ResourceLocation("farmersdelight:tomato")).addOptional(new ResourceLocation("candlelight:tomato")).addOptional(new ResourceLocation("ratatouille_fried_delights:tomato_ingredient"));
        t(cTag("tomato_sauce")).addOptional(new ResourceLocation("farmersdelight:tomato_sauce"));
        t(cTag("tools/knife")).addOptionalTag(modTag("farmersdelight", "tools/knives"));
        t(cTag("tortilla")).addOptional(new ResourceLocation("culturaldelights:tortilla")).addOptional(new ResourceLocation("hearthandharvest:tortilla"));
        t(cTag("tortilla_chip_bowl")).addOptional(new ResourceLocation("createfood:pita_chip_bowl"));
        t(cTag("tortilla_chips")).addOptional(new ResourceLocation("culturaldelights:tortilla_chips"));
        t(cTag("turnip")).addOptional(new ResourceLocation("veggiesdelight:turnip"));
        t(cTag("ube_cookie")).addOptional(new ResourceLocation("ubesdelight:cookie_ube"));
        t(cTag("ube_cream_frosting")).addOptional(new ResourceLocation("createfood:ube_cream_frosting_bottle"));
        t(cTag("vegetable_oil")).addOptional(new ResourceLocation("createfood:vegetable_oil_bucket")).addOptional(new ResourceLocation("ratatouille_fried_delights:sunflower_oil")).addOptional(new ResourceLocation("hearthandharvest:cooking_oil")).addOptional(new ResourceLocation("rusticdelight:cooking_oil")).addOptional(new ResourceLocation("ratatouille_fried_delights:sunflower_seed_oil_bottle")).addOptional(new ResourceLocation("ratatouille_fried_delights:sunflower_oil_bucket"));
        t(cTag("vegetables")).addOptional(new ResourceLocation("minecraft:carrot")).addOptional(new ResourceLocation("minecraft:potato")).addOptional(new ResourceLocation("farmersdelight:tomato")).addOptional(new ResourceLocation("farmersdelight:onion")).addOptional(new ResourceLocation("veggiesdelight:turnip")).addOptional(new ResourceLocation("veggiesdelight:zucchini")).addOptional(new ResourceLocation("veggiesdelight:zucchini_slice")).addOptional(new ResourceLocation("veggiesdelight:broccoli")).addOptional(new ResourceLocation("veggiesdelight:cauliflower")).addOptional(new ResourceLocation("veggiesdelight:cauliflower_floret")).addOptional(new ResourceLocation("veggiesdelight:garlic")).addOptional(new ResourceLocation("veggiesdelight:garlic_clove")).addOptional(new ResourceLocation("veggiesdelight:bellpepper")).addOptional(new ResourceLocation("veggiesdelight:sweet_potato")).addOptional(new ResourceLocation("expandeddelight:sweet_potato")).addOptional(new ResourceLocation("expandeddelight:asparagus"));
        t(cTag("vegetables/carrot")).addOptional(new ResourceLocation("createfood:sliced_carrot"));
        t(cTag("vegetables/ginger")).addOptional(new ResourceLocation("ubesdelight:ginger")).addOptional(new ResourceLocation("culturaldelights:ginger"));
        t(cTag("crops/ginger")).addOptionalTag(cTag("vegetables/ginger"));
        t(cTag("vegetables/potato")).addOptional(new ResourceLocation("createfood:sliced_potato"));
        t(cTag("vegetables/ube")).addOptional(new ResourceLocation("ubesdelight:ube"));
        t(cTag("vinegar_bottle")).addOptional(new ResourceLocation("createfood:vinegar_bottle")).addOptional(new ResourceLocation("dumplings_delight:vinegar"));
        t(cTag("waffle")).addOptional(new ResourceLocation("hearthandharvest:waffle"));
        t(cTag("waffle_cone")).addOptional(new ResourceLocation("ratatouille_fried_delights:cone"));
        t(cTag("warped_fungus")).addOptional(new ResourceLocation("minecraft:warped_fungus")).addOptional(new ResourceLocation("createfood:sliced_warped_fungus"));
        t(cTag("wheat_dough")).addOptional(new ResourceLocation("create:dough")).addOptional(new ResourceLocation("farmersdelight:wheat_dough"));
        t(cTag("zucchini")).addOptional(new ResourceLocation("veggiesdelight:zucchini")).addOptional(new ResourceLocation("veggiesdelight:zucchini_slice")).addOptional(new ResourceLocation("veggiesdelight:roasted_zucchini"));
        t(modTag("farmersdelight", "cabbage_roll_ingredients")).addOptional(new ResourceLocation("createfood:tropical_fish_slice"));
        t(modTag("fruitsdelight", "jam")).addOptional(new ResourceLocation("createfood:apple_jam_bottle")).addOptional(new ResourceLocation("createfood:berry_jam_bottle")).addOptional(new ResourceLocation("createfood:chorus_fruit_jam_bottle")).addOptional(new ResourceLocation("createfood:glow_berry_jam_bottle")).addOptional(new ResourceLocation("createfood:melon_jam_bottle"));
        t(modTag("hearthandharvest", "jelly")).addOptional(new ResourceLocation("createfood:apple_jam_bottle")).addOptional(new ResourceLocation("createfood:berry_jam_bottle")).addOptional(new ResourceLocation("createfood:chorus_fruit_jam_bottle")).addOptional(new ResourceLocation("createfood:glow_berry_jam_bottle")).addOptional(new ResourceLocation("createfood:melon_jam_bottle"));
        t(modTag("ratatouille_fried_delights", "ratatouille_burger_ingredients")).addOptional(new ResourceLocation("createfood:bun")).addOptional(new ResourceLocation("createfood:cheese_slice")).addOptional(new ResourceLocation("createfood:chicken_patty")).addOptional(new ResourceLocation("createfood:sausage_patty")).addOptional(new ResourceLocation("createfood:sliced_tomato"));
        t(modTag("rusticdelight", "batter")).addOptionalTag(cTag("batter_bowl"));
        t(modTag("rusticdelight", "cooking_oil")).addOptional(new ResourceLocation("createfood:vegetable_oil_bucket"));
        t(modTag("rusticdelight", "syrup")).addOptional(new ResourceLocation("createfood:cane_syrup_bottle"));
        t(modTag("minecraft", "fishes")).addOptional(new ResourceLocation("createfood:cooked_tropical_fish"));
        t(modTag("minecraft", "cat_food")).addOptional(new ResourceLocation("createfood:tropical_fish_slice"));

        // Delightful Creators ships these four under data/c/tags/items/, the 1.20 path, so none of
        // them load on 1.21 and its horse feed recipe has no valid input. Re-declare them here.
        t(cTag("bale")).addOptional(new ResourceLocation("minecraft:hay_block")).addOptional(new ResourceLocation("farmersdelight:rice_bale"));
        t(cTag("bone_broth_ingredients")).addOptional(new ResourceLocation("minecraft:glow_berries")).addOptional(new ResourceLocation("minecraft:glow_lichen")).addOptional(new ResourceLocation("minecraft:hanging_roots")).addOptionalTag(cTag("mushrooms"));
        t(cTag("dumplings_ingredients")).addOptional(new ResourceLocation("minecraft:beef")).addOptional(new ResourceLocation("minecraft:chicken")).addOptional(new ResourceLocation("minecraft:porkchop")).addOptional(new ResourceLocation("farmersdelight:bacon")).addOptional(new ResourceLocation("farmersdelight:chicken_cuts")).addOptional(new ResourceLocation("farmersdelight:minced_beef")).addOptionalTag(cTag("mushrooms"));
        t(cTag("mushroom_rice_ingredients")).addOptionalTag(cTag("carrot")).addOptionalTag(cTag("potato"));
        // Delightful Creators ships its own pumpkin pie slice; treat it as ours.
        t(cTag("pumpkin_pie_slice")).addOptional(new ResourceLocation("delightfulcreators:pumpkin_pie_slice"));
    }

    private static TagKey<Item> cTag(String id) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("c", id));
    }

    private static TagKey<Item> modTag(String namespace, String id) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(namespace, id));
    }

    /** Deduping stand-in for {@link #tag(TagKey)}; see {@link #emitted}. */
    private DedupedAppender t(TagKey<Item> key) {
        return new DedupedAppender(key, tag(key));
    }

    /**
     * Forwards to a real {@link TagsProvider.TagAppender} but skips entries already added to the same
     * tag. First occurrence wins, so tag ordering in the generated files is unchanged.
     */
    private final class DedupedAppender {

        private final TagKey<Item> key;
        private final TagsProvider.TagAppender<Item> delegate;

        private DedupedAppender(TagKey<Item> key, TagsProvider.TagAppender<Item> delegate) {
            this.key = key;
            this.delegate = delegate;
        }

        private DedupedAppender addOptional(ResourceLocation id) {
            if (emitted.add(key.location() + "|" + id)) {
                delegate.addOptional(id);
            }
            return this;
        }

        private DedupedAppender addOptionalTag(TagKey<Item> tag) {
            if (emitted.add(key.location() + "|#" + tag.location())) {
                delegate.addOptionalTag(tag);
            }
            return this;
        }
    }
}