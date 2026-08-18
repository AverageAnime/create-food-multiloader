package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.neoforge.block.FluidRegistration;
import dev.averageanime.neoforge.block.type.fluid.FluidBlock;
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

            TagKey<Fluid> tag = TagKey.create(Registries.FLUID,
                    ResourceLocation.fromNamespaceAndPath("c", fluidId));

            tag(tag)
                    .addOptional(ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, flowingId))
                    .addOptional(ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, fluidId));
        }

        tag(fluidTag("berry_juice"))
                .addOptional(ResourceLocation.parse("hearthandharvest:sweet_berry_juice"))
                .addOptional(ResourceLocation.parse("hearthandharvest:flowing_sweet_berry_juice"));
        tag(fluidTag("glow_berry_juice"))
                .addOptional(ResourceLocation.parse("hearthandharvest:glow_berry_juice"))
                .addOptional(ResourceLocation.parse("hearthandharvest:flowing_glow_berry_juice"));

        tag(fluidTag("cake_batter"))
                .addOptional(ResourceLocation.parse("ratatouille:cake_batter"));

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

        // Delightful Creators turns Farmer's Delight dishes into Create fluids. Aliasing them under c:
        // lets our recipes reference them by tag instead of naming the mod directly. None of these
        // collide with a Create: Food fluid — our soups and stews are separate dishes by design.
        for (String id : DELIGHTFUL_CREATORS_FLUIDS) {
            tag(fluidTag(id))
                    .addOptional(ResourceLocation.fromNamespaceAndPath("delightfulcreators", id))
                    .addOptional(ResourceLocation.fromNamespaceAndPath("delightfulcreators", "flowing_" + id));
        }

        // Cultural Creators' one fluid, aliased for the same reason.
        tag(fluidTag("creamed_corn"))
                .addOptional(ResourceLocation.parse("culturalcreators:creamed_corn"))
                .addOptional(ResourceLocation.parse("culturalcreators:flowing_creamed_corn"));

        // Reciprocal: lets Ratatouille Fried Delights' Continuous Fryer run on Create: Food's vegetable oil.
        tag(modTag("ratatouille_fried_delights", "oil"))
                .addOptional(ResourceLocation.parse("createfood:vegetable_oil"))
                .addOptional(ResourceLocation.parse("createfood:flowing_vegetable_oil"));
    }

    private static TagKey<Fluid> fluidTag(String id) {
        return TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath("c", id));
    }

    private static TagKey<Fluid> modTag(String namespace, String id) {
        return TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(namespace, id));
    }
}