package dev.averageanime.fabric.menu;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.fabric.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.fabric.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.fabric.menu.type.item.ClothSackItemMenu;
import dev.averageanime.fabric.menu.type.block.ClothSackBlockMenu;
import dev.averageanime.fabric.menu.type.item.RationBoxItemMenu;
import dev.averageanime.fabric.menu.type.block.RationBoxBlockMenu;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;


public class MenuRegistration {

    public static MenuType<RationBoxBlockMenu> RATION_BOX;
    public static MenuType<ClothSackBlockMenu> CLOTH_SACK;
    public static MenuType<RationBoxItemMenu> RATION_BOX_ITEM;
    public static MenuType<ClothSackItemMenu> CLOTH_SACK_ITEM;

    public static void init() {
        RATION_BOX = Registry.register(BuiltInRegistries.MENU,
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "ration_box"),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> {
                    RationBoxBlockEntity be = null;
                    if (inv.player.level().getBlockEntity(pos) instanceof RationBoxBlockEntity rb) be = rb;
                    return new RationBoxBlockMenu(syncId, inv, be);
                }, BlockPos.STREAM_CODEC));

        CLOTH_SACK = Registry.register(BuiltInRegistries.MENU,
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "cloth_sack"),
                new ExtendedScreenHandlerType<>((syncId, inv, pos) -> {
                    ClothSackBlockEntity be = null;
                    if (inv.player.level().getBlockEntity(pos) instanceof ClothSackBlockEntity cs) be = cs;
                    return new ClothSackBlockMenu(syncId, inv, be);
                }, BlockPos.STREAM_CODEC));

        RATION_BOX_ITEM = Registry.register(BuiltInRegistries.MENU,
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "ration_box_item"),
                new ExtendedScreenHandlerType<>(RationBoxItemMenu::new,
                        ByteBufCodecs.VAR_INT));

        CLOTH_SACK_ITEM = Registry.register(BuiltInRegistries.MENU,
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "cloth_sack_item"),
                new ExtendedScreenHandlerType<>(ClothSackItemMenu::new,
                        ByteBufCodecs.VAR_INT));
    }
}
