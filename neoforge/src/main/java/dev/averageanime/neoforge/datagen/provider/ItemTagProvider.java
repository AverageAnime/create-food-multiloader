package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.createfood.lib.datagen.DedupedTagAppender;
import dev.averageanime.CreateFoodCommon;
import dev.averageanime.neoforge.block.BlockRegistration;
import dev.averageanime.neoforge.block.FluidRegistration;
import dev.averageanime.createfood.lib.fluid.FluidBlock;
import dev.averageanime.neoforge.item.ItemRegistration;
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
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

public class ItemTagProvider extends TagsProvider<Item> {

    /** Drops repeats, which walking items then blocks would otherwise produce for every block item. */
    private final DedupedTagAppender<Item> appender = new DedupedTagAppender<>(this::tag);

    public ItemTagProvider(PackOutput output,
                           CompletableFuture<HolderLookup.Provider> lookupProvider,
                           ExistingFileHelper existingFileHelper) {
        super(output, Registries.ITEM, lookupProvider, CreateFoodCommon.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        ItemRegistration.ITEMS.getEntries().forEach(holder -> {
            String id = holder.getId().getPath();
            t(cTag(id)).addOptional(ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, id));
            if (id.endsWith("_bottle")) {
                String baseId = id.substring(0, id.length() - "_bottle".length());
                t(cTag(baseId)).addOptional(ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, id));
            }
        });

        BlockRegistration.BLOCKS.getEntries().forEach(holder -> {
            String id = holder.getId().getPath();
            t(cTag(id)).addOptional(ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, id));
        });

        for (Field field : FluidRegistration.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            try {
                Object value = field.get(null);
                if (value instanceof FluidBlock.FluidType fluidType) {
                    DeferredItem<?> bucket = fluidType.BUCKET;
                    String id = bucket.getId().getPath();
                    t(cTag(id)).addOptional(ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, id));
                }
            } catch (IllegalAccessException ignored) {}
        }

        t(cTag("apple")).addOptional(ResourceLocation.parse("minecraft:apple")).addOptional(ResourceLocation.parse("createfood:apple_slice"));
        t(cTag("apple_jam")).addOptional(ResourceLocation.parse("hearthandharvest:apple_jam"));
        t(cTag("apple_jam_bottle")).addOptional(ResourceLocation.parse("bakery:apple_jam")).addOptional(ResourceLocation.parse("fruitsdelight:apple_jam"));
        t(cTag("apple_juice_bottle")).addOptional(ResourceLocation.parse("expandeddelight:apple_juice"));
        t(cTag("apple_slice")).addOptional(ResourceLocation.parse("create_deepfried:apple_slices")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:apple_slices"));
        t(cTag("asparagus")).addOptional(ResourceLocation.parse("expandeddelight:asparagus"));
        t(cTag("bacon_sandwich")).addOptional(ResourceLocation.parse("delightfulcreators:incomplete_bacon_sandwich"));
        t(cTag("bar_of_chocolate")).addOptional(ResourceLocation.parse("create:bar_of_chocolate")).addOptional(ResourceLocation.parse("candlelight:chocolate")).addOptional(ResourceLocation.parse("hearthandharvest:chocolate_bar"));
        t(cTag("bar_of_dark_chocolate")).addOptional(ResourceLocation.parse("create_confectionery:bar_of_black_chocolate"));
        t(cTag("bar_of_white_chocolate")).addOptional(ResourceLocation.parse("create_confectionery:bar_of_white_chocolate"));
        t(cTag("batter_bowl")).addOptional(ResourceLocation.parse("hearthandharvest:batter")).addOptional(ResourceLocation.parse("rusticdelight:batter"));
        t(cTag("beef_meatball_stick")).addOptional(ResourceLocation.parse("createfood:beef_meatball_stick_1")).addOptional(ResourceLocation.parse("createfood:beef_meatball_stick_2")).addOptional(ResourceLocation.parse("createfood:beef_meatball_stick_3"));
        t(cTag("vegetable_meatball_stick")).addOptional(ResourceLocation.parse("createfood:vegetable_meatball_stick_1")).addOptional(ResourceLocation.parse("createfood:vegetable_meatball_stick_2")).addOptional(ResourceLocation.parse("createfood:vegetable_meatball_stick_3"));
        t(cTag("beetroot")).addOptional(ResourceLocation.parse("minecraft:beetroot")).addOptional(ResourceLocation.parse("createfood:sliced_beetroot")).addOptional(ResourceLocation.parse("createfood:shredded_beetroot"));
        // Whole bell peppers are deliberately absent: Rustic Delight cuts them into slices and seeds on
        // the same cutting board our paprika recipe uses, and the first match wins. Its slices and
        // roasted forms are listed instead, so paprika comes from those.
        t(cTag("bell_pepper")).addOptional(ResourceLocation.parse("rusticdelight:bell_pepper_slice_black")).addOptional(ResourceLocation.parse("rusticdelight:bell_pepper_slice_blue")).addOptional(ResourceLocation.parse("rusticdelight:bell_pepper_slice_green")).addOptional(ResourceLocation.parse("rusticdelight:bell_pepper_slice_orange")).addOptional(ResourceLocation.parse("rusticdelight:bell_pepper_slice_pink")).addOptional(ResourceLocation.parse("rusticdelight:bell_pepper_slice_purple")).addOptional(ResourceLocation.parse("rusticdelight:bell_pepper_slice_red")).addOptional(ResourceLocation.parse("rusticdelight:bell_pepper_slice_white")).addOptional(ResourceLocation.parse("rusticdelight:bell_pepper_slice_yellow")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_black")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_blue")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_green")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_orange")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_pink")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_purple")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_red")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_white")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_yellow")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_slice_black")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_slice_blue")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_slice_green")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_slice_orange")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_slice_pink")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_slice_purple")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_slice_red")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_slice_white")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_slice_yellow")).addOptional(ResourceLocation.parse("veggiesdelight:bellpepper")).addOptional(ResourceLocation.parse("veggiesdelight:smoked_bellpepper"));
        t(cTag("berry_jam")).addOptional(ResourceLocation.parse("hearthandharvest:sweet_berry_jam"));
        t(cTag("berry_jam_bottle")).addOptional(ResourceLocation.parse("bakery:sweetberry_jam")).addOptional(ResourceLocation.parse("expandeddelight:sweet_berry_jelly")).addOptional(ResourceLocation.parse("fruitsdelight:sweetberry_jam"));
        t(cTag("berry_juice_bottle")).addOptional(ResourceLocation.parse("expandeddelight:sweet_berry_juice")).addOptional(ResourceLocation.parse("hearthandharvest:sweet_berry_juice"));
        t(cTag("berry_milkshake_bottle")).addOptional(ResourceLocation.parse("beachparty:sweetberry_milkshake")).addOptional(ResourceLocation.parse("create_dd:strawberry_milkshake"));
        t(cTag("berry_milkshake_bucket")).addOptional(ResourceLocation.parse("create_dd:strawberry_milkshake_bucket"));
        t(cTag("avocado")).addOptionalTag(modTag("culturaldelights", "avocados"));
        t(cTag("bowl")).addOptional(ResourceLocation.parse("minecraft:bowl"));
        t(cTag("bread")).addOptional(ResourceLocation.parse("minecraft:bread")).addOptionalTag(cTag("foods/bread"));
        t(cTag("bread_crumbs")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:breadcrumb"));
        t(cTag("bread_fried_egg")).addOptional(ResourceLocation.parse("delightfulcreators:incomplete_egg_sandwich"));
        t(cTag("bread_slice")).addOptional(ResourceLocation.parse("moredelight:bread_slice"));
        t(cTag("broccoli")).addOptional(ResourceLocation.parse("veggiesdelight:broccoli"));
        t(cTag("brown_mushroom")).addOptional(ResourceLocation.parse("minecraft:brown_mushroom")).addOptional(ResourceLocation.parse("createfood:sliced_brown_mushroom"));
        t(cTag("brown_sugar")).addOptional(ResourceLocation.parse("ubesdelight:sugar_brown"));
        t(cTag("bun")).addOptional(ResourceLocation.parse("bakery:bun")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:burger_bun")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:top_burger_bun")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:bottom_burger_bun"));
        t(cTag("butter")).addOptional(ResourceLocation.parse("croptopia:butter")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:butter")).addOptional(ResourceLocation.parse("hearthandharvest:butter"));
        t(cTag("cacao_butter")).addOptional(ResourceLocation.parse("create_confectionery:cocoa_butter")).addOptional(ResourceLocation.parse("ratatouille:cocoa_butter"));
        t(cTag("cacao_mass_bucket")).addOptional(ResourceLocation.parse("ratatouille:cocoa_liquor_bucket"));
        t(cTag("cacao_nibs")).addOptional(ResourceLocation.parse("ratatouille:dried_cocoa_nibs"));
        t(cTag("cake_base")).addOptional(ResourceLocation.parse("createadditions:cake_base_baked")).addOptional(ResourceLocation.parse("bakery:blank_cake")).addOptional(ResourceLocation.parse("ratatouille:cake_base"));
        t(cTag("cake_batter_bucket")).addOptional(ResourceLocation.parse("ratatouille:cake_batter_bucket"));
        t(cTag("calamari")).addOptionalTag(cTag("foods/raw_calamari")).addOptionalTag(cTag("foods/cooked_calamari")).addOptional(ResourceLocation.parse("rusticdelight:calamari")).addOptional(ResourceLocation.parse("rusticdelight:calamari_slice")).addOptional(ResourceLocation.parse("rusticdelight:cooked_calamari")).addOptional(ResourceLocation.parse("rusticdelight:cooked_calamari_slice")).addOptional(ResourceLocation.parse("culturaldelights:raw_calamari")).addOptional(ResourceLocation.parse("culturaldelights:cooked_calamari"));
        t(cTag("cane_syrup")).addOptional(ResourceLocation.parse("rusticdelight:syrup")).addOptional(ResourceLocation.parse("hearthandharvest:syrup_bottle"));
        t(cTag("syrup")).addOptional(ResourceLocation.parse("createfood:cane_syrup_bottle")).addOptional(ResourceLocation.parse("rusticdelight:syrup")).addOptional(ResourceLocation.parse("hearthandharvest:syrup_bottle"));
        t(cTag("cane_syrup_bottle")).addOptional(ResourceLocation.parse("rusticdelight:syrup")).addOptional(ResourceLocation.parse("hearthandharvest:syrup_bottle"));
        t(cTag("caramel")).addOptional(ResourceLocation.parse("hearthandharvest:caramel")).addOptional(ResourceLocation.parse("create_confectionery:bar_of_caramel"));
        t(cTag("caramel_berries")).addOptional(ResourceLocation.parse("create_confectionery:caramel_glazed_berries"));
        // Create Confectionery glazes the same berries in the same fluid we do, so its recipe and ours
        // race on the spout. Listing its results keeps whichever one wins interchangeable with ours.
        t(cTag("dark_chocolate_berries")).addOptional(ResourceLocation.parse("create_confectionery:black_chocolate_glazed_berries"));
        t(cTag("white_chocolate_berries")).addOptional(ResourceLocation.parse("create_confectionery:white_chocolate_glazed_berries"));
        t(cTag("caramel_bucket")).addOptional(ResourceLocation.parse("create_dd:caramel_bucket")).addOptional(ResourceLocation.parse("create_confectionery:caramel_bucket"));
        t(cTag("carrot")).addOptional(ResourceLocation.parse("createfood:sliced_carrot")).addOptional(ResourceLocation.parse("minecraft:carrot")).addOptional(ResourceLocation.parse("createfood:shredded_carrot"));
        t(cTag("cauliflower")).addOptional(ResourceLocation.parse("veggiesdelight:cauliflower")).addOptional(ResourceLocation.parse("veggiesdelight:cauliflower_floret")).addOptional(ResourceLocation.parse("veggiesdelight:roasted_cauliflower_floret"));
        t(cTag("cheese_block")).addOptional(ResourceLocation.parse("meadow:cheese_block")).addOptional(ResourceLocation.parse("casualnessdelight:cheese_wheel")).addOptional(ResourceLocation.parse("hearthandharvest:cheddar_cheese_wheel")).addOptional(ResourceLocation.parse("hearthandharvest:goat_cheese_wheel")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:cheese")).addOptional(ResourceLocation.parse("expandeddelight:cheese_wheel")).addOptional(ResourceLocation.parse("expandeddelight:goat_cheese_wheel"));
        t(cTag("cheese_sandwich")).addOptional(ResourceLocation.parse("expandeddelight:cheese_sandwich"));
        t(cTag("cheese_slice")).addOptional(ResourceLocation.parse("meadow:piece_of_cheese")).addOptional(ResourceLocation.parse("casualnessdelight:cheese_wheel_slice")).addOptional(ResourceLocation.parse("hearthandharvest:cheddar_cheese_slice")).addOptional(ResourceLocation.parse("hearthandharvest:goat_cheese_slice")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:cheese_slice")).addOptional(ResourceLocation.parse("expandeddelight:goat_cheese_slice"));
        t(cTag("cheeses")).addOptional(ResourceLocation.parse("createfood:cheese_slice")).addOptional(ResourceLocation.parse("brewinandchewin:flaxen_cheese_wedge")).addOptional(ResourceLocation.parse("expandeddelight:cheese_slice")).addOptional(ResourceLocation.parse("meadow:piece_of_cheese")).addOptional(ResourceLocation.parse("casualnessdelight:cheese_wheel_slice")).addOptional(ResourceLocation.parse("create_bic_bit:unripe_cheese_wedge")).addOptional(ResourceLocation.parse("create_bic_bit:young_cheese_wedge")).addOptional(ResourceLocation.parse("create_bic_bit:aged_cheese_wedge")).addOptional(ResourceLocation.parse("hearthandharvest:cheddar_cheese_slice")).addOptional(ResourceLocation.parse("hearthandharvest:goat_cheese_slice")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:cheese")).addOptional(ResourceLocation.parse("expandeddelight:goat_cheese_slice"));
        t(cTag("chicken_nuggets")).addOptional(ResourceLocation.parse("create_deepfried:chicken_nuggets")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:chicken_nuggets"));
        t(cTag("chorus_fruit_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:chorus_jam"));
        t(cTag("garlic")).addOptional(ResourceLocation.parse("veggiesdelight:garlic")).addOptional(ResourceLocation.parse("veggiesdelight:garlic_clove")).addOptional(ResourceLocation.parse("veggiesdelight:roasted_garlic_clove"));
        t(cTag("grilled_cheese_sandwich")).addOptional(ResourceLocation.parse("expandeddelight:grilled_cheese"));
        t(cTag("ice_cream_bucket")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:ice_cream_base_bucket"));
        t(cTag("melon_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:melon_jam"));
        t(cTag("pepper")).addOptionalTag(cTag("bell_pepper")).addOptional(ResourceLocation.parse("expandeddelight:chili_pepper")).addOptional(ResourceLocation.parse("croptopia:chile_pepper"));
        t(cTag("chocolate_cake_base"));
        t(cTag("chocolate_chip_chocolate_cookie")).addOptional(ResourceLocation.parse("expandeddelight:chocolate_cookie"));
        t(cTag("chocolate_milk_bottle")).addOptional(ResourceLocation.parse("hearthandharvest:chocolate_milk_bottle"));
        t(cTag("chocolate_milkshake_bottle")).addOptional(ResourceLocation.parse("beachparty:chocolate_milkshake")).addOptional(ResourceLocation.parse("create_dd:chocolate_milkshake"));
        t(cTag("chocolate_milkshake_bucket")).addOptional(ResourceLocation.parse("create_dd:chocolate_milkshake_bucket"));
        t(cTag("chocolate_sweet_dough")).addOptional(ResourceLocation.parse("createfood:chocolate_sugar_dough"));
        t(cTag("chocolate_toast")).addOptional(ResourceLocation.parse("moredelight:chocolate_toast"));
        t(cTag("chorus_cookie")).addOptional(ResourceLocation.parse("ends_delight:chorus_cookie"));
        t(cTag("chorus_fruit")).addOptional(ResourceLocation.parse("minecraft:chorus_fruit")).addOptional(ResourceLocation.parse("createfood:chorus_fruit_slice"));
        t(cTag("chorus_fruit_juice_bottle")).addOptional(ResourceLocation.parse("endersdelight:chorus_juice"));
        t(cTag("cinnamon_sweet_roll")).addOptional(ResourceLocation.parse("expandeddelight:sweet_roll"));
        t(cTag("cinnamon_sweet_roll_berry")).addOptional(ResourceLocation.parse("expandeddelight:berry_sweet_roll"));
        t(cTag("cinnamon_sweet_roll_glow_berry")).addOptional(ResourceLocation.parse("expandeddelight:glow_berry_sweet_roll"));
        t(cTag("cocoa_powder")).addOptional(ResourceLocation.parse("create_confectionery:cocoa_powder")).addOptional(ResourceLocation.parse("ratatouille:cocoa_powder"));
        t(cTag("coffee_beans")).addOptional(ResourceLocation.parse("rusticdelight:coffee_beans"));
        t(cTag("condensed_milk_bottle")).addOptional(ResourceLocation.parse("ubesdelight:condensed_milk_bottle"));
        t(cTag("condensed_milk_bucket")).addOptional(ResourceLocation.parse("create_dd:condense_milk_bucket"));
        t(cTag("container")).addOptional(ResourceLocation.parse("minecraft:bucket")).addOptional(ResourceLocation.parse("minecraft:stick"));
        t(cTag("cooked_bacon")).addOptional(ResourceLocation.parse("farmersdelight:cooked_bacon"));
        // beef and rabbit grind from their own c:*_cuts items; chicken and mutton have no
        // in-house cut, so their Farmer's Delight equivalents are aliased here to keep every
        // ground_* cutting recipe on a tag.
        t(cTag("chicken_cuts")).addOptional(ResourceLocation.parse("farmersdelight:chicken_cuts"));
        t(cTag("mutton_chops")).addOptional(ResourceLocation.parse("farmersdelight:mutton_chops"));
        t(cTag("cooked_beef")).addOptional(ResourceLocation.parse("minecraft:cooked_beef")).addOptional(ResourceLocation.parse("farmersdelight:beef_patty")).addOptional(ResourceLocation.parse("vegandelight:tofu_patty")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:hamburger_patty")).addOptional(ResourceLocation.parse("kaleidoscope_cookery:cooked_cow_offal")).addOptional(ResourceLocation.parse("createfood:cooked_beef_cuts"));
        t(cTag("cooked_chicken")).addOptional(ResourceLocation.parse("farmersdelight:cooked_chicken_cuts")).addOptional(ResourceLocation.parse("minecraft:cooked_chicken"));
        t(cTag("cooked_corn")).addOptional(ResourceLocation.parse("culturaldelights:smoked_corn"));
        t(cTag("cooked_eggplant")).addOptionalTag(modTag("culturaldelights", "smoked_regular_eggplants")).addOptional(ResourceLocation.parse("culturaldelights:smoked_white_eggplant"));
        t(cTag("cooked_eggs")).addOptional(ResourceLocation.parse("farmersdelight:fried_egg"));
        t(cTag("cooked_fishes")).addOptional(ResourceLocation.parse("minecraft:cooked_salmon")).addOptional(ResourceLocation.parse("farmersdelight:cooked_salmon_slice")).addOptional(ResourceLocation.parse("minecraft:cooked_cod")).addOptional(ResourceLocation.parse("farmersdelight:cooked_cod_slice")).addOptional(ResourceLocation.parse("createfood:cooked_tropical_fish")).addOptional(ResourceLocation.parse("createfood:cooked_tropical_fish_slice"));
        t(cTag("cooked_mutton")).addOptional(ResourceLocation.parse("minecraft:cooked_mutton")).addOptional(ResourceLocation.parse("farmersdelight:cooked_mutton_chops"));
        t(cTag("cooked_pasta")).addOptional(ResourceLocation.parse("createfood:pasta"));
        t(cTag("cooked_pork")).addOptional(ResourceLocation.parse("croptopia:cooked_bacon")).addOptional(ResourceLocation.parse("farmersdelight:cooked_bacon")).addOptional(ResourceLocation.parse("minecraft:cooked_porkchop")).addOptional(ResourceLocation.parse("createfood:bacon_bits"));
        t(cTag("cooked_rabbit")).addOptional(ResourceLocation.parse("minecraft:cooked_rabbit")).addOptional(ResourceLocation.parse("createfood:cooked_rabbit_cuts")).addOptional(ResourceLocation.parse("createfood:rabbit_jerky"));
        t(cTag("cooked_rice")).addOptional(ResourceLocation.parse("farmersdelight:cooked_rice"));
        t(cTag("corn")).addOptional(ResourceLocation.parse("culturaldelights:corn_cob")).addOptional(ResourceLocation.parse("hearthandharvest:corn"));
        t(cTag("corn_dough")).addOptional(ResourceLocation.parse("culturaldelights:corn_dough"));
        t(cTag("cucumber")).addOptionalTag(modTag("culturaldelights", "cucumbers"));
        t(cTag("eggplant")).addOptionalTag(modTag("culturaldelights", "regular_eggplants"));
        t(cTag("corn_flour")).addOptional(ResourceLocation.parse("hearthandharvest:corn_meal"));
        t(cTag("corn_kernel")).addOptional(ResourceLocation.parse("culturaldelights:corn_kernels")).addOptional(ResourceLocation.parse("hearthandharvest:corn_kernels"));
        t(cTag("corn_kernels")).addOptionalTag(cTag("corn_kernel"));
        t(cTag("cotton_candy_stick")).addOptional(ResourceLocation.parse("hearthandharvest:cotton_candy"));
        t(cTag("cream_donut")).addOptional(ResourceLocation.parse("create_snt:sweet_donut")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:creamy_donut"));
        t(cTag("crimson_fungus")).addOptional(ResourceLocation.parse("minecraft:crimson_fungus")).addOptional(ResourceLocation.parse("createfood:sliced_crimson_fungus"));
        t(cTag("crushable_cookies")).addOptional(ResourceLocation.parse("minecraft:cookie")).addOptional(ResourceLocation.parse("createfood:butterscotch_chip_cookie")).addOptional(ResourceLocation.parse("createfood:caramel_chip_cookie")).addOptional(ResourceLocation.parse("createfood:dark_chocolate_chip_cookie")).addOptional(ResourceLocation.parse("createfood:toffee_chip_cookie")).addOptional(ResourceLocation.parse("createfood:white_chocolate_chip_cookie")).addOptional(ResourceLocation.parse("farmersdelight:honey_cookie"));
        t(cTag("donut_base")).addOptional(ResourceLocation.parse("create_snt:donut")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:fried_donut"));
        t(cTag("dough")).addOptional(ResourceLocation.parse("farmersdelight:wheat_dough"));
        t(cTag("dried_coffee_beans")).addOptional(ResourceLocation.parse("rusticdelight:roasted_coffee_beans"));
        t(cTag("dumpling_ingredients")).addOptionalTag(cTag("foods/raw_pork")).addOptionalTag(cTag("foods/raw_chicken")).addOptionalTag(cTag("foods/raw_beef")).addOptionalTag(cTag("foods/raw_mutton")).addOptionalTag(cTag("mushrooms"));
        t(cTag("dumplings")).addOptional(ResourceLocation.parse("brewery:dumplings")).addOptional(ResourceLocation.parse("farmersdelight:dumplings")).addOptional(ResourceLocation.parse("delightfulcreators:incomplete_dumplings"));
        t(cTag("egg_burrito_ingredients")).addOptional(ResourceLocation.parse("createfood:boiled_egg_peeled")).addOptional(ResourceLocation.parse("farmersdelight:fried_egg"));
        t(cTag("egg_yolk")).addOptional(ResourceLocation.parse("ratatouille:egg_yolk"));
        t(cTag("eggplant_burger")).addOptional(ResourceLocation.parse("culturalcreators:incomplete_eggplant_burger"));
        t(cTag("eggs")).addOptional(ResourceLocation.parse("createfood:egg_powder"));
        t(cTag("eggshell")).addOptional(ResourceLocation.parse("ratatouille:egg_shell"));
        t(cTag("endermite_meatball_stick")).addOptional(ResourceLocation.parse("createfood:endermite_meatball_stick_1")).addOptional(ResourceLocation.parse("createfood:endermite_meatball_stick_2")).addOptional(ResourceLocation.parse("createfood:endermite_meatball_stick_3"));
        t(cTag("flesh_cookie")).addOptional(ResourceLocation.parse("frightsdelight:cookie_flesh"));
        t(cTag("flours/wheat")).addOptional(ResourceLocation.parse("farm_and_charm:flour")).addOptional(ResourceLocation.parse("hearthandharvest:flour")).addOptional(ResourceLocation.parse("kaleidoscope_cookery:flour"));
        t(cTag("food_plates")).addOptional(ResourceLocation.parse("displaydelight:food_plate")).addOptional(ResourceLocation.parse("displaydelight:small_food_plate"));
        t(cTag("foods/cooked_fish")).addOptional(ResourceLocation.parse("createfood:cooked_tropical_fish")).addOptional(ResourceLocation.parse("createfood:cooked_tropical_fish_slice"));
        t(cTag("foods/cooked_meats/cooked_rabbit")).addOptional(ResourceLocation.parse("minecraft:cooked_rabbit")).addOptional(ResourceLocation.parse("createfood:cooked_rabbit_cuts"));
        t(cTag("foods/cooked_tropical_fish")).addOptional(ResourceLocation.parse("createfood:cooked_tropical_fish")).addOptional(ResourceLocation.parse("createfood:cooked_tropical_fish_slice"));
        t(cTag("foods/doughs")).addOptional(ResourceLocation.parse("create:dough"));
        t(cTag("foods/raw_bacon")).addOptional(ResourceLocation.parse("farmersdelight:bacon"));
        t(cTag("foods/raw_beef")).addOptional(ResourceLocation.parse("createfood:ground_beef"));
        t(cTag("foods/raw_chicken")).addOptional(ResourceLocation.parse("createfood:ground_chicken"));
        t(cTag("foods/raw_fish")).addOptional(ResourceLocation.parse("createfood:tropical_fish_slice"));
        t(cTag("foods/raw_meats/ground")).addOptional(ResourceLocation.parse("createfood:ground_beef")).addOptional(ResourceLocation.parse("createfood:ground_chicken")).addOptional(ResourceLocation.parse("createfood:ground_mutton")).addOptional(ResourceLocation.parse("createfood:ground_pork")).addOptional(ResourceLocation.parse("createfood:ground_rabbit")).addOptional(ResourceLocation.parse("ratatouille:mince_meat"));
        t(cTag("foods/raw_meats/raw_bacon")).addOptional(ResourceLocation.parse("farmersdelight:bacon"));
        t(cTag("foods/raw_meats/raw_beef")).addOptional(ResourceLocation.parse("createfood:ground_beef"));
        t(cTag("foods/raw_meats/raw_chicken")).addOptional(ResourceLocation.parse("createfood:ground_chicken"));
        t(cTag("foods/raw_meats/raw_mutton")).addOptional(ResourceLocation.parse("createfood:ground_mutton"));
        t(cTag("foods/raw_meats/raw_pork")).addOptional(ResourceLocation.parse("createfood:ground_pork"));
        t(cTag("foods/raw_meats/raw_rabbit")).addOptional(ResourceLocation.parse("minecraft:rabbit")).addOptional(ResourceLocation.parse("createfood:rabbit_cuts")).addOptional(ResourceLocation.parse("createfood:ground_rabbit"));
        t(cTag("foods/raw_mutton")).addOptional(ResourceLocation.parse("createfood:ground_mutton"));
        t(cTag("foods/raw_pork")).addOptional(ResourceLocation.parse("createfood:ground_pork"));
        t(cTag("foods/raw_rabbit")).addOptional(ResourceLocation.parse("minecraft:rabbit")).addOptional(ResourceLocation.parse("createfood:rabbit_cuts")).addOptional(ResourceLocation.parse("createfood:ground_rabbit"));
        t(cTag("foods/raw_tropical_fish")).addOptional(ResourceLocation.parse("minecraft:tropical_fish")).addOptional(ResourceLocation.parse("createfood:tropical_fish_slice"));
        t(cTag("foods/safe_raw_fish")).addOptional(ResourceLocation.parse("createfood:tropical_fish_slice"));
        t(cTag("frosting_ingredients")).addOptionalTag(cTag("cream_cheese")).addOptionalTag(cTag("butter"));
        t(cTag("fruits")).addOptionalTag(cTag("apple")).addOptionalTag(cTag("melon")).addOptionalTag(cTag("chorus_fruit")).addOptionalTag(cTag("foods/berries")).addOptional(ResourceLocation.parse("hearthandharvest:blueberries")).addOptional(ResourceLocation.parse("hearthandharvest:raspberry")).addOptional(ResourceLocation.parse("hearthandharvest:red_grapes")).addOptional(ResourceLocation.parse("hearthandharvest:green_grapes")).addOptional(ResourceLocation.parse("hearthandharvest:cherry")).addOptional(ResourceLocation.parse("fruitsdelight:bayberry")).addOptional(ResourceLocation.parse("fruitsdelight:blueberry")).addOptional(ResourceLocation.parse("fruitsdelight:cranberry")).addOptional(ResourceLocation.parse("fruitsdelight:durian_flesh")).addOptional(ResourceLocation.parse("fruitsdelight:fig")).addOptional(ResourceLocation.parse("fruitsdelight:hamimelon")).addOptional(ResourceLocation.parse("fruitsdelight:hamimelon_slice")).addOptional(ResourceLocation.parse("fruitsdelight:hawberry")).addOptional(ResourceLocation.parse("fruitsdelight:kiwi")).addOptional(ResourceLocation.parse("fruitsdelight:lemon")).addOptional(ResourceLocation.parse("fruitsdelight:lemon_slice")).addOptional(ResourceLocation.parse("fruitsdelight:lychee")).addOptional(ResourceLocation.parse("fruitsdelight:mango")).addOptional(ResourceLocation.parse("fruitsdelight:mangosteen")).addOptional(ResourceLocation.parse("fruitsdelight:orange")).addOptional(ResourceLocation.parse("fruitsdelight:orange_slice")).addOptional(ResourceLocation.parse("fruitsdelight:peach")).addOptional(ResourceLocation.parse("fruitsdelight:pear")).addOptional(ResourceLocation.parse("fruitsdelight:persimmon")).addOptional(ResourceLocation.parse("fruitsdelight:pineapple")).addOptional(ResourceLocation.parse("fruitsdelight:pineapple_slice")).addOptionalTag(cTag("foods/berry"));
        t(cTag("gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:black_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:blue_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:brown_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:cyan_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:gray_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:green_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:light_gray_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:lime_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:magenta_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:orange_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:pink_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:purple_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:red_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:yellow_gelatin_dessert_block"));
        t(cTag("gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:black_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:blue_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:brown_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:cyan_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:gray_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:green_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:light_gray_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:lime_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:magenta_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:orange_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:pink_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:purple_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:red_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:yellow_gelatin_dessert_slice"));
        t(cTag("gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:black_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:blue_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:brown_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:cyan_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:gray_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:green_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:light_gray_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:lime_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:magenta_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:orange_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:pink_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:purple_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:red_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:yellow_gelatin_mix_bucket"));
        t(cTag("ginger_cookie")).addOptional(ResourceLocation.parse("ubesdelight:cookie_ginger"));
        t(cTag("glow_berry_jam")).addOptional(ResourceLocation.parse("hearthandharvest:glow_berry_jam"));
        t(cTag("glow_berry_jam_bottle")).addOptional(ResourceLocation.parse("bakery:glowberry_jam")).addOptional(ResourceLocation.parse("expandeddelight:glow_berry_jelly")).addOptional(ResourceLocation.parse("fruitsdelight:glowberry_jam"));
        t(cTag("glow_berry_juice_bottle")).addOptional(ResourceLocation.parse("expandeddelight:glow_berry_juice")).addOptional(ResourceLocation.parse("hearthandharvest:glow_berry_juice"));
        t(cTag("glow_berry_milkshake_bottle")).addOptional(ResourceLocation.parse("createfood:glow_berry_milkshake_bottle")).addOptional(ResourceLocation.parse("create_dd:glow_berry_milkshake"));
        t(cTag("glow_berry_milkshake_bucket")).addOptional(ResourceLocation.parse("createfood:glow_berry_milkshake_bucket")).addOptional(ResourceLocation.parse("create_dd:glow_berry_milkshake_bucket"));
        t(cTag("green_tea_cookie")).addOptional(ResourceLocation.parse("farmersrespite:green_tea_cookie"));
        t(cTag("ground_strider")).addOptional(ResourceLocation.parse("nethersdelight:ground_strider")).addOptional(ResourceLocation.parse("mynethersdelight:minced_strider"));
        t(cTag("gyro_meat_ingredients")).addOptionalTag(cTag("foods/raw_meats/raw_beef")).addOptionalTag(cTag("foods/raw_meats/raw_pork")).addOptionalTag(cTag("foods/raw_meats/raw_chicken")).addOptionalTag(cTag("foods/raw_meats/raw_rabbit"));
        t(cTag("gyro_mutton_ingredient")).addOptionalTag(cTag("foods/cooked_mutton")).addOptional(ResourceLocation.parse("createfood:gyro_meat_slice"));
        t(cTag("hamburger")).addOptional(ResourceLocation.parse("createfood:hamburger")).addOptional(ResourceLocation.parse("delightfulcreators:incomplete_hamburger"));
        t(cTag("hamburger_onion_lettuce_tomato")).addOptional(ResourceLocation.parse("farmersdelight:hamburger"));

        // Dish slots this mod's line-up leaves to a compat mod, declared so the mapping
        // is written down in one place. Each names a composition we do not register
        // ourselves -- we stop at bacon_sandwich_lettuce, mutton_wrap_lettuce_tomato and
        // have no plain chicken sandwich -- and the foreign item fills it, the same way
        // c:hamburger_onion_lettuce_tomato holds Farmer's Delight's hamburger.
        t(cTag("bacon_sandwich_lettuce_tomato")).addOptional(ResourceLocation.parse("farmersdelight:bacon_sandwich"));
        t(cTag("chicken_sandwich")).addOptional(ResourceLocation.parse("farmersdelight:chicken_sandwich"));
        t(cTag("mutton_wrap_onion_lettuce")).addOptional(ResourceLocation.parse("farmersdelight:mutton_wrap"));

        t(cTag("honey_cookie")).addOptional(ResourceLocation.parse("farmersdelight:honey_cookie"));
        t(cTag("honeyed_apple")).addOptional(ResourceLocation.parse("create:honeyed_apple"));
        t(cTag("honeyed_donut")).addOptional(ResourceLocation.parse("createfood:honeyed_donut")).addOptional(ResourceLocation.parse("create_snt:honey_donut"));
        t(cTag("hot_chocolate")).addOptional(ResourceLocation.parse("createfood:hot_chocolate_bottle"));
        t(cTag("hot_chocolate_bottle")).addOptional(ResourceLocation.parse("createfood:hot_chocolate_bottle")).addOptional(ResourceLocation.parse("create_dd:hot_chocolate")).addOptional(ResourceLocation.parse("create_confectionery:hot_chocolate_bottle"));
        t(cTag("hot_chocolate_bucket")).addOptional(ResourceLocation.parse("createfood:hot_chocolate_bucket")).addOptional(ResourceLocation.parse("create_dd:hot_chocolate_bucket")).addOptional(ResourceLocation.parse("create_confectionery:hot_chocolate_bucket"));
        t(cTag("hot_dark_chocolate")).addOptional(ResourceLocation.parse("createfood:hot_dark_chocolate_bottle"));
        t(cTag("hot_white_chocolate")).addOptional(ResourceLocation.parse("createfood:hot_white_chocolate_bottle"));
        t(cTag("ice_blocks")).addOptional(ResourceLocation.parse("minecraft:ice")).addOptional(ResourceLocation.parse("minecraft:packed_ice")).addOptional(ResourceLocation.parse("minecraft:blue_ice"));
        t(cTag("ice_cream_cone")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:vanilla_cone"));
        t(cTag("macaroni_bowl_cheese")).addOptional(ResourceLocation.parse("hearthandharvest:macaroni_and_cheese"));
        t(cTag("magma_gelatin")).addOptional(ResourceLocation.parse("nethersdelight:magma_gelatin")).addOptional(ResourceLocation.parse("mynethersdelight:hot_cream"));
        t(cTag("marshmallow_dark_chocolate_fudge")).addOptional(ResourceLocation.parse("createfood:marshmallow_chocolate_fudge"));
        t(cTag("marshmallow_stick")).addOptional(ResourceLocation.parse("hearthandharvest:marshmallow_stick"));
        t(cTag("marshmallow_white_chocolate_fudge")).addOptional(ResourceLocation.parse("createfood:marshmallow_chocolate_fudge"));
        t(cTag("mashed_potatoes_bowl")).addOptional(ResourceLocation.parse("hearthandharvest:mashed_potatoes"));
        t(cTag("melon")).addOptional(ResourceLocation.parse("minecraft:melon")).addOptional(ResourceLocation.parse("minecraft:melon_slice"));
        t(cTag("melon_cream_frosting")).addOptional(ResourceLocation.parse("createfood:melon_cream_frosting_bottle"));
        t(cTag("melon_jam")).addOptional(ResourceLocation.parse("hearthandharvest:melon_jam"));
        t(cTag("milk")).addOptional(ResourceLocation.parse("minecraft:milk_bucket"));
        t(cTag("milk_bottle")).addOptional(ResourceLocation.parse("farmersdelight:milk_bottle")).addOptional(ResourceLocation.parse("hearthandharvest:goat_milk_bottle")).addOptional(ResourceLocation.parse("expandeddelight:goat_milk_bottle"));
        t(cTag("milk_buckets")).addOptional(ResourceLocation.parse("meadow:wooden_milk_bucket")).addOptional(ResourceLocation.parse("createfood:milk_powder")).addOptional(ResourceLocation.parse("ubesdelight:milk_powder"));
        t(cTag("milk_powder")).addOptional(ResourceLocation.parse("createfood:milk_powder")).addOptional(ResourceLocation.parse("ubesdelight:milk_powder"));
        t(cTag("milkshake")).addOptional(ResourceLocation.parse("createfood:milkshake_bottle"));
        t(cTag("milkshake_bottle")).addOptional(ResourceLocation.parse("createfood:milkshake_bottle")).addOptional(ResourceLocation.parse("create_dd:vanilla_milkshake"));
        t(cTag("milkshake_bucket")).addOptional(ResourceLocation.parse("createfood:milkshake_bucket")).addOptional(ResourceLocation.parse("create_dd:vanilla_milkshake_bucket"));
        t(cTag("minced_beef")).addOptional(ResourceLocation.parse("farmersdelight:minced_beef")).addOptional(ResourceLocation.parse("farm_and_charm:minced_beef"));
        t(cTag("muffin_base")).addOptional(ResourceLocation.parse("createfood:muffin_base")).addOptional(ResourceLocation.parse("create_snt:muffin"));
        t(cTag("mushrooms")).addOptional(ResourceLocation.parse("createfood:sliced_brown_mushroom")).addOptional(ResourceLocation.parse("createfood:sliced_red_mushroom")).addOptional(ResourceLocation.parse("minecraft:brown_mushroom")).addOptional(ResourceLocation.parse("minecraft:red_mushroom"));
        t(cTag("mutton_sandwich")).addOptional(ResourceLocation.parse("createfood:mutton_sandwich")).addOptional(ResourceLocation.parse("culturalcreators:incomplete_mutton_sandwich"));
        t(cTag("onion")).addOptional(ResourceLocation.parse("createfood:sliced_onion")).addOptional(ResourceLocation.parse("createfood:diced_onion")).addOptional(ResourceLocation.parse("farmersdelight:onion"));
        t(cTag("onion_rings")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:onion_rings"));
        t(cTag("bayberry")).addOptional(ResourceLocation.parse("fruitsdelight:bayberry"));
        t(cTag("bayberry_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:bayberry_jam"));
        t(cTag("blueberry")).addOptional(ResourceLocation.parse("fruitsdelight:blueberry")).addOptional(ResourceLocation.parse("hearthandharvest:blueberries"));
        t(cTag("blueberry_custard_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:blueberry_custard"));
        t(cTag("blueberry_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:blueberry_jam"));
        t(cTag("blueberry_juice_bottle")).addOptional(ResourceLocation.parse("hearthandharvest:blueberry_juice"));
        t(cTag("blueberry_pie")).addOptional(ResourceLocation.parse("hearthandharvest:blueberry_pie"));
        t(cTag("blueberry_pie_slice")).addOptional(ResourceLocation.parse("hearthandharvest:blueberry_pie_slice"));
        t(cTag("cranberry")).addOptional(ResourceLocation.parse("fruitsdelight:cranberry"));
        t(cTag("cranberry_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:cranberry_jam"));
        t(cTag("cranberry_juice_bottle")).addOptional(ResourceLocation.parse("expandeddelight:cranberry_juice"));
        t(cTag("durian")).addOptional(ResourceLocation.parse("fruitsdelight:durian_flesh"));
        t(cTag("durian_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:durian_jam"));
        t(cTag("durian_pie")).addOptional(ResourceLocation.parse("fruitsdelight:durian_pie"));
        t(cTag("fig")).addOptional(ResourceLocation.parse("fruitsdelight:fig"));
        t(cTag("fig_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:fig_jam"));
        t(cTag("hamimelon")).addOptional(ResourceLocation.parse("fruitsdelight:hamimelon")).addOptionalTag(cTag("hamimelon_slice"));
        t(cTag("hamimelon_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:hamimelon_jam"));
        t(cTag("hamimelon_juice_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:hamimelon_juice"));
        t(cTag("hamimelon_popsicle")).addOptional(ResourceLocation.parse("fruitsdelight:hamimelon_popsicle"));
        t(cTag("hamimelon_slice")).addOptional(ResourceLocation.parse("fruitsdelight:hamimelon_slice"));
        t(cTag("hawberry")).addOptional(ResourceLocation.parse("fruitsdelight:hawberry"));
        t(cTag("hawberry_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:hawberry_jam"));
        t(cTag("kiwi")).addOptional(ResourceLocation.parse("fruitsdelight:kiwi"));
        t(cTag("kiwi_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:kiwi_jam"));
        t(cTag("kiwi_juice_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:kiwi_juice"));
        t(cTag("kiwi_popsicle")).addOptional(ResourceLocation.parse("fruitsdelight:kiwi_popsicle"));
        t(cTag("lemon")).addOptional(ResourceLocation.parse("fruitsdelight:lemon")).addOptionalTag(cTag("lemon_slice"));
        t(cTag("lemon_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:lemon_jam"));
        t(cTag("lemon_juice_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:lemon_juice"));
        t(cTag("lemon_slice")).addOptional(ResourceLocation.parse("fruitsdelight:lemon_slice"));
        t(cTag("lychee")).addOptional(ResourceLocation.parse("fruitsdelight:lychee"));
        t(cTag("lychee_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:lychee_jam"));
        t(cTag("mango")).addOptional(ResourceLocation.parse("fruitsdelight:mango"));
        t(cTag("mango_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:mango_jam"));
        t(cTag("mango_milkshake_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:mango_milkshake"));
        t(cTag("mangosteen")).addOptional(ResourceLocation.parse("fruitsdelight:mangosteen"));
        t(cTag("mangosteen_cream_cake")).addOptional(ResourceLocation.parse("fruitsdelight:mangosteen_cake"));
        t(cTag("mangosteen_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:mangosteen_jam"));
        t(cTag("peach")).addOptional(ResourceLocation.parse("fruitsdelight:peach"));
        t(cTag("peach_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:peach_jam"));
        t(cTag("pear")).addOptional(ResourceLocation.parse("fruitsdelight:pear"));
        t(cTag("pear_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:pear_jam"));
        t(cTag("pear_juice_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:pear_juice"));
        t(cTag("persimmon")).addOptional(ResourceLocation.parse("fruitsdelight:persimmon"));
        t(cTag("persimmon_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:persimmon_jam"));
        t(cTag("pineapple")).addOptional(ResourceLocation.parse("fruitsdelight:pineapple")).addOptionalTag(cTag("pineapple_slice"));
        t(cTag("pineapple_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:pineapple_jam"));
        t(cTag("pineapple_pie")).addOptional(ResourceLocation.parse("fruitsdelight:pineapple_pie"));
        t(cTag("pineapple_slice")).addOptional(ResourceLocation.parse("fruitsdelight:pineapple_slice"));
        t(cTag("orange")).addOptional(ResourceLocation.parse("fruitsdelight:orange")).addOptionalTag(cTag("orange_slice"));
        t(cTag("orange_jam_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:orange_jam"));
        t(cTag("orange_juice_bottle")).addOptional(ResourceLocation.parse("fruitsdelight:orange_juice"));
        t(cTag("orange_slice")).addOptional(ResourceLocation.parse("fruitsdelight:orange_slice"));
        t(cTag("paprika")).addOptional(ResourceLocation.parse("createfood:paprika")).addOptional(ResourceLocation.parse("create:cinder_flour"));
        t(cTag("paprika_ingredient")).addOptionalTag(cTag("pepper")).addOptional(ResourceLocation.parse("minecraft:nether_wart"));
        t(cTag("pasta_plate")).addOptional(ResourceLocation.parse("createfood:pasta_plate")).addOptional(ResourceLocation.parse("delightfulcreators:incomplete_pasta_dish"));
        t(cTag("pasta_plate_beef_meatballs")).addOptional(ResourceLocation.parse("createfood:pasta_plate_beef_meatballs")).addOptional(ResourceLocation.parse("delightfulcreators:incomplete_pasta_with_meatballs"));
        t(cTag("pasta_plate_eggplant")).addOptional(ResourceLocation.parse("createfood:pasta_plate_eggplant")).addOptional(ResourceLocation.parse("culturalcreators:incomplete_fried_eggplant_pasta"));
        t(cTag("pasta_plate_mutton_chop")).addOptional(ResourceLocation.parse("createfood:pasta_plate_mutton_chop")).addOptional(ResourceLocation.parse("delightfulcreators:incomplete_pasta_with_mutton_chop"));
        t(cTag("pasta_plate_squid_ink")).addOptional(ResourceLocation.parse("createfood:pasta_plate_squid_ink")).addOptional(ResourceLocation.parse("delightfulcreators:incomplete_squid_ink_pasta"));
        t(cTag("pasta_with_slimeballs")).addOptional(ResourceLocation.parse("frightsdelight:pasta_with_slimeballs"));
        t(cTag("peanut")).addOptional(ResourceLocation.parse("expandeddelight:peanut")).addOptional(ResourceLocation.parse("hearthandharvest:peanut")).addOptional(ResourceLocation.parse("hearthandharvest:roasted_peanuts"));
        t(cTag("peanut_butter")).addOptional(ResourceLocation.parse("expandeddelight:peanut_butter")).addOptional(ResourceLocation.parse("croptopia:peanut_butter")).addOptional(ResourceLocation.parse("hearthandharvest:peanut_butter"));
        t(cTag("peanut_butter_sandwich")).addOptional(ResourceLocation.parse("expandeddelight:peanut_butter_sandwich"));
        t(cTag("pita_dough_ingredients")).addOptional(ResourceLocation.parse("createfood:salt_dough_small")).addOptional(ResourceLocation.parse("createfood:wheat_dough_small"));
        t(cTag("plain_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:gelatin_dessert_block"));
        t(cTag("popcorn")).addOptional(ResourceLocation.parse("culturaldelights:popcorn")).addOptional(ResourceLocation.parse("hearthandharvest:popcorn"));
        t(cTag("pork_meatball_stick")).addOptional(ResourceLocation.parse("createfood:pork_meatball_stick_1")).addOptional(ResourceLocation.parse("createfood:pork_meatball_stick_2")).addOptional(ResourceLocation.parse("createfood:pork_meatball_stick_3"));
        t(cTag("pie_crust")).addOptional(ResourceLocation.parse("farmersdelight:pie_crust"));
        t(cTag("carrot_juice")).addOptional(ResourceLocation.parse("veggiesdelight:carrot_juice"));
        t(cTag("cherry_juice")).addOptional(ResourceLocation.parse("hearthandharvest:cherry_juice"));
        t(cTag("green_grape_juice")).addOptional(ResourceLocation.parse("hearthandharvest:green_grape_juice"));
        t(cTag("red_grape_juice")).addOptional(ResourceLocation.parse("hearthandharvest:red_grape_juice"));
        t(cTag("raspberry_juice")).addOptional(ResourceLocation.parse("hearthandharvest:raspberry_juice"));
        t(cTag("dandelion_juice")).addOptional(ResourceLocation.parse("veggiesdelight:dandelion_juice"));
        t(cTag("green_grape")).addOptional(ResourceLocation.parse("hearthandharvest:green_grapes"));
        t(cTag("red_grape")).addOptional(ResourceLocation.parse("hearthandharvest:red_grapes"));
        t(cTag("raspberry")).addOptional(ResourceLocation.parse("hearthandharvest:raspberry"));
        t(cTag("dandelion")).addOptional(ResourceLocation.parse("veggiesdelight:dandelion")).addOptional(ResourceLocation.parse("veggiesdelight:dandelion_leaf"));
        t(cTag("cherry_blossom")).addOptional(ResourceLocation.parse("minecraft:pink_petals")).addOptional(ResourceLocation.parse("minecraft:cherry_leaves"));
        t(cTag("soul_berry")).addOptional(ResourceLocation.parse("frightsdelight:soul_berry"));
        t(cTag("wither_berry")).addOptional(ResourceLocation.parse("frightsdelight:wither_berry"));
        // End's Delight's chorus cookie is this mod's chorus fruit cookie -- same raw form,
        // same bake -- so it merges rather than getting a second set of routes.
        t(cTag("chorus_fruit_cookie")).addOptional(ResourceLocation.parse("ends_delight:chorus_cookie"));
        t(cTag("chocolate_cake")).addOptional(ResourceLocation.parse("neapolitan:chocolate_cake"));
        t(cTag("chocolate_donut")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:chocolate_donut"));
        t(cTag("honey_cake")).addOptional(ResourceLocation.parse("createaddition:honey_cake"));
        t(cTag("tear_popsicle")).addOptional(ResourceLocation.parse("mynethersdelight:tear_popsicle"));
        t(cTag("cranberry_muffin")).addOptional(ResourceLocation.parse("fruitsdelight:cranberry_muffin"));
        t(cTag("ghast_tear")).addOptional(ResourceLocation.parse("minecraft:ghast_tear"));
        // Ratatouille Fried Delights' puff pastry is flour, sugar and butter -- this mod's
        // sweet dough plus butter -- so it is the same pastry base and merges.
        t(cTag("pastry_base")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:puff_pastry"));
        t(cTag("fried_apple_pie")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:fried_apple_pie"));
        t(cTag("cobweb")).addOptional(ResourceLocation.parse("minecraft:cobweb"));
        t(cTag("rotten_flesh")).addOptional(ResourceLocation.parse("minecraft:rotten_flesh"));
        t(cTag("spider_eye")).addOptional(ResourceLocation.parse("minecraft:spider_eye"));
        t(cTag("slime_apple")).addOptional(ResourceLocation.parse("frightsdelight:apple_slime"));
        t(cTag("rose_hips")).addOptional(ResourceLocation.parse("farmersrespite:rose_hips"));
        t(cTag("dried_chorus_flower")).addOptional(ResourceLocation.parse("ends_delight:dried_chorus_flower"));
        t(cTag("carrot_cream_cake")).addOptional(ResourceLocation.parse("hearthandharvest:carrot_cake")).addOptional(ResourceLocation.parse("veggiesdelight:carrot_cake"));
        t(cTag("coffee_cream_cake")).addOptional(ResourceLocation.parse("farmersrespite:coffee_cake"));
        t(cTag("potato")).addOptional(ResourceLocation.parse("createfood:shredded_potato")).addOptional(ResourceLocation.parse("createfood:sliced_potato")).addOptional(ResourceLocation.parse("minecraft:potato")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:peeled_potato")).addOptional(ResourceLocation.parse("rusticdelight:potato_slices"));
        t(cTag("potato_chips")).addOptional(ResourceLocation.parse("createfood:potato_chips")).addOptional(ResourceLocation.parse("casualnessdelight:potato_chip"));
        t(cTag("powderable_eggs")).addOptional(ResourceLocation.parse("createfood:boiled_egg_peeled")).addOptional(ResourceLocation.parse("farmersdelight:fried_egg"));
        t(cTag("pressed_cocoa")).addOptional(ResourceLocation.parse("ratatouille:cocoa_solids"));
        t(cTag("pumpkin")).addOptional(ResourceLocation.parse("minecraft:pumpkin")).addOptional(ResourceLocation.parse("farmersdelight:pumpkin_slice"));
        t(cTag("pumpkin_pie")).addOptional(ResourceLocation.parse("minecraft:pumpkin_pie")).addOptional(ResourceLocation.parse("delightfulcreators:incomplete_pumpkin_pie"));
        t(cTag("rabbit_meatball_stick")).addOptional(ResourceLocation.parse("createfood:rabbit_meatball_stick_1")).addOptional(ResourceLocation.parse("createfood:rabbit_meatball_stick_2")).addOptional(ResourceLocation.parse("createfood:rabbit_meatball_stick_3"));
        t(cTag("raw_dragon_meat_cuts")).addOptional(ResourceLocation.parse("ends_delight:raw_dragon_meat_cuts"));
        t(cTag("raw_onion_rings")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:breaded_onion_rings"));
        t(cTag("raw_pasta")).addOptional(ResourceLocation.parse("farmersdelight:raw_pasta")).addOptional(ResourceLocation.parse("farm_and_charm:raw_pasta"));
        t(cTag("raw_sausages")).addOptional(ResourceLocation.parse("ratatouille:raw_sausage")).addOptional(ResourceLocation.parse("hearthandharvest:raw_sausage"));
        t(cTag("red_mushroom")).addOptional(ResourceLocation.parse("minecraft:red_mushroom")).addOptional(ResourceLocation.parse("createfood:sliced_red_mushroom"));
        // Recipes ask for c:foods/leafy_green -- 133 files -- and nothing reads c:salad_ingredients,
        // so the lettuces have to reach the former. Nesting runs this way only: pointing
        // salad_ingredients back at leafy_green as well would be a tag cycle.
        t(cTag("foods/leafy_green")).addOptionalTag(cTag("salad_ingredients"));
        t(cTag("salad_ingredients")).addOptional(ResourceLocation.parse("croptopia:lettuce")).addOptional(ResourceLocation.parse("candlelight:lettuce"));
        t(cTag("salt")).addOptionalTag(cTag("dusts/salt")).addOptional(ResourceLocation.parse("hearthandharvest:salt")).addOptional(ResourceLocation.parse("meadow:alpine_salt")).addOptional(ResourceLocation.parse("vegandelight:salt")).addOptional(ResourceLocation.parse("ratatouille:salt"));
        t(cTag("salt_dough")).addOptional(ResourceLocation.parse("ratatouille:salty_dough"));
        t(cTag("sausage")).addOptional(ResourceLocation.parse("createfood:sausages"));
        t(cTag("sausages")).addOptional(ResourceLocation.parse("createfood:sausages")).addOptional(ResourceLocation.parse("createfood:sausage_bits")).addOptional(ResourceLocation.parse("hearthandharvest:cooked_sausage")).addOptional(ResourceLocation.parse("ratatouille:sausage"));
        t(cTag("shakshuka_bowl")).addOptional(ResourceLocation.parse("veggiesdelight:shakshouka"));
        t(cTag("shredded_potato")).addOptional(ResourceLocation.parse("createfood:shredded_potato")).addOptional(ResourceLocation.parse("moredelight:diced_potatoes"));
        t(cTag("sliced_potato")).addOptional(ResourceLocation.parse("createfood:sliced_potato")).addOptional(ResourceLocation.parse("casualnessdelight:potato_slice")).addOptional(ResourceLocation.parse("rusticdelight:potato_slices"));
        t(cTag("sliced_tomato")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:tomato_slices"));
        t(cTag("smore")).addOptional(ResourceLocation.parse("hearthandharvest:smore"));
        t(cTag("snickerdoodle")).addOptional(ResourceLocation.parse("expandeddelight:snickerdoodle"));
        t(cTag("soul_berry_cookie")).addOptional(ResourceLocation.parse("frightsdelight:cookie_soul_berry"));
        t(cTag("spider_eye_cookie")).addOptional(ResourceLocation.parse("frightsdelight:cookie_spidereye"));
        t(cTag("sugar")).addOptional(ResourceLocation.parse("minecraft:sugar")).addOptional(ResourceLocation.parse("createfood:powdered_sugar")).addOptional(ResourceLocation.parse("hearthandharvest:sugar_cubes"));
        t(cTag("sugar_cane")).addOptional(ResourceLocation.parse("minecraft:sugar_cane"));
        t(cTag("sugar_cookie")).addOptional(ResourceLocation.parse("expandeddelight:sugar_cookie"));
        t(cTag("sweet_berry_cookie")).addOptional(ResourceLocation.parse("farmersdelight:sweet_berry_cookie"));
        t(cTag("sweet_dough")).addOptional(ResourceLocation.parse("createfood:sugar_dough")).addOptional(ResourceLocation.parse("bakery:sweet_dough"));
        t(cTag("sweet_potato")).addOptional(ResourceLocation.parse("veggiesdelight:sweet_potato")).addOptional(ResourceLocation.parse("expandeddelight:sweet_potato"));
        t(cTag("sweet_roll")).addOptional(ResourceLocation.parse("create:sweet_roll"));
        t(cTag("syrup_cookie")).addOptional(ResourceLocation.parse("rusticdelight:syrup_cookie")).addOptional(ResourceLocation.parse("hearthandharvest:maple_cookie"));
        t(cTag("taco_shell_ingredient")).addOptionalTag(cTag("pita_bread")).addOptionalTag(cTag("tortilla"));
        t(cTag("toast")).addOptional(ResourceLocation.parse("createfood:toast_slice")).addOptional(ResourceLocation.parse("moredelight:toast"));
        t(cTag("toast_slice")).addOptional(ResourceLocation.parse("createfood:toast_slice")).addOptional(ResourceLocation.parse("moredelight:toast"));
        t(cTag("tomato")).addOptional(ResourceLocation.parse("createfood:diced_tomato")).addOptional(ResourceLocation.parse("createfood:sliced_tomato")).addOptional(ResourceLocation.parse("farmersdelight:tomato")).addOptional(ResourceLocation.parse("candlelight:tomato")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:tomato_ingredient"));
        t(cTag("tomato_sauce")).addOptional(ResourceLocation.parse("farmersdelight:tomato_sauce"));
        t(cTag("tools/knife")).addOptionalTag(modTag("farmersdelight", "tools/knives"));
        t(cTag("tortilla")).addOptional(ResourceLocation.parse("culturaldelights:tortilla")).addOptional(ResourceLocation.parse("hearthandharvest:tortilla"));
        t(cTag("tortilla_chip_bowl")).addOptional(ResourceLocation.parse("createfood:pita_chip_bowl"));
        t(cTag("tortilla_chips")).addOptional(ResourceLocation.parse("culturaldelights:tortilla_chips"));
        t(cTag("turnip")).addOptional(ResourceLocation.parse("veggiesdelight:turnip"));
        t(cTag("ube_cookie")).addOptional(ResourceLocation.parse("ubesdelight:cookie_ube"));
        t(cTag("ube_cream_frosting")).addOptional(ResourceLocation.parse("createfood:ube_cream_frosting_bottle"));
        t(cTag("vegetable_oil")).addOptional(ResourceLocation.parse("createfood:vegetable_oil_bucket")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:sunflower_oil")).addOptional(ResourceLocation.parse("hearthandharvest:cooking_oil")).addOptional(ResourceLocation.parse("rusticdelight:cooking_oil")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:sunflower_seed_oil_bottle")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:sunflower_oil_bucket"));
        t(cTag("vegetables")).addOptional(ResourceLocation.parse("minecraft:carrot")).addOptional(ResourceLocation.parse("minecraft:potato")).addOptional(ResourceLocation.parse("farmersdelight:tomato")).addOptional(ResourceLocation.parse("farmersdelight:onion")).addOptional(ResourceLocation.parse("veggiesdelight:turnip")).addOptional(ResourceLocation.parse("veggiesdelight:zucchini")).addOptional(ResourceLocation.parse("veggiesdelight:zucchini_slice")).addOptional(ResourceLocation.parse("veggiesdelight:broccoli")).addOptional(ResourceLocation.parse("veggiesdelight:cauliflower")).addOptional(ResourceLocation.parse("veggiesdelight:cauliflower_floret")).addOptional(ResourceLocation.parse("veggiesdelight:garlic")).addOptional(ResourceLocation.parse("veggiesdelight:garlic_clove")).addOptional(ResourceLocation.parse("veggiesdelight:bellpepper")).addOptional(ResourceLocation.parse("veggiesdelight:sweet_potato")).addOptional(ResourceLocation.parse("expandeddelight:sweet_potato")).addOptional(ResourceLocation.parse("expandeddelight:asparagus"));
        t(cTag("vegetables/carrot")).addOptional(ResourceLocation.parse("createfood:sliced_carrot"));
        t(cTag("vegetables/ginger")).addOptional(ResourceLocation.parse("ubesdelight:ginger")).addOptional(ResourceLocation.parse("culturaldelights:ginger"));
        t(cTag("crops/ginger")).addOptionalTag(cTag("vegetables/ginger"));
        t(cTag("vegetables/potato")).addOptional(ResourceLocation.parse("createfood:sliced_potato"));
        t(cTag("vegetables/ube")).addOptional(ResourceLocation.parse("ubesdelight:ube"));
        t(cTag("vinegar_bottle")).addOptional(ResourceLocation.parse("createfood:vinegar_bottle")).addOptional(ResourceLocation.parse("dumplings_delight:vinegar"));
        t(cTag("waffle")).addOptional(ResourceLocation.parse("hearthandharvest:waffle"));
        t(cTag("waffle_cone")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:cone"));
        t(cTag("warped_fungus")).addOptional(ResourceLocation.parse("minecraft:warped_fungus")).addOptional(ResourceLocation.parse("createfood:sliced_warped_fungus"));
        t(cTag("wheat_dough")).addOptional(ResourceLocation.parse("create:dough")).addOptional(ResourceLocation.parse("farmersdelight:wheat_dough"));
        t(cTag("zucchini")).addOptional(ResourceLocation.parse("veggiesdelight:zucchini")).addOptional(ResourceLocation.parse("veggiesdelight:zucchini_slice")).addOptional(ResourceLocation.parse("veggiesdelight:roasted_zucchini"));
        t(modTag("farmersdelight", "cabbage_roll_ingredients")).addOptional(ResourceLocation.parse("createfood:tropical_fish_slice"));
        t(modTag("fruitsdelight", "jam")).addOptional(ResourceLocation.parse("createfood:apple_jam_bottle")).addOptional(ResourceLocation.parse("createfood:berry_jam_bottle")).addOptional(ResourceLocation.parse("createfood:chorus_fruit_jam_bottle")).addOptional(ResourceLocation.parse("createfood:glow_berry_jam_bottle")).addOptional(ResourceLocation.parse("createfood:melon_jam_bottle"));
        t(modTag("hearthandharvest", "jelly")).addOptional(ResourceLocation.parse("createfood:apple_jam_bottle")).addOptional(ResourceLocation.parse("createfood:berry_jam_bottle")).addOptional(ResourceLocation.parse("createfood:chorus_fruit_jam_bottle")).addOptional(ResourceLocation.parse("createfood:glow_berry_jam_bottle")).addOptional(ResourceLocation.parse("createfood:melon_jam_bottle"));
        t(modTag("ratatouille_fried_delights", "ratatouille_burger_ingredients")).addOptional(ResourceLocation.parse("createfood:bun")).addOptional(ResourceLocation.parse("createfood:cheese_slice")).addOptional(ResourceLocation.parse("createfood:chicken_patty")).addOptional(ResourceLocation.parse("createfood:sausage_patty")).addOptional(ResourceLocation.parse("createfood:sliced_tomato")).addOptional(ResourceLocation.parse("createfood:vegetable_patty"));
        t(modTag("rusticdelight", "batter")).addOptionalTag(cTag("batter_bowl"));
        t(modTag("rusticdelight", "cooking_oil")).addOptional(ResourceLocation.parse("createfood:vegetable_oil_bucket"));
        t(modTag("rusticdelight", "syrup")).addOptional(ResourceLocation.parse("createfood:cane_syrup_bottle"));
        t(modTag("minecraft", "fishes")).addOptional(ResourceLocation.parse("createfood:cooked_tropical_fish"));
        t(modTag("minecraft", "cat_food")).addOptional(ResourceLocation.parse("createfood:tropical_fish_slice"));

        // Tags another mod owns that our recipe overrides now consume. Adding our own
        // equivalent is what lets the foreign recipe accept a Create: Food item, and
        // declaring the tag here is also what lets `cfood audit` tell a genuinely
        // unpopulated tag from one a dependency populates out of our sight.
        t(cTag("flours")).addOptional(ResourceLocation.parse("createfood:corn_flour")).addOptionalTag(cTag("flours/wheat"));
        t(cTag("jams/sweet_berry")).addOptional(ResourceLocation.parse("createfood:berry_jam_bottle"));
        t(cTag("jams/glow_berry")).addOptional(ResourceLocation.parse("createfood:glow_berry_jam_bottle"));
        t(cTag("tea_ingredients/sweet/weak")).addOptional(ResourceLocation.parse("createfood:powdered_sugar")).addOptional(ResourceLocation.parse("createfood:brown_sugar"));
        // Create Confectionery's marshmallow is ours under another name; c:marshmallow
        // held only ours, so its s'more and hot chocolate recipes could not see it.
        t(cTag("marshmallow")).addOptional(ResourceLocation.parse("create_confectionery:marshmallow"));

        // Tags a dependency owns and populates, which no file here declared. Naming them
        // is what lets `cfood audit` tell a tag a dependency fills out of our sight from
        // one nothing populates at all -- the two look identical to a repo-scoped check,
        // and the audit was reporting these seven as dead. c:salt and our cheese slice are
        // the same substances the foreign tags already hold, so they join; the rest have no
        // Create: Food counterpart and are declared empty.
        t(cTag("ice"));
        t(cTag("flour"));
        t(cTag("raw_meat"));
        t(cTag("raw_meat_delight"));
        t(cTag("jams/cranberry"));
        t(cTag("salts")).addOptional(ResourceLocation.parse("createfood:salt"));
        t(cTag("cheese")).addOptional(ResourceLocation.parse("createfood:cheese_slice"));

        // c:vegetable_oil holds bottles and buckets together, because recipes want either.
        // Almost Unified cannot be pointed at it for that reason -- it would merge a 250mB
        // bottle with a 1000mB bucket -- so the unifiable halves are split out here. This
        // is what the _compat suffix is for: the narrow, same-item view of a tag whose wide
        // form exists for recipes. Ratatouille Fried Delights' bare `sunflower_oil` is in
        // neither, because its container is not stated anywhere in its data.
        t(cTag("vegetable_oil_bottle_compat"))
                .addOptional(ResourceLocation.parse("createfood:vegetable_oil_bottle"))
                .addOptional(ResourceLocation.parse("hearthandharvest:cooking_oil"))
                .addOptional(ResourceLocation.parse("rusticdelight:cooking_oil"))
                .addOptional(ResourceLocation.parse("ratatouille_fried_delights:sunflower_seed_oil_bottle"));
        t(cTag("vegetable_oil_bucket_compat"))
                .addOptional(ResourceLocation.parse("createfood:vegetable_oil_bucket"))
                .addOptional(ResourceLocation.parse("ratatouille_fried_delights:sunflower_oil_bucket"));

        // Delightful Creators ships these four under the 1.20 path, so none load on 1.21.
        t(cTag("bale")).addOptional(ResourceLocation.parse("minecraft:hay_block")).addOptional(ResourceLocation.parse("farmersdelight:rice_bale"));
        t(cTag("bone_broth_ingredients")).addOptional(ResourceLocation.parse("minecraft:glow_berries")).addOptional(ResourceLocation.parse("minecraft:glow_lichen")).addOptional(ResourceLocation.parse("minecraft:hanging_roots")).addOptionalTag(cTag("mushrooms"));
        t(cTag("dumplings_ingredients")).addOptional(ResourceLocation.parse("minecraft:beef")).addOptional(ResourceLocation.parse("minecraft:chicken")).addOptional(ResourceLocation.parse("minecraft:porkchop")).addOptional(ResourceLocation.parse("farmersdelight:bacon")).addOptional(ResourceLocation.parse("farmersdelight:chicken_cuts")).addOptional(ResourceLocation.parse("farmersdelight:minced_beef")).addOptionalTag(cTag("mushrooms"));
        t(cTag("mushroom_rice_ingredients")).addOptionalTag(cTag("carrot")).addOptionalTag(cTag("potato"));
        // Delightful Creators ships its own pumpkin pie slice; treat it as ours.
        t(cTag("pumpkin_pie_slice")).addOptional(ResourceLocation.parse("delightfulcreators:pumpkin_pie_slice")).addOptional(ResourceLocation.parse("farmersdelight:pumpkin_pie_slice"));

        // Container counterparts of c:any_milk, for the hand routes that name a
        // bottle or bucket rather than a fluid. The per-item c:<id> tags above are
        // generated; an aggregate over two of them has to be written out.
        t(cTag("any_milk_bottle")).addOptionalTag(cTag("milk_bottle")).addOptionalTag(cTag("skim_milk_bottle"));
        t(cTag("any_milk_bucket")).addOptionalTag(cTag("buckets/milk")).addOptionalTag(cTag("skim_milk_bucket"));

        // Slots in Farmer's Delight cooking recipes that accept any of several tags.
        // Its 1.21 codec rejects the bare nested-ingredient array, and the replacement it
        // uses itself, neoforge:compound, is NeoForge-only -- these recipes live in the
        // shared common resources, so the slot has to collapse to one tag.
        t(cTag("stew_potato_ingredients")).addOptionalTag(cTag("potato")).addOptionalTag(cTag("sweet_potato"));
        t(cTag("cabbage_roll_ingredients")).addOptionalTag(cTag("foods/raw_meat")).addOptionalTag(cTag("foods/raw_fish")).addOptionalTag(cTag("foods/vegetable")).addOptionalTag(cTag("mushrooms"));

        // Members missing from tags that already exist. Each of these blocked something
        // concrete: c:tomato without Kaleidoscope Cookery's tomato is not a superset of
        // c:crops/tomato, so the narrow-to-wide widening its recipes want cannot be
        // justified, and c:cooked_rice held one item, which makes any override naming it
        // a no-op.
        t(cTag("tomato")).addOptional(ResourceLocation.parse("kaleidoscope_cookery:tomato"));
        t(cTag("crops/tomato")).addOptional(ResourceLocation.parse("kaleidoscope_cookery:tomato"));
        t(cTag("cooked_rice")).addOptional(ResourceLocation.parse("kaleidoscope_cookery:cooked_rice"));
        t(cTag("lemon")).addOptional(ResourceLocation.parse("expandeddelight:lemon"));
        t(cTag("blueberry_jam_bottle")).addOptional(ResourceLocation.parse("hearthandharvest:blueberry_jam"));
        t(cTag("mashed_potatoes_bowl")).addOptional(ResourceLocation.parse("veggiesdelight:mashed_potatoes"));

        // Items Create: Food also ships, under another mod's name. The c:<id> tag is
        // generated from our own registration, so only the foreign half is listed here.
        t(cTag("meat_pie")).addOptional(ResourceLocation.parse("kaleidoscope_cookery:meat_pie"));
        t(cTag("boiled_egg")).addOptional(ResourceLocation.parse("mynethersdelight:boiled_egg"));
        t(cTag("raw_apple_pie")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:raw_apple_pie"));
        t(cTag("chorus_fruit_pie")).addOptional(ResourceLocation.parse("ends_delight:chorus_fruit_pie"));
        t(cTag("chorus_fruit_pie_slice")).addOptional(ResourceLocation.parse("ends_delight:chorus_fruit_pie_slice"));
        t(cTag("chorus_fruit_popsicle")).addOptional(ResourceLocation.parse("ends_delight:chorus_fruit_popsicle"));

        // Two other mods duplicating each other, with Create: Food in neither. Nothing
        // here changes a recipe of ours; the tag exists so the published Almost Unified
        // config has something to point at, which is the only way a pack running both
        // mods ends up with one item instead of two.
        t(cTag("ketchup")).addOptional(ResourceLocation.parse("create_bic_bit:ketchup")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:ketchup"));
        t(cTag("ketchup_bucket")).addOptional(ResourceLocation.parse("create_bic_bit:ketchup_bucket")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:ketchup_bucket"));
        t(cTag("mayonnaise")).addOptional(ResourceLocation.parse("create_bic_bit:mayonnaise")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:mayonnaise"));
        t(cTag("mayonnaise_bucket")).addOptional(ResourceLocation.parse("create_bic_bit:mayonnaise_bucket")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:mayonnaise_bucket"));
        t(cTag("cotton")).addOptional(ResourceLocation.parse("hearthandharvest:cotton")).addOptional(ResourceLocation.parse("rusticdelight:cotton"));
        t(cTag("wild_cotton")).addOptional(ResourceLocation.parse("hearthandharvest:wild_cotton")).addOptional(ResourceLocation.parse("rusticdelight:wild_cotton"));
        t(cTag("wild_peanuts")).addOptional(ResourceLocation.parse("expandeddelight:wild_peanuts")).addOptional(ResourceLocation.parse("hearthandharvest:wild_peanuts"));
        t(cTag("wild_garlic")).addOptional(ResourceLocation.parse("ubesdelight:wild_garlic")).addOptional(ResourceLocation.parse("veggiesdelight:wild_garlic"));
        t(cTag("wild_bell_peppers")).addOptional(ResourceLocation.parse("rusticdelight:wild_bell_peppers")).addOptional(ResourceLocation.parse("veggiesdelight:wild_bellpeppers"));
        t(cTag("wild_sweet_potato")).addOptional(ResourceLocation.parse("expandeddelight:wild_sweet_potato")).addOptional(ResourceLocation.parse("veggiesdelight:wild_sweet_potatoes"));
        t(cTag("sweet_potato_crate")).addOptional(ResourceLocation.parse("expandeddelight:sweet_potato_crate")).addOptional(ResourceLocation.parse("veggiesdelight:sweet_potato_crate"));
        t(cTag("baked_sweet_potato")).addOptional(ResourceLocation.parse("expandeddelight:baked_sweet_potato")).addOptional(ResourceLocation.parse("veggiesdelight:baked_sweet_potato"));
        t(cTag("rice_panicle")).addOptional(ResourceLocation.parse("farmersdelight:rice_panicle")).addOptional(ResourceLocation.parse("kaleidoscope_cookery:rice_panicle"));
        t(cTag("straw_bale")).addOptional(ResourceLocation.parse("farmersdelight:straw_bale")).addOptional(ResourceLocation.parse("kaleidoscope_cookery:straw_block"));
        t(cTag("scarecrow")).addOptional(ResourceLocation.parse("hearthandharvest:scarecrow")).addOptional(ResourceLocation.parse("kaleidoscope_cookery:scarecrow"));
        t(cTag("stove")).addOptional(ResourceLocation.parse("farmersdelight:stove")).addOptional(ResourceLocation.parse("kaleidoscope_cookery:stove"));
        t(cTag("cask")).addOptional(ResourceLocation.parse("expandeddelight:cask")).addOptional(ResourceLocation.parse("hearthandharvest:cask"));
        t(cTag("rope")).addOptional(ResourceLocation.parse("create:rope")).addOptional(ResourceLocation.parse("farmersdelight:rope"));
        t(cTag("elote")).addOptional(ResourceLocation.parse("culturaldelights:elote")).addOptional(ResourceLocation.parse("hearthandharvest:elote"));
        t(cTag("calamari_roll")).addOptional(ResourceLocation.parse("culturaldelights:calamari_roll")).addOptional(ResourceLocation.parse("rusticdelight:calamari_roll"));
        t(cTag("pancake")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:pancake")).addOptional(ResourceLocation.parse("rusticdelight:pancake"));
        t(cTag("mead")).addOptional(ResourceLocation.parse("brewinandchewin:mead")).addOptional(ResourceLocation.parse("hearthandharvest:mead"));
        t(cTag("spicy_curry")).addOptional(ResourceLocation.parse("culturaldelights:spicy_curry")).addOptional(ResourceLocation.parse("mynethersdelight:spicy_curry"));
        t(cTag("blueberry_muffin")).addOptional(ResourceLocation.parse("fruitsdelight:blueberry_muffin")).addOptional(ResourceLocation.parse("hearthandharvest:blueberry_muffin"));
        t(cTag("coffee")).addOptional(ResourceLocation.parse("farmersrespite:coffee")).addOptional(ResourceLocation.parse("rusticdelight:coffee"));
        t(cTag("sunflower_seeds")).addOptional(ResourceLocation.parse("create_bic_bit:sunflower_seeds")).addOptional(ResourceLocation.parse("hearthandharvest:sunflower_seeds")).addOptional(ResourceLocation.parse("ratatouille_fried_delights:sunflower_seeds"));
        t(cTag("fried_egg")).addOptional(ResourceLocation.parse("farmersdelight:fried_egg")).addOptional(ResourceLocation.parse("kaleidoscope_cookery:fried_egg"));
        t(cTag("apple_cider")).addOptional(ResourceLocation.parse("farmersdelight:apple_cider")).addOptional(ResourceLocation.parse("farmersrespite:apple_cider"));
        t(cTag("apple_cider_bucket")).addOptional(ResourceLocation.parse("delightfulcreators:apple_cider_bucket")).addOptional(ResourceLocation.parse("farmersrespite:apple_cider_bucket"));
        t(cTag("hot_cocoa")).addOptional(ResourceLocation.parse("farmersdelight:hot_cocoa")).addOptional(ResourceLocation.parse("farmersrespite:hot_cocoa"));
        t(cTag("hot_cocoa_bucket")).addOptional(ResourceLocation.parse("delightfulcreators:hot_cocoa_bucket")).addOptional(ResourceLocation.parse("farmersrespite:hot_cocoa_bucket"));
        t(cTag("melon_juice")).addOptional(ResourceLocation.parse("farmersdelight:melon_juice")).addOptional(ResourceLocation.parse("farmersrespite:melon_juice")).addOptional(ResourceLocation.parse("delightfulcreators:melon_juice")).addOptional(ResourceLocation.parse("hearthandharvest:melon_juice"));
        t(cTag("melon_juice_bucket")).addOptional(ResourceLocation.parse("delightfulcreators:melon_juice_bucket")).addOptional(ResourceLocation.parse("farmersrespite:melon_juice_bucket")).addOptional(ResourceLocation.parse("ratatouille:melon_juice_fluid_bucket"));
    }

    private static TagKey<Item> cTag(String id) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", id));
    }

    private static TagKey<Item> modTag(String namespace, String id) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, id));
    }

    private DedupedTagAppender.Appender<Item> t(TagKey<Item> key) {
        return appender.tag(key);
    }

}