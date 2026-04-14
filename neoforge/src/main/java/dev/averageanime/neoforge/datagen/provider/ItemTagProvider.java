package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModBlocks;
import dev.averageanime.neoforge.block.ModFluids;
import dev.averageanime.neoforge.block.type.fluid.FluidEntry;
import dev.averageanime.neoforge.item.ModItems;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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

    public ItemTagProvider(PackOutput output,
                           CompletableFuture<HolderLookup.Provider> lookupProvider,
                           ExistingFileHelper existingFileHelper) {
        super(output, Registries.ITEM, lookupProvider, CommonClass.ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        ModItems.ITEMS.getEntries().forEach(holder -> {
            String id = holder.getId().getPath();
            tag(cTag(id)).addOptional(ResourceLocation.fromNamespaceAndPath(CommonClass.ID, id));
            if (id.endsWith("_bottle")) {
                String baseId = id.substring(0, id.length() - "_bottle".length());
                tag(cTag(baseId)).addOptional(ResourceLocation.fromNamespaceAndPath(CommonClass.ID, id));
            }
        });

        ModBlocks.BLOCKS.getEntries().forEach(holder -> {
            String id = holder.getId().getPath();
            tag(cTag(id)).addOptional(ResourceLocation.fromNamespaceAndPath(CommonClass.ID, id));
        });

        for (Field field : ModFluids.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            try {
                Object value = field.get(null);
                if (value instanceof FluidEntry.FluidType fluidType) {
                    DeferredItem<?> bucket = fluidType.BUCKET;
                    String id = bucket.getId().getPath();
                    tag(cTag(id)).addOptional(ResourceLocation.fromNamespaceAndPath(CommonClass.ID, id));
                }
            } catch (IllegalAccessException ignored) {}
        }
        tag(cTag("foods/raw_fish")).addOptional(ResourceLocation.parse("createfood:tropical_fish_slice"));
        tag(cTag("foods/raw_tropical_fish")).addOptional(ResourceLocation.parse("minecraft:tropical_fish")).addOptional(ResourceLocation.parse("createfood:tropical_fish_slice"));
        tag(cTag("foods/safe_raw_fish")).addOptional(ResourceLocation.parse("createfood:tropical_fish_slice"));
        tag(cTag("foods/cooked_fish")).addOptional(ResourceLocation.parse("createfood:cooked_tropical_fish")).addOptional(ResourceLocation.parse("createfood:cooked_tropical_fish_slice"));
        tag(cTag("foods/cooked_tropical_fish")).addOptional(ResourceLocation.parse("createfood:cooked_tropical_fish")).addOptional(ResourceLocation.parse("createfood:cooked_tropical_fish_slice"));

        tag(modTag("farmersdelight", "cabbage_roll_ingredients")).addOptional(ResourceLocation.parse("createfood:tropical_fish_slice"));
        tag(modTag("minecraft", "fishes")).addOptional(ResourceLocation.parse("createfood:cooked_tropical_fish"));
        tag(cTag("honeyed_apple")).addOptional(ResourceLocation.parse("create:honeyed_apple"));
        tag(cTag("tortilla_chip_bowl")).addOptional(ResourceLocation.parse("createfood:pita_chip_bowl"));

        tag(cTag("apple")).addOptional(ResourceLocation.parse("minecraft:apple")).addOptional(ResourceLocation.parse("createfood:apple_slice"));
        tag(cTag("apple_jam_bottle")).addOptional(ResourceLocation.parse("bakery:apple_jam"));
        tag(cTag("apple_juice_bottle")).addOptional(ResourceLocation.parse("expandeddelight:apple_juice"));
        tag(cTag("apple_juice_bottle_compat")).addOptional(ResourceLocation.parse("createfood:apple_juice_bottle"));
        tag(cTag("apple_slice")).addOptional(ResourceLocation.parse("create_deepfried:apple_slices"));
        tag(cTag("bacon_sandwich")).addOptional(ResourceLocation.parse("delightfulcreators:incomplete_bacon_sandwich"));
        tag(cTag("bar_of_chocolate")).addOptional(ResourceLocation.parse("create:bar_of_chocolate")).addOptional(ResourceLocation.parse("candlelight:chocolate"));
        tag(cTag("beef_meatball_stick")).addOptional(ResourceLocation.parse("createfood:beef_meatball_stick_1")).addOptional(ResourceLocation.parse("createfood:beef_meatball_stick_2")).addOptional(ResourceLocation.parse("createfood:beef_meatball_stick_3"));
        tag(cTag("beetroot")).addOptional(ResourceLocation.parse("minecraft:beetroot")).addOptional(ResourceLocation.parse("createfood:sliced_beetroot")).addOptional(ResourceLocation.parse("createfood:shredded_beetroot"));
        tag(cTag("berry_jam_bottle")).addOptional(ResourceLocation.parse("bakery:sweetberry_jam")).addOptional(ResourceLocation.parse("expandeddelight:sweet_berry_jelly"));
        tag(cTag("berry_jam_bottle_compat")).addOptional(ResourceLocation.parse("createfood:berry_jam_bottle"));
        tag(cTag("berry_juice_bottle")).addOptional(ResourceLocation.parse("expandeddelight:sweet_berry_juice"));
        tag(cTag("berry_juice_bottle_compat")).addOptional(ResourceLocation.parse("createfood:berry_juice_bottle"));
        tag(cTag("berry_milkshake_bottle")).addOptional(ResourceLocation.parse("beachparty:sweetberry_milkshake")).addOptional(ResourceLocation.parse("create_dd:strawberry_milkshake"));
        tag(cTag("berry_milkshake_bucket")).addOptional(ResourceLocation.parse("create_dd:strawberry_milkshake_bucket"));
        tag(cTag("bowl")).addOptional(ResourceLocation.parse("minecraft:bowl"));
        tag(cTag("bread_fried_egg")).addOptional(ResourceLocation.parse("delightfulcreators:incomplete_egg_sandwich"));
        tag(cTag("bread_slice")).addOptional(ResourceLocation.parse("moredelight:bread_slice"));
        tag(cTag("brown_mushroom")).addOptional(ResourceLocation.parse("minecraft:brown_mushroom")).addOptional(ResourceLocation.parse("createfood:sliced_brown_mushroom"));
        tag(cTag("brown_sugar")).addOptional(ResourceLocation.parse("ubesdelight:brown_sugar"));
        tag(cTag("bun")).addOptional(ResourceLocation.parse("bakery:bun"));
        tag(cTag("butter")).addOptional(ResourceLocation.parse("croptopia:butter"));
        tag(cTag("butter_compat")).addOptional(ResourceLocation.parse("createfood:butter"));
        tag(cTag("cake_base")).addOptional(ResourceLocation.parse("createadditions:cake_base_baked")).addOptional(ResourceLocation.parse("bakery:blank_cake"));
        tag(cTag("caramel_bucket")).addOptional(ResourceLocation.parse("create_dd:caramel_bucket"));
        tag(cTag("carrot")).addOptional(ResourceLocation.parse("createfood:sliced_carrot")).addOptional(ResourceLocation.parse("minecraft:carrot")).addOptional(ResourceLocation.parse("createfood:shredded_carrot"));
        tag(cTag("cheese_block")).addOptional(ResourceLocation.parse("meadow:cheese_block")).addOptional(ResourceLocation.parse("casualnessdelight:cheese_wheel"));
        tag(cTag("cheese_slice")).addOptional(ResourceLocation.parse("meadow:piece_of_cheese")).addOptional(ResourceLocation.parse("casualnessdelight:cheese_wheel_slice"));
        tag(cTag("cheeses")).addOptional(ResourceLocation.parse("createfood:cheese_slice")).addOptional(ResourceLocation.parse("brewinandchewin:flaxen_cheese_wedge")).addOptional(ResourceLocation.parse("expandeddelight:cheese_slice")).addOptional(ResourceLocation.parse("meadow:piece_of_cheese")).addOptional(ResourceLocation.parse("casualness_delight:cheese_wheel_slice")).addOptional(ResourceLocation.parse("create_bic_bit:unripe_cheese_wedge")).addOptional(ResourceLocation.parse("create_bic_bit:young_cheese_wedge")).addOptional(ResourceLocation.parse("create_bic_bit:aged_cheese_wedge"));
        tag(cTag("chicken_nuggets")).addOptional(ResourceLocation.parse("create_deepfried:chicken_nuggets"));
        tag(cTag("chili_pepper")).addOptional(ResourceLocation.parse("expandeddelight:chili_pepper")).addOptional(ResourceLocation.parse("croptopia:chile_pepper")).addOptional(ResourceLocation.parse("rusticdelight:bell_pepper_red")).addOptional(ResourceLocation.parse("rusticdelight:roasted_bell_pepper_red"));
        tag(cTag("chocolate_chip_chocolate_cookie")).addOptional(ResourceLocation.parse("expandeddelight:chocolate_chip_chocolate_cookie"));
        tag(cTag("chocolate_milkshake_bottle")).addOptional(ResourceLocation.parse("beachparty:chocolate_milkshake")).addOptional(ResourceLocation.parse("create_dd:chocolate_milkshake"));
        tag(cTag("chocolate_milkshake_bucket")).addOptional(ResourceLocation.parse("create_dd:chocolate_milkshake_bucket"));
        tag(cTag("chocolate_toast")).addOptional(ResourceLocation.parse("moredelight:chocolate_toast"));
        tag(cTag("chorus_cookie")).addOptional(ResourceLocation.parse("ends_delight:chorus_cookie"));
        tag(cTag("chorus_fruit")).addOptional(ResourceLocation.parse("minecraft:chorus_fruit")).addOptional(ResourceLocation.parse("createfood:chorus_fruit_slice"));
        tag(cTag("chorus_fruit_juice_bottle")).addOptional(ResourceLocation.parse("endersdelight:chorus_juice"));
        tag(cTag("cinnamon_sweet_roll")).addOptional(ResourceLocation.parse("expandeddelight:sweet_roll"));
        tag(cTag("cinnamon_sweet_roll_berry")).addOptional(ResourceLocation.parse("expandeddelight:berry_sweet_roll"));
        tag(cTag("cinnamon_sweet_roll_glow_berry")).addOptional(ResourceLocation.parse("expandeddelight:glow_berry_sweet_roll"));
        tag(cTag("coffee_beans")).addOptional(ResourceLocation.parse("rusticdelight:coffee_beans"));
        tag(cTag("condensed_milk_bottle")).addOptional(ResourceLocation.parse("ubesdelight:condensed_milk_bottle"));
        tag(cTag("condensed_milk_bucket")).addOptional(ResourceLocation.parse("create_dd:condense_milk_bucket"));
        tag(cTag("container")).addOptional(ResourceLocation.parse("minecraft:bucket")).addOptional(ResourceLocation.parse("minecraft:stick"));
        tag(cTag("cooked_bacon")).addOptional(ResourceLocation.parse("farmersdelight:cooked_bacon"));
        tag(cTag("cooked_beef")).addOptional(ResourceLocation.parse("minecraft:cooked_beef")).addOptional(ResourceLocation.parse("farmersdelight:beef_patty")).addOptional(ResourceLocation.parse("vegandelight:tofu_patty"));
        tag(cTag("cooked_chicken")).addOptional(ResourceLocation.parse("farmersdelight:cooked_chicken_cuts")).addOptional(ResourceLocation.parse("minecraft:cooked_chicken"));
        tag(cTag("cooked_eggplant")).addOptional(ResourceLocation.parse("culturaldelights:smoked_eggplant")).addOptional(ResourceLocation.parse("culturaldelights:smoked_cut_eggplant"));
        tag(cTag("cooked_eggs")).addOptional(ResourceLocation.parse("farmersdelight:fried_egg"));
        tag(cTag("cooked_fishes")).addOptional(ResourceLocation.parse("minecraft:cooked_salmon")).addOptional(ResourceLocation.parse("farmersdelight:cooked_salmon_slice")).addOptional(ResourceLocation.parse("minecraft:cooked_cod")).addOptional(ResourceLocation.parse("farmersdelight:cooked_cod_slice")).addOptional(ResourceLocation.parse("createfood:cooked_tropical_fish")).addOptional(ResourceLocation.parse("createfood:cooked_tropical_fish_slice"));
        tag(cTag("cooked_mutton")).addOptional(ResourceLocation.parse("minecraft:cooked_mutton")).addOptional(ResourceLocation.parse("farmersdelight:cooked_mutton_chops"));
        tag(cTag("cooked_pasta")).addOptional(ResourceLocation.parse("createfood:pasta"));
        tag(cTag("cooked_pork")).addOptional(ResourceLocation.parse("croptopia:cooked_bacon")).addOptional(ResourceLocation.parse("farmersdelight:cooked_bacon")).addOptional(ResourceLocation.parse("minecraft:cooked_porkchop")).addOptional(ResourceLocation.parse("createfood:bacon_bits"));
        tag(cTag("cooked_rabbit")).addOptional(ResourceLocation.parse("minecraft:cooked_rabbit")).addOptional(ResourceLocation.parse("createfood:cooked_rabbit_cuts")).addOptional(ResourceLocation.parse("createfood:rabbit_jerky"));
        tag(cTag("cooked_rice")).addOptional(ResourceLocation.parse("farmersdelight:cooked_rice"));
        tag(cTag("corn")).addOptional(ResourceLocation.parse("culturaldelights:corn_cob"));
        tag(cTag("corn_dough")).addOptional(ResourceLocation.parse("culturaldelights:corn_dough"));
        tag(cTag("corn_kernel")).addOptional(ResourceLocation.parse("culturaldelights:corn_kernels"));
        tag(cTag("cream_donut")).addOptional(ResourceLocation.parse("create_snt:sweet_donut"));
        tag(cTag("crimson_fungus")).addOptional(ResourceLocation.parse("minecraft:crimson_fungus")).addOptional(ResourceLocation.parse("createfood:sliced_crimson_fungus"));
        tag(cTag("crushable_cookies")).addOptional(ResourceLocation.parse("minecraft:cookie")).addOptional(ResourceLocation.parse("createfood:butterscotch_chip_cookie")).addOptional(ResourceLocation.parse("createfood:caramel_chip_cookie")).addOptional(ResourceLocation.parse("createfood:dark_chocolate_chip_cookie")).addOptional(ResourceLocation.parse("createfood:toffee_chip_cookie")).addOptional(ResourceLocation.parse("createfood:white_chocolate_chip_cookie")).addOptional(ResourceLocation.parse("farmersdelight:honey_cookie"));
        tag(cTag("donut_base")).addOptional(ResourceLocation.parse("create_snt:donut"));
        tag(cTag("dough")).addOptional(ResourceLocation.parse("farmersdelight:wheat_dough"));
        tag(cTag("dried_coffee_beans")).addOptional(ResourceLocation.parse("rusticdelight:roasted_coffee_beans"));
        tag(cTag("dried_coffee_beans_compat")).addOptional(ResourceLocation.parse("createfood:dried_coffee_beans"));
        tag(cTag("dumplings")).addOptional(ResourceLocation.parse("brewery:dumplings")).addOptional(ResourceLocation.parse("farmersdelight:dumplings"));
        tag(cTag("egg_burrito_ingredients")).addOptional(ResourceLocation.parse("createfood:boiled_egg_peeled")).addOptional(ResourceLocation.parse("farmersdelight:fried_egg"));
        tag(cTag("eggplant_burger")).addOptional(ResourceLocation.parse("culturalcreators:incomplete_eggplant_burger"));
        tag(cTag("eggs")).addOptional(ResourceLocation.parse("createfood:egg_powder"));
        tag(cTag("endermite_meatball_stick")).addOptional(ResourceLocation.parse("createfood:endermite_meatball_stick_1")).addOptional(ResourceLocation.parse("createfood:endermite_meatball_stick_2")).addOptional(ResourceLocation.parse("createfood:endermite_meatball_stick_3"));
        tag(cTag("flesh_cookie")).addOptional(ResourceLocation.parse("frightsdelight:cookie_flesh"));
        tag(cTag("flour")).addOptional(ResourceLocation.parse("farm_and_charm:flour"));
        tag(cTag("food_plates")).addOptional(ResourceLocation.parse("displaydelight:food_plate")).addOptional(ResourceLocation.parse("displaydelight:small_food_plate"));
        tag(cTag("foods/cooked_meats/cooked_rabbit")).addOptional(ResourceLocation.parse("minecraft:cooked_rabbit")).addOptional(ResourceLocation.parse("createfood:cooked_rabbit_cuts"));
        tag(cTag("foods/doughs")).addOptional(ResourceLocation.parse("create:dough"));
        tag(cTag("foods/raw_bacon")).addOptional(ResourceLocation.parse("farmersdelight:bacon"));
        tag(cTag("foods/raw_beef")).addOptional(ResourceLocation.parse("createfood:ground_beef"));
        tag(cTag("foods/raw_chicken")).addOptional(ResourceLocation.parse("createfood:ground_chicken"));
        tag(cTag("foods/raw_meats/ground")).addOptional(ResourceLocation.parse("createfood:ground_beef")).addOptional(ResourceLocation.parse("createfood:ground_chicken")).addOptional(ResourceLocation.parse("createfood:ground_mutton")).addOptional(ResourceLocation.parse("createfood:ground_pork")).addOptional(ResourceLocation.parse("createfood:ground_rabbit"));
        tag(cTag("foods/raw_meats/raw_bacon")).addOptional(ResourceLocation.parse("farmersdelight:bacon"));
        tag(cTag("foods/raw_meats/raw_beef")).addOptional(ResourceLocation.parse("createfood:ground_beef"));
        tag(cTag("foods/raw_meats/raw_chicken")).addOptional(ResourceLocation.parse("createfood:ground_chicken"));
        tag(cTag("foods/raw_meats/raw_mutton")).addOptional(ResourceLocation.parse("createfood:ground_mutton"));
        tag(cTag("foods/raw_meats/raw_pork")).addOptional(ResourceLocation.parse("createfood:ground_pork"));
        tag(cTag("foods/raw_meats/raw_rabbit")).addOptional(ResourceLocation.parse("minecraft:rabbit")).addOptional(ResourceLocation.parse("createfood:rabbit_cuts")).addOptional(ResourceLocation.parse("createfood:ground_rabbit"));
        tag(cTag("foods/raw_mutton")).addOptional(ResourceLocation.parse("createfood:ground_mutton"));
        tag(cTag("foods/raw_pork")).addOptional(ResourceLocation.parse("createfood:ground_pork"));
        tag(cTag("foods/raw_rabbit")).addOptional(ResourceLocation.parse("minecraft:rabbit")).addOptional(ResourceLocation.parse("createfood:rabbit_cuts")).addOptional(ResourceLocation.parse("createfood:ground_rabbit"));
        tag(cTag("frosting_ingredients")).addOptionalTag(cTag("cream_cheese")).addOptionalTag(cTag("butter"));
        tag(cTag("fruits")).addOptionalTag(cTag("apple")).addOptionalTag(cTag("melon")).addOptionalTag(cTag("chorus_fruit")).addOptionalTag(cTag("foods/berries"));
        tag(cTag("gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:black_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:blue_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:brown_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:cyan_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:gray_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:green_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:light_gray_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:lime_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:magenta_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:orange_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:pink_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:purple_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:red_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:yellow_gelatin_dessert_block"));
        tag(cTag("gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:black_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:blue_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:brown_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:cyan_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:gray_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:green_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:light_gray_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:lime_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:magenta_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:orange_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:pink_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:purple_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:red_gelatin_dessert_slice")).addOptional(ResourceLocation.parse("createfood:yellow_gelatin_dessert_slice"));
        tag(cTag("gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:black_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:blue_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:brown_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:cyan_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:gray_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:green_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:light_gray_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:lime_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:magenta_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:orange_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:pink_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:purple_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:red_gelatin_mix_bucket")).addOptional(ResourceLocation.parse("createfood:yellow_gelatin_mix_bucket"));
        tag(cTag("ginger_cookie")).addOptional(ResourceLocation.parse("ubesdelight:cookie_ginger"));
        tag(cTag("glow_berry_jam_bottle")).addOptional(ResourceLocation.parse("bakery:glowberry_jam")).addOptional(ResourceLocation.parse("expandeddelight:glow_berry_jelly"));
        tag(cTag("glow_berry_jam_bottle_compat")).addOptional(ResourceLocation.parse("createfood:glow_berry_jam_bottle"));
        tag(cTag("glow_berry_juice_bottle")).addOptional(ResourceLocation.parse("expandeddelight:glow_berry_juice"));
        tag(cTag("glow_berry_juice_bottle_compat")).addOptional(ResourceLocation.parse("createfood:glow_berry_juice_bottle"));
        tag(cTag("glow_berry_milkshake_bottle")).addOptional(ResourceLocation.parse("createfood:glow_berry_milkshake_bottle")).addOptional(ResourceLocation.parse("create_dd:glow_berry_milkshake"));
        tag(cTag("glow_berry_milkshake_bucket")).addOptional(ResourceLocation.parse("createfood:glow_berry_milkshake_bucket")).addOptional(ResourceLocation.parse("create_dd:glow_berry_milkshake_bucket"));
        tag(cTag("green_tea_cookie")).addOptional(ResourceLocation.parse("farmersrespite:green_tea_cookie"));
        tag(cTag("ground_strider")).addOptional(ResourceLocation.parse("nethersdelight:ground_strider")).addOptional(ResourceLocation.parse("mynethersdelight:minced_strider"));
        tag(cTag("gyro_meat_ingredients")).addOptionalTag(cTag("foods/raw_meats/raw_beef")).addOptionalTag(cTag("foods/raw_meats/raw_pork")).addOptionalTag(cTag("foods/raw_meats/raw_chicken")).addOptionalTag(cTag("foods/raw_meats/raw_rabbit"));
        tag(cTag("gyro_mutton_ingredient")).addOptionalTag(cTag("foods/cooked_mutton")).addOptional(ResourceLocation.parse("createfood:gyro_meat_slice"));
        tag(cTag("hamburger")).addOptional(ResourceLocation.parse("createfood:hamburger")).addOptional(ResourceLocation.parse("delightfulcreators:incomplete_hamburger"));
        tag(cTag("hamburger_onion_lettuce_tomato")).addOptional(ResourceLocation.parse("farmersdelight:hamburger"));
        tag(cTag("hidden_from_recipe_viewers")).addOptional(ResourceLocation.parse("createfood:icon"));
        tag(cTag("honey_cookie")).addOptional(ResourceLocation.parse("farmersdelight:honey_cookie"));
        tag(cTag("honeyed_donut")).addOptional(ResourceLocation.parse("createfood:honeyed_donut")).addOptional(ResourceLocation.parse("create_snt:honey_donut"));
        tag(cTag("hot_chocolate")).addOptional(ResourceLocation.parse("createfood:hot_chocolate_bottle"));
        tag(cTag("hot_chocolate_bottle")).addOptional(ResourceLocation.parse("createfood:hot_chocolate_bottle")).addOptional(ResourceLocation.parse("create_dd:hot_chocolate"));
        tag(cTag("hot_chocolate_bucket")).addOptional(ResourceLocation.parse("createfood:hot_chocolate_bucket")).addOptional(ResourceLocation.parse("create_dd:hot_chocolate_bucket"));
        tag(cTag("hot_dark_chocolate")).addOptional(ResourceLocation.parse("createfood:hot_dark_chocolate_bottle"));
        tag(cTag("hot_white_chocolate")).addOptional(ResourceLocation.parse("createfood:hot_white_chocolate_bottle"));
        tag(cTag("ice_blocks")).addOptional(ResourceLocation.parse("minecraft:ice")).addOptional(ResourceLocation.parse("minecraft:packed_ice")).addOptional(ResourceLocation.parse("minecraft:blue_ice"));
        tag(cTag("magma_gelatin")).addOptional(ResourceLocation.parse("nethersdelight:magma_gelatin")).addOptional(ResourceLocation.parse("mynethersdelight:hot_cream"));
        tag(cTag("marshmallow_dark_chocolate_fudge")).addOptional(ResourceLocation.parse("createfood:marshmallow_chocolate_fudge"));
        tag(cTag("marshmallow_white_chocolate_fudge")).addOptional(ResourceLocation.parse("createfood:marshmallow_chocolate_fudge"));
        tag(cTag("melon")).addOptional(ResourceLocation.parse("minecraft:melon")).addOptional(ResourceLocation.parse("minecraft:melon_slice"));
        tag(cTag("melon_cream_frosting")).addOptional(ResourceLocation.parse("createfood:melon_cream_frosting_bottle"));
        tag(cTag("milk")).addOptional(ResourceLocation.parse("minecraft:milk_bucket"));
        tag(cTag("milk_bottle")).addOptional(ResourceLocation.parse("farmersdelight:milk_bottle"));
        tag(cTag("milk_buckets")).addOptional(ResourceLocation.parse("meadow:wooden_milk_bucket")).addOptional(ResourceLocation.parse("createfood:milk_powder")).addOptional(ResourceLocation.parse("ubesdelight:milk_powder"));
        tag(cTag("milk_powder")).addOptional(ResourceLocation.parse("createfood:milk_powder")).addOptional(ResourceLocation.parse("ubesdelight:milk_powder"));
        tag(cTag("milk_powder_compat")).addOptional(ResourceLocation.parse("createfood:milk_powder"));
        tag(cTag("milks")).addOptional(ResourceLocation.parse("createfood:milk_powder")).addOptional(ResourceLocation.parse("ubesdelight:milk_powder"));
        tag(cTag("milkshake")).addOptional(ResourceLocation.parse("createfood:milkshake_bottle"));
        tag(cTag("milkshake_bottle")).addOptional(ResourceLocation.parse("createfood:milkshake_bottle")).addOptional(ResourceLocation.parse("create_dd:vanilla_milkshake"));
        tag(cTag("milkshake_bucket")).addOptional(ResourceLocation.parse("createfood:milkshake_bucket")).addOptional(ResourceLocation.parse("create_dd:vanilla_milkshake_bucket"));
        tag(cTag("minced_beef")).addOptional(ResourceLocation.parse("farmersdelight:minced_beef")).addOptional(ResourceLocation.parse("farm_and_charm:minced_beef"));
        tag(cTag("muffin_base")).addOptional(ResourceLocation.parse("createfood:muffin_base")).addOptional(ResourceLocation.parse("create_snt:muffin"));
        tag(cTag("mushrooms")).addOptional(ResourceLocation.parse("createfood:sliced_brown_mushroom")).addOptional(ResourceLocation.parse("createfood:sliced_red_mushroom"));
        tag(cTag("mutton_sandwich")).addOptional(ResourceLocation.parse("createfood:mutton_sandwich")).addOptional(ResourceLocation.parse("culturalcreators:incomplete_mutton_sandwich"));
        tag(cTag("onion")).addOptional(ResourceLocation.parse("createfood:sliced_onion")).addOptional(ResourceLocation.parse("createfood:diced_onion")).addOptional(ResourceLocation.parse("farmersdelight:onion"));
        tag(cTag("paprika")).addOptional(ResourceLocation.parse("createfood:paprika")).addOptional(ResourceLocation.parse("create:cinder_flour"));
        tag(cTag("paprika_compat")).addOptional(ResourceLocation.parse("createfood:paprika"));
        tag(cTag("paprika_ingredient")).addOptionalTag(cTag("chili_pepper")).addOptional(ResourceLocation.parse("minecraft:nether_wart"));
        tag(cTag("pasta_plate")).addOptional(ResourceLocation.parse("createfood:pasta_plate")).addOptional(ResourceLocation.parse("delightfulcreators:incomplete_pasta_dish"));
        tag(cTag("pasta_plate_beef_meatballs")).addOptional(ResourceLocation.parse("createfood:pasta_plate_beef_meatballs")).addOptional(ResourceLocation.parse("delightfulcreators:incomplete_pasta_with_meatballs"));
        tag(cTag("pasta_plate_eggplant")).addOptional(ResourceLocation.parse("createfood:pasta_plate_eggplant")).addOptional(ResourceLocation.parse("culturalcreators:incomplete_fried_eggplant_pasta"));
        tag(cTag("pasta_plate_mutton_chop")).addOptional(ResourceLocation.parse("createfood:pasta_plate_mutton_chop")).addOptional(ResourceLocation.parse("delightfulcreators:incomplete_pasta_with_mutton_chop"));
        tag(cTag("pasta_plate_squid_ink")).addOptional(ResourceLocation.parse("createfood:pasta_plate_squid_ink")).addOptional(ResourceLocation.parse("delightfulcreators:incomplete_squid_ink_pasta"));
        tag(cTag("pasta_with_slimeballs")).addOptional(ResourceLocation.parse("frightsdelight:pasta_with_slimeballs"));
        tag(cTag("peanut")).addOptional(ResourceLocation.parse("expandeddelight:peanut"));
        tag(cTag("peanut_butter")).addOptional(ResourceLocation.parse("expandeddelight:peanut_butter")).addOptional(ResourceLocation.parse("croptopia:peanut_butter"));
        tag(cTag("peanut_butter_sandwich")).addOptional(ResourceLocation.parse("expandeddelight:peanut_butter_sandwich"));
        tag(cTag("pita_dough_ingredients")).addOptional(ResourceLocation.parse("createfood:salt_dough_small")).addOptional(ResourceLocation.parse("createfood:wheat_dough_small"));
        tag(cTag("plain_gelatin_dessert_block")).addOptional(ResourceLocation.parse("createfood:gelatin_dessert_block"));
        tag(cTag("popcorn")).addOptional(ResourceLocation.parse("culturaldelights:popcorn"));
        tag(cTag("pork_meatball_stick")).addOptional(ResourceLocation.parse("createfood:pork_meatball_stick_1")).addOptional(ResourceLocation.parse("createfood:pork_meatball_stick_2")).addOptional(ResourceLocation.parse("createfood:pork_meatball_stick_3"));
        tag(cTag("potato")).addOptional(ResourceLocation.parse("createfood:shredded_potato")).addOptional(ResourceLocation.parse("createfood:sliced_potato")).addOptional(ResourceLocation.parse("minecraft:potato"));
        tag(cTag("potato_chips")).addOptional(ResourceLocation.parse("createfood:potato_chips")).addOptional(ResourceLocation.parse("casualnessdelight:potato_chip"));
        tag(cTag("powderable_eggs")).addOptional(ResourceLocation.parse("createfood:boiled_egg_peeled")).addOptional(ResourceLocation.parse("farmersdelight:fried_egg"));
        tag(cTag("pumpkin")).addOptional(ResourceLocation.parse("minecraft:pumpkin")).addOptional(ResourceLocation.parse("farmersdelight:pumpkin_slice"));
        tag(cTag("pumpkin_pie")).addOptional(ResourceLocation.parse("minecraft:pumpkin_pie"));
        tag(cTag("rabbit_meatball_stick")).addOptional(ResourceLocation.parse("createfood:rabbit_meatball_stick_1")).addOptional(ResourceLocation.parse("createfood:rabbit_meatball_stick_2")).addOptional(ResourceLocation.parse("createfood:rabbit_meatball_stick_3"));
        tag(cTag("raw_cake_base")).addOptional(ResourceLocation.parse("createfood:raw_cake_base")).addOptional(ResourceLocation.parse("createadditions:cake_base_baked"));
        tag(cTag("raw_dragon_meat_cuts")).addOptional(ResourceLocation.parse("ends_delight:raw_dragon_meat_cuts"));
        tag(cTag("raw_pasta")).addOptional(ResourceLocation.parse("farmersdelight:raw_pasta")).addOptional(ResourceLocation.parse("farm_and_charm:raw_pasta"));
        tag(cTag("red_mushroom")).addOptional(ResourceLocation.parse("minecraft:red_mushroom")).addOptional(ResourceLocation.parse("createfood:sliced_red_mushroom"));
        tag(cTag("salad_ingredients")).addOptional(ResourceLocation.parse("croptopia:lettuce")).addOptional(ResourceLocation.parse("candlelight:lettuce"));
        tag(cTag("salt")).addOptional(ResourceLocation.parse("createfood:salt")).addOptional(ResourceLocation.parse("expandeddelight:ground_salt")).addOptional(ResourceLocation.parse("meadow:alpine_salt")).addOptional(ResourceLocation.parse("vegandelight:salt"));
        tag(cTag("salt_compat")).addOptional(ResourceLocation.parse("createfood:salt"));
        tag(cTag("sausage")).addOptional(ResourceLocation.parse("createfood:sausages"));
        tag(cTag("sausages")).addOptional(ResourceLocation.parse("createfood:sausages")).addOptional(ResourceLocation.parse("createfood:sausage_bits"));
        tag(cTag("shredded_potato")).addOptional(ResourceLocation.parse("createfood:shredded_potato")).addOptional(ResourceLocation.parse("moredelight:diced_potatoes"));
        tag(cTag("sliced_potato")).addOptional(ResourceLocation.parse("createfood:sliced_potato")).addOptional(ResourceLocation.parse("casualnessdelight:potato_slice")).addOptional(ResourceLocation.parse("rusticdelight:potato_slices"));
        tag(cTag("snickerdoodle")).addOptional(ResourceLocation.parse("expandeddelight:snickerdoodle"));
        tag(cTag("soul_berry_cookie")).addOptional(ResourceLocation.parse("frightsdelight:cookie_soul_berry"));
        tag(cTag("spider_eye_cookie")).addOptional(ResourceLocation.parse("frightsdelight:cookie_spidereye"));
        tag(cTag("sugar")).addOptional(ResourceLocation.parse("minecraft:sugar")).addOptional(ResourceLocation.parse("createfood:powdered_sugar"));
        tag(cTag("sugar_cane")).addOptional(ResourceLocation.parse("minecraft:sugar_cane"));
        tag(cTag("sugar_cookie")).addOptional(ResourceLocation.parse("expandeddelight:sugar_cookie"));
        tag(cTag("sweet_berry_cookie")).addOptional(ResourceLocation.parse("farmersdelight:sweet_berry_cookie"));
        tag(cTag("sweet_dough")).addOptional(ResourceLocation.parse("createfood:sugar_dough")).addOptional(ResourceLocation.parse("bakery:sweet_dough"));
        tag(cTag("sweet_roll")).addOptional(ResourceLocation.parse("create:sweet_roll"));
        tag(cTag("taco_shell_ingredient")).addOptionalTag(cTag("pita_bread")).addOptionalTag(cTag("tortilla"));
        tag(cTag("toast")).addOptional(ResourceLocation.parse("createfood:toast_slice")).addOptional(ResourceLocation.parse("moredelight:toast"));
        tag(cTag("toast_slice")).addOptional(ResourceLocation.parse("createfood:toast_slice")).addOptional(ResourceLocation.parse("moredelight:toast"));
        tag(cTag("tomato")).addOptional(ResourceLocation.parse("createfood:diced_tomato")).addOptional(ResourceLocation.parse("createfood:sliced_tomato")).addOptional(ResourceLocation.parse("farmersdelight:tomato")).addOptional(ResourceLocation.parse("candlelight:tomato"));
        tag(cTag("tomato_sauce")).addOptional(ResourceLocation.parse("farmersdelight:tomato_sauce"));
        tag(cTag("tools/knife")).addOptional(ResourceLocation.parse("bakery:bread_knife"));
        tag(cTag("tortilla")).addOptional(ResourceLocation.parse("culturaldelights:tortilla"));
        tag(cTag("tortilla_chips")).addOptional(ResourceLocation.parse("culturaldelights:tortilla_chips"));
        tag(cTag("ube_cookie")).addOptional(ResourceLocation.parse("ubesdelight:cookie_ube"));
        tag(cTag("ube_cream_frosting")).addOptional(ResourceLocation.parse("createfood:ube_cream_frosting_bottle"));
        tag(cTag("vegetable_oil")).addOptional(ResourceLocation.parse("createfood:vegetable_oil_bucket"));
        tag(cTag("vegetables")).addOptional(ResourceLocation.parse("minecraft:carrot")).addOptional(ResourceLocation.parse("minecraft:potato")).addOptional(ResourceLocation.parse("farmersdelight:tomato")).addOptional(ResourceLocation.parse("farmersdelight:onion"));
        tag(cTag("vegetables/carrot")).addOptional(ResourceLocation.parse("createfood:sliced_carrot"));
        tag(cTag("vegetables/ginger")).addOptional(ResourceLocation.parse("ubesdelight:ginger"));
        tag(cTag("vegetables/potato")).addOptional(ResourceLocation.parse("createfood:sliced_potato"));
        tag(cTag("vegetables/ube")).addOptional(ResourceLocation.parse("ubesdelight:ube"));
        tag(cTag("vinegar_bottle")).addOptional(ResourceLocation.parse("createfood:vinegar_bottle")).addOptional(ResourceLocation.parse("dumplings_delight:vinegar"));
        tag(cTag("warped_fungus")).addOptional(ResourceLocation.parse("minecraft:warped_fungus")).addOptional(ResourceLocation.parse("createfood:sliced_warped_fungus"));
        tag(cTag("wheat_dough")).addOptional(ResourceLocation.parse("create:dough")).addOptional(ResourceLocation.parse("farmersdelight:wheat_dough"));
        tag(cTag("chocolate_sweet_dough")).addOptional(ResourceLocation.parse("createfood:chocolate_sugar_dough"));

    }

    /** Build a TagKey under the {@code c} namespace for the given item ID. */
    private static TagKey<Item> cTag(String id) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", id));
    }

    private static TagKey<Item> modTag(String namespace, String id) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, id));
    }
}