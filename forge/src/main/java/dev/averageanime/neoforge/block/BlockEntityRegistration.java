package dev.averageanime.forge.block;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.forge.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.forge.block.type.blockentity.GenericDisplayBlockEntity;
import dev.averageanime.forge.block.type.blockentity.RationBoxBlockEntity;
import dev.averageanime.forge.block.type.blockentity.LargeBowlBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class BlockEntityRegistration {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CreateFoodCommon.MOD_ID);

    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<RationBoxBlockEntity>>
        RATION_BOX = BLOCK_ENTITIES.register("ration_box",
            () -> BlockEntityType.Builder
                .of(RationBoxBlockEntity::new, BlockRegistration.RATION_BOX.get())
                .build(null));

    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<ClothSackBlockEntity>>
        CLOTH_SACK = BLOCK_ENTITIES.register("cloth_sack",
            () -> BlockEntityType.Builder
                .of(ClothSackBlockEntity::new, BlockRegistration.CLOTH_SACK.get())
                .build(null));

    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<GenericDisplayBlockEntity>>
        GENERIC_DISPLAY_PLATE = BLOCK_ENTITIES.register("generic_display_plate",
            () -> BlockEntityType.Builder
                .of(GenericDisplayBlockEntity::new,
                    DisplayBlockRegistration.GENERIC_DISPLAY_PLATE_BLOCK.get(),
                    DisplayBlockRegistration.GENERIC_DISPLAY_BOWL_BLOCK.get())
                .build(null));

    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<LargeBowlBlockEntity>>
        LARGE_BOWL = BLOCK_ENTITIES.register("large_bowl",
            () -> BlockEntityType.Builder
                .of(LargeBowlBlockEntity::new, DisplayBlockRegistration.LARGE_BOWL_BLOCK.get())
                .build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
