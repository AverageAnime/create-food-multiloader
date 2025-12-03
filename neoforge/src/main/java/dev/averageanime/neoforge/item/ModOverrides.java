package dev.averageanime.neoforge.item;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(modid = CommonClass.ID, bus = EventBusSubscriber.Bus.MOD)
public class ModOverrides {

    @SubscribeEvent
    public static void onRegisterItems(RegisterEvent event) {
        event.register(Registries.ITEM, registry -> {
            ResourceLocation pumpkinPieId = ResourceLocation.withDefaultNamespace("pumpkin_pie");
            registry.register(pumpkinPieId,
                    new BlockItem(ModBlocks.PUMPKIN_PIE_BLOCK.get(), new Item.Properties()));
        });
    }
}