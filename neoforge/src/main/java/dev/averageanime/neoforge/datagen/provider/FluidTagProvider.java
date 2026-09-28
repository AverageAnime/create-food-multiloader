package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.createfood.lib.datagen.DatagenHelpers;
import dev.averageanime.neoforge.block.FluidRegistration;
import dev.averageanime.createfood.lib.fluid.FluidBlock;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class FluidTagProvider extends TagsProvider<Fluid> {

    private static final String[] DELIGHTFUL_CREATORS_FLUIDS = {
            "apple_cider", "baked_cod_stew", "beef_stew", "beetroot_soup", "bone_broth",
            "chicken_soup", "cooked_rice", "dog_food", "fish_stew", "glow_berry_custard",
            "hot_cocoa", "melon_juice", "mushroom_stew", "noodle_soup", "pumpkin_soup",
            "rabbit_stew", "ratatouille", "tomato_sauce", "vegetable_soup"
    };

    public FluidTagProvider(PackOutput output,
                            CompletableFuture<HolderLookup.Provider> lookupProvider,
                            ExistingFileHelper existingFileHelper) {
        super(output, Registries.FLUID, lookupProvider, CreateFoodCommon.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        for (FluidBlock.FluidType fluidType : FluidRegistration.BY_ID.values()) {
            String fluidId = fluidType.SOURCE.getId().getPath();
            String flowingId = fluidType.FLOWING.getId().getPath();

            DatagenHelpers.conventionTag((key, id) -> tag(key).addOptional(id),
                    CreateFoodCommon.MOD_ID, fluidId, flowingId);
        }

        tag(fluidTag("berry_juice"))
                .addOptional(ResourceLocation.parse("hearthandharvest:sweet_berry_juice"))
                .addOptional(ResourceLocation.parse("hearthandharvest:flowing_sweet_berry_juice"));
        tag(fluidTag("glow_berry_juice"))
                .addOptional(ResourceLocation.parse("hearthandharvest:glow_berry_juice"))
                .addOptional(ResourceLocation.parse("hearthandharvest:flowing_glow_berry_juice"));

        tag(fluidTag("cake_batter"))
                .addOptional(ResourceLocation.parse("ratatouille:cake_batter"));

        // Our Delightful Creators melon juice override reads c:melon, which covers the melon slices
        // Ratatouille's own mixing recipe names, so the two race in the basin. Listing its fluid keeps
        // whichever wins interchangeable.
        tag(fluidTag("melon_juice"))
                .addOptional(ResourceLocation.parse("ratatouille:melon_juice_fluid"))
                .addOptional(ResourceLocation.parse("ratatouille:flowing_melon_juice_fluid"));

        tag(fluidTag("caramel"))
                .addOptional(ResourceLocation.parse("create_confectionery:caramel"))
                .addOptional(ResourceLocation.parse("create_confectionery:flowing_caramel"));
        tag(fluidTag("dark_chocolate"))
                .addOptional(ResourceLocation.parse("create_confectionery:black_chocolate"))
                .addOptional(ResourceLocation.parse("create_confectionery:flowing_black_chocolate"));
        tag(fluidTag("hot_chocolate"))
                .addOptional(ResourceLocation.parse("create_confectionery:hot_chocolate"))
                .addOptional(ResourceLocation.parse("create_confectionery:flowing_hot_chocolate"));
        tag(fluidTag("white_chocolate"))
                .addOptional(ResourceLocation.parse("create_confectionery:white_chocolate"))
                .addOptional(ResourceLocation.parse("create_confectionery:flowing_white_chocolate"));

        for (String id : DELIGHTFUL_CREATORS_FLUIDS) {
            tag(fluidTag(id))
                    .addOptional(ResourceLocation.fromNamespaceAndPath("delightfulcreators", id))
                    .addOptional(ResourceLocation.fromNamespaceAndPath("delightfulcreators", "flowing_" + id));
        }

        tag(fluidTag("creamed_corn"))
                .addOptional(ResourceLocation.parse("culturalcreators:creamed_corn"))
                .addOptional(ResourceLocation.parse("culturalcreators:flowing_creamed_corn"));

        // Lets Ratatouille Fried Delights' fryer run on Create: Food's vegetable oil.
        tag(modTag("ratatouille_fried_delights", "oil"))
                .addOptional(ResourceLocation.parse("createfood:vegetable_oil"))
                .addOptional(ResourceLocation.parse("createfood:flowing_vegetable_oil"));

        // Whole milk and skim milk stay separate tags so that separating cream out of
        // milk actually costs whole milk; c:any_milk is for the many recipes that do
        // not care which they get. Nesting the two tags rather than listing fluid ids
        // keeps this correct as other mods contribute their own milks.
        // c:any_milk must never appear in the cream separation recipe -- it would let
        // that recipe satisfy itself from its own skim output and revert it to 1:1.
        tag(fluidTag("any_milk"))
                .addTag(fluidTag("milk"))
                .addTag(fluidTag("skim_milk"));

        // c:any_juice is for recipes that want juice but do not care which -- vinegar is the
        // one so far. Nesting the per-flavour tags rather than listing fluid ids means the
        // foreign juices already aliased above (Hearth and Harvest's berry juices, Ratatouille's
        // and Delightful Creators' melon juice) come along without being repeated here.
        // c:melon_juice has no Create: Food fluid of its own and is empty without those mods,
        // which costs nothing.
        tag(fluidTag("any_juice"))
                .addTag(fluidTag("apple_juice"))
                .addTag(fluidTag("berry_juice"))
                .addTag(fluidTag("chorus_fruit_juice"))
                .addTag(fluidTag("glow_berry_juice"))
                .addTag(fluidTag("melon_juice"))
                .addTag(fluidTag("sugar_cane_juice"));
    }

    private static TagKey<Fluid> fluidTag(String id) {
        return TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath("c", id));
    }

    private static TagKey<Fluid> modTag(String namespace, String id) {
        return TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(namespace, id));
    }
}