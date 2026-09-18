package dev.averageanime.forge.menu;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.forge.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.forge.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.forge.menu.type.item.ClothSackItemMenu;
import dev.averageanime.forge.menu.type.block.ClothSackBlockMenu;
import dev.averageanime.forge.menu.type.item.RationBoxItemMenu;
import dev.averageanime.forge.menu.type.block.RationBoxBlockMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class MenuRegistration {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, CreateFoodCommon.MOD_ID);

    public static final RegistryObject<MenuType<RationBoxBlockMenu>>
        RATION_BOX = MENUS.register("ration_box",
            () -> IForgeMenuType.create((windowId, inv, data) -> {
                RationBoxBlockEntity be = (RationBoxBlockEntity) inv.player.level()
                        .getBlockEntity(data.readBlockPos());
                return new RationBoxBlockMenu(windowId, inv, be);
            }));

    public static final RegistryObject<MenuType<ClothSackBlockMenu>>
        CLOTH_SACK = MENUS.register("cloth_sack",
            () -> IForgeMenuType.create((windowId, inv, data) -> {
                ClothSackBlockEntity be = (ClothSackBlockEntity) inv.player.level()
                        .getBlockEntity(data.readBlockPos());
                return new ClothSackBlockMenu(windowId, inv, be);
            }));

    public static final RegistryObject<MenuType<ClothSackItemMenu>>
        CLOTH_SACK_ITEM = MENUS.register("cloth_sack_item",
            () -> IForgeMenuType.create((windowId, inv, data) ->
                new ClothSackItemMenu(windowId, inv, data.readInt())));

    public static final RegistryObject<MenuType<RationBoxItemMenu>>
        RATION_BOX_ITEM = MENUS.register("ration_box_item",
            () -> IForgeMenuType.create((windowId, inv, data) ->
                new RationBoxItemMenu(windowId, inv, data.readInt())));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
