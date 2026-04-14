package dev.averageanime.neoforge.datagen.provider;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModFluids;
import dev.averageanime.neoforge.block.type.fluid.FluidEntry;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
        super(output, Registries.FLUID, lookupProvider, CommonClass.ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        for (Field field : ModFluids.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            try {
                Object value = field.get(null);
                if (value instanceof FluidEntry.FluidType fluidType) {
                    String fluidId = fluidType.SOURCE.getId().getPath();
                    String flowingId = fluidType.FLOWING.getId().getPath();

                    TagKey<Fluid> tag = TagKey.create(Registries.FLUID,
                            ResourceLocation.fromNamespaceAndPath("c", fluidId));

                    tag(tag)
                            .addOptional(ResourceLocation.fromNamespaceAndPath(CommonClass.ID, flowingId))
                            .addOptional(ResourceLocation.fromNamespaceAndPath(CommonClass.ID, fluidId));
                }
            } catch (IllegalAccessException ignored) {}
        }
    }
}
