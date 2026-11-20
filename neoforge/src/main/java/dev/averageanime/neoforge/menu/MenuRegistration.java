package dev.averageanime.neoforge.menu;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.neoforge.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.neoforge.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.neoforge.menu.type.item.ClothSackItemMenu;
import dev.averageanime.neoforge.menu.type.block.ClothSackBlockMenu;
import dev.averageanime.neoforge.menu.type.item.RationBoxItemMenu;
import dev.averageanime.neoforge.menu.type.block.RationBoxBlockMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MenuRegistration {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, CreateFoodCommon.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<RationBoxBlockMenu>>
        RATION_BOX = MENUS.register("ration_box",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> {
                // The block can be gone by the time the packet arrives; the menu then shows only the player inventory.
                RationBoxBlockEntity be = inv.player.level().getBlockEntity(data.readBlockPos())
                        instanceof RationBoxBlockEntity rb ? rb : null;
                return new RationBoxBlockMenu(windowId, inv, be);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<ClothSackBlockMenu>>
        CLOTH_SACK = MENUS.register("cloth_sack",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> {
                ClothSackBlockEntity be = inv.player.level().getBlockEntity(data.readBlockPos())
                        instanceof ClothSackBlockEntity cs ? cs : null;
                return new ClothSackBlockMenu(windowId, inv, be);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<ClothSackItemMenu>>
        CLOTH_SACK_ITEM = MENUS.register("cloth_sack_item",
            () -> IMenuTypeExtension.create((windowId, inv, data) ->
                new ClothSackItemMenu(windowId, inv, data.readInt())));

    public static final DeferredHolder<MenuType<?>, MenuType<RationBoxItemMenu>>
        RATION_BOX_ITEM = MENUS.register("ration_box_item",
            () -> IMenuTypeExtension.create((windowId, inv, data) ->
                new RationBoxItemMenu(windowId, inv, data.readInt())));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
