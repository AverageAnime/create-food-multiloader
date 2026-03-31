package dev.averageanime.neoforge.datagen;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.datagen.provider.LootTableProvider;
import dev.averageanime.neoforge.datagen.provider.BlockStateProvider;
import dev.averageanime.neoforge.datagen.provider.FluidTagProvider;
import dev.averageanime.neoforge.datagen.provider.ItemTagProvider;
import dev.averageanime.neoforge.datagen.provider.BlockModelProvider;
import dev.averageanime.neoforge.datagen.provider.ItemModelProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = CommonClass.ID)
public class ModDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(),
                new BlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(),
                new ItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(),
                new BlockModelProvider(output, existingFileHelper));

        generator.addProvider(event.includeServer(),
                new net.minecraft.data.loot.LootTableProvider(output, Collections.emptySet(),
                        List.of(new net.minecraft.data.loot.LootTableProvider.SubProviderEntry(
                                LootTableProvider::new,
                                LootContextParamSets.BLOCK)),
                        lookupProvider));
        generator.addProvider(event.includeServer(),
                new ItemTagProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(),
                new FluidTagProvider(output, lookupProvider, existingFileHelper));
    }
}