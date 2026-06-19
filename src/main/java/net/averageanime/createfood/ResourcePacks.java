package net.averageanime.createfood;

import com.simibubi.create.foundation.pack.ModFilePackResources;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
            if (event.getPackType() != PackType.CLIENT_RESOURCES) return;

            IModFileInfo modFileInfo = ModList.get().getModFileById("createfood");
            if (modFileInfo == null) {
                System.out.println("Could not find CreateFood mod file info; built-in resource packs will be missing!");
                return;
            }

            IModFile modFile = modFileInfo.getFile();

            // Resource packs are only needed when Create is installed
            if (!ModList.get().isLoaded("create")) return;

            addPack(event, modFile, "create",           "Create");
            addPack(event, modFile, "farmers_delight",  "Farmer's Delight");
            addPack(event, modFile, "expanded_delight", "Expanded Delight");
            addPack(event, modFile, "farmers_respite",  "Farmer's Respite");
            addPack(event, modFile, "ends_delight",     "End's Delight");
            addPack(event, modFile, "cultural_delight", "Cultural Delight");
            addPack(event, modFile, "ubes_delight",     "Ube's Delight");
            addPack(event, modFile, "rustic_delight",   "Rustic Delight");
        }

        private static void addPack(AddPackFindersEvent event, IModFile modFile, String id, String title) {
            String packId = new ResourceLocation("create", id).toString();
            event.addRepositorySource(consumer -> {
                Pack pack = Pack.readMetaAndCreate(
                        packId,
                        Component.literal(title),
                        false,
                        pId -> new ModFilePackResources(pId, modFile, "resourcepacks/" + id),
                        PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
                if (pack != null) consumer.accept(pack);
            });
        }
    }
}
