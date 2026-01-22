package dev.averageanime.neoforge.datagen;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.datagen.provider.LootTablesProvider;
import dev.averageanime.neoforge.datagen.provider.BlockStateProvider;
import dev.averageanime.neoforge.datagen.provider.ItemModelProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = CommonClass.ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Client-side data
        generator.addProvider(event.includeClient(),
                new BlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(),
                new ItemModelProvider(output, existingFileHelper));

        // Server-side data (loot tables)
        generator.addProvider(event.includeServer(),
                new LootTableProvider(output, Collections.emptySet(),
                        List.of(new LootTableProvider.SubProviderEntry(
                                LootTablesProvider::new,
                                LootContextParamSets.BLOCK)),
                        lookupProvider));
    }
}