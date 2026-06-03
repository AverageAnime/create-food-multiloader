package net.averageanime.createfood.menu;

import net.averageanime.createfood.CreateFood;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, CreateFood.ID);

    public static final RegistryObject<MenuType<ClothSackMenu>> CLOTH_SACK =
            MENU_TYPES.register("cloth_sack", () -> IForgeMenuType.create((windowId, inv, data) -> {
                net.minecraft.core.BlockPos pos = data.readBlockPos();
                net.averageanime.createfood.block.blockentity.ClothSackBlockEntity be =
                        (net.averageanime.createfood.block.blockentity.ClothSackBlockEntity)
                        inv.player.level().getBlockEntity(pos);
                return new ClothSackMenu(windowId, inv, be);
            }));

    public static final RegistryObject<MenuType<RationBoxMenu>> RATION_BOX =
            MENU_TYPES.register("ration_box", () -> IForgeMenuType.create((windowId, inv, data) -> {
                net.minecraft.core.BlockPos pos = data.readBlockPos();
                net.averageanime.createfood.block.blockentity.RationBoxBlockEntity be =
                        (net.averageanime.createfood.block.blockentity.RationBoxBlockEntity)
                        inv.player.level().getBlockEntity(pos);
                return new RationBoxMenu(windowId, inv, be);
            }));

    public static final RegistryObject<MenuType<ClothSackItemMenu>> CLOTH_SACK_ITEM =
            MENU_TYPES.register("cloth_sack_item",
                    () -> IForgeMenuType.create((windowId, inv, data) ->
                            new ClothSackItemMenu(windowId, inv, data.readInt())));

    public static final RegistryObject<MenuType<RationBoxItemMenu>> RATION_BOX_ITEM =
            MENU_TYPES.register("ration_box_item",
                    () -> IForgeMenuType.create((windowId, inv, data) ->
                            new RationBoxItemMenu(windowId, inv, data.readInt())));

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
