package net.averageanime.createfood.block;

import net.averageanime.createfood.CreateFood;
import net.averageanime.createfood.block.blockentity.GenericDisplayPlateBlockEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CreateFood.ID);

    public static final RegistryObject<BlockEntityType<GenericDisplayPlateBlockEntity>> GENERIC_DISPLAY_PLATE =
            BLOCK_ENTITIES.register("generic_display_plate", () ->
                    BlockEntityType.Builder.of(
                            (pos, state) -> new GenericDisplayPlateBlockEntity(pos, state),
                            ModBlocks.GENERIC_DISPLAY_PLATE.get())
                            .build(null));

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}
