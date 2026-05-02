package dev.averageanime.neoforge.menu;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.neoforge.block.type.blockentity.RationBoxBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, CommonClass.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<RationBoxMenu>>
        RATION_BOX = MENUS.register("ration_box",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> {
                RationBoxBlockEntity be = (RationBoxBlockEntity) inv.player.level()
                        .getBlockEntity(data.readBlockPos());
                return new RationBoxMenu(windowId, inv, be);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<ClothSackMenu>>
        CLOTH_SACK = MENUS.register("cloth_sack",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> {
                ClothSackBlockEntity be = (ClothSackBlockEntity) inv.player.level()
                        .getBlockEntity(data.readBlockPos());
                return new ClothSackMenu(windowId, inv, be);
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
