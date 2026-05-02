package dev.averageanime.neoforge.block.type.blockentity;

import dev.averageanime.CommonClass;
import dev.averageanime.neoforge.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CommonClass.MOD_ID);

    @SuppressWarnings("DataFlowIssue")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RationBoxBlockEntity>>
        RATION_BOX = BLOCK_ENTITIES.register("ration_box",
            () -> BlockEntityType.Builder
                .of(RationBoxBlockEntity::new, ModBlocks.RATION_BOX.get())
                .build(null));

    @SuppressWarnings("DataFlowIssue")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClothSackBlockEntity>>
        CLOTH_SACK = BLOCK_ENTITIES.register("cloth_sack",
            () -> BlockEntityType.Builder
                .of(ClothSackBlockEntity::new, ModBlocks.CLOTH_SACK.get())
                .build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
