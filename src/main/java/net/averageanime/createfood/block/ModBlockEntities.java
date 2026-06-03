package net.averageanime.createfood.block;

import net.averageanime.createfood.CreateFood;
import net.averageanime.createfood.block.blockentity.ClothSackBlockEntity;
import net.averageanime.createfood.block.blockentity.GenericDisplayPlateBlockEntity;
import net.averageanime.createfood.block.blockentity.RationBoxBlockEntity;
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
                            ModDisplayBlocks.GENERIC_DISPLAY_PLATE_BLOCK.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<ClothSackBlockEntity>> CLOTH_SACK =
            BLOCK_ENTITIES.register("cloth_sack", () ->
                    BlockEntityType.Builder.of(
                            ClothSackBlockEntity::new,
                            ModBlocks.CLOTH_SACK_BLOCK.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<RationBoxBlockEntity>> RATION_BOX =
            BLOCK_ENTITIES.register("ration_box", () ->
                    BlockEntityType.Builder.of(
                            RationBoxBlockEntity::new,
                            ModBlocks.RATION_BOX_BLOCK.get())
                            .build(null));

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}
