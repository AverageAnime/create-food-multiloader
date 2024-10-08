package net.averageanime.createfood;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.ModFilePackResources;

import com.simibubi.create.foundation.utility.Components;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;

@EventBusSubscriber
public class ResourcePacks {

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {

        @SubscribeEvent
        public static void addPackFinders(AddPackFindersEvent event) {
            if (event.getPackType() == PackType.CLIENT_RESOURCES)
            {
                IModFileInfo modFileInfo = ModList.get().getModFileById("createfood");
                if (modFileInfo == null) {
                    System.out.println("Could not find CreateFood mod file info; built-in resource packs will be missing!");
                    return;
                }

                IModFile modFile = modFileInfo.getFile();

                event.addRepositorySource(consumer
                        -> {
                    Pack pack = Pack.readMetaAndCreate(Create.asResource("create").toString(),
                            Components.literal("Create"),
                            false,
                            id -> new ModFilePackResources(id, modFile, "resourcepacks/create"),
                            PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
                    if (pack != null) {
                        consumer.accept(pack);
                    }
                });

                event.addRepositorySource(consumer
                        -> {
                    Pack pack = Pack.readMetaAndCreate(Create.asResource("farmers_delight").toString(),
                            Components.literal("Farmer's Delight"),
                            false,
                            id -> new ModFilePackResources(id, modFile, "resourcepacks/farmers_delight"),
                            PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
                    if (pack != null) {
                        consumer.accept(pack);
                    }
                });

                event.addRepositorySource(consumer
                        -> {
                    Pack pack = Pack.readMetaAndCreate(Create.asResource("expanded_delight").toString(),
                            Components.literal("Expanded Delight"),
                            false,
                            id -> new ModFilePackResources(id, modFile, "resourcepacks/expanded_delight"),
                            PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
                    if (pack != null) {
                        consumer.accept(pack);
                    }
                });

                event.addRepositorySource(consumer
                        -> {
                    Pack pack = Pack.readMetaAndCreate(Create.asResource("farmers_respite").toString(),
                            Components.literal("Farmer's Respite"),
                            false,
                            id -> new ModFilePackResources(id, modFile, "resourcepacks/farmers_respite"),
                            PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
                    if (pack != null) {
                        consumer.accept(pack);
                    }
                });

                event.addRepositorySource(consumer
                        -> {
                    Pack pack = Pack.readMetaAndCreate(Create.asResource("ends_delight").toString(),
                            Components.literal("End's Delight"),
                            false,
                            id -> new ModFilePackResources(id, modFile, "resourcepacks/ends_delight"),
                            PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
                    if (pack != null) {
                        consumer.accept(pack);
                    }
                });

                event.addRepositorySource(consumer
                        -> {
                    Pack pack = Pack.readMetaAndCreate(Create.asResource("cultural_delight").toString(),
                            Components.literal("Cultural Delight"),
                            false,
                            id -> new ModFilePackResources(id, modFile, "resourcepacks/cultural_delight"),
                            PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
                    if (pack != null) {
                        consumer.accept(pack);
                    }
                });

                event.addRepositorySource(consumer
                        -> {
                    Pack pack = Pack.readMetaAndCreate(Create.asResource("ubes_delight").toString(),
                            Components.literal("Ube's Delight"),
                            false,
                            id -> new ModFilePackResources(id, modFile, "resourcepacks/ubes_delight"),
                            PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
                    if (pack != null) {
                        consumer.accept(pack);
                    }
                });

            }
        }
    }
}