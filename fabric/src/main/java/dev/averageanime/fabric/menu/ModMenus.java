package dev.averageanime.fabric.menu;

import dev.averageanime.fabric.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.fabric.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.fabric.menu.type.ClothSackItemMenu;
import dev.averageanime.fabric.menu.type.ClothSackMenu;
import dev.averageanime.fabric.menu.type.RationBoxItemMenu;
import dev.averageanime.fabric.menu.type.RationBoxMenu;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;

import static dev.averageanime.fabric.CreateFood.MOD_ID;

public class ModMenus {

    public static MenuType<RationBoxMenu> RATION_BOX;
    public static MenuType<ClothSackMenu> CLOTH_SACK;
    public static MenuType<RationBoxItemMenu> RATION_BOX_ITEM;
    public static MenuType<ClothSackItemMenu> CLOTH_SACK_ITEM;

    public static void init() {
        RATION_BOX = Registry.register(BuiltInRegistries.MENU,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "ration_box"),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> {
                    RationBoxBlockEntity be = null;
                    if (inv.player.level().getBlockEntity(pos) instanceof RationBoxBlockEntity rb) be = rb;
                    return new RationBoxMenu(syncId, inv, be);
                }, BlockPos.STREAM_CODEC));

        CLOTH_SACK = Registry.register(BuiltInRegistries.MENU,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "cloth_sack"),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> {
                    ClothSackBlockEntity be = null;
                    if (inv.player.level().getBlockEntity(pos) instanceof ClothSackBlockEntity cs) be = cs;
                    return new ClothSackMenu(syncId, inv, be);
                }, BlockPos.STREAM_CODEC));

        RATION_BOX_ITEM = Registry.register(BuiltInRegistries.MENU,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "ration_box_item"),
                new ExtendedScreenHandlerType<>(RationBoxItemMenu::new,
                        ByteBufCodecs.VAR_INT));

        CLOTH_SACK_ITEM = Registry.register(BuiltInRegistries.MENU,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "cloth_sack_item"),
                new ExtendedScreenHandlerType<>(ClothSackItemMenu::new,
                        ByteBufCodecs.VAR_INT));
    }
}
