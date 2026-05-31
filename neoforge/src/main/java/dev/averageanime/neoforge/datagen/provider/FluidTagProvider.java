package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModFluids;
import dev.averageanime.neoforge.block.type.fluid.FluidEntry;
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

    public FluidTagProvider(PackOutput output,
                            CompletableFuture<HolderLookup.Provider> lookupProvider,
                            ExistingFileHelper existingFileHelper) {
        super(output, Registries.FLUID, lookupProvider, CommonClass.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        for (FluidEntry.FluidType fluidType : ModFluids.BY_ID.values()) {
            String fluidId = fluidType.SOURCE.getId().getPath();
            String flowingId = fluidType.FLOWING.getId().getPath();

            TagKey<Fluid> tag = TagKey.create(Registries.FLUID,
                    ResourceLocation.fromNamespaceAndPath("c", fluidId));

            tag(tag)
                    .addOptional(ResourceLocation.fromNamespaceAndPath(CommonClass.MOD_ID, flowingId))
                    .addOptional(ResourceLocation.fromNamespaceAndPath(CommonClass.MOD_ID, fluidId));
        }

        // Create Confectionery fluid compat
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
    }

    private static TagKey<Fluid> fluidTag(String id) {
        return TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath("c", id));
    }
}