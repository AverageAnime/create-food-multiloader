package dev.averageanime.fabric.block;

import dev.averageanime.block.type.blockentity.GenericDisplayPlateBlockEntity;
import dev.averageanime.fabric.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.fabric.block.type.blockentity.RationBoxBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static dev.averageanime.fabric.CreateFood.MOD_ID;

public class ModBlockEntities {

    public static BlockEntityType<RationBoxBlockEntity> RATION_BOX;
    public static BlockEntityType<ClothSackBlockEntity> CLOTH_SACK;
    public static BlockEntityType<GenericDisplayPlateBlockEntity> GENERIC_DISPLAY_PLATE;

    @SuppressWarnings("DataFlowIssue")
    public static void init() {
        RATION_BOX = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "ration_box"),
                BlockEntityType.Builder.of(RationBoxBlockEntity::new, ModBlocks.RATION_BOX).build(null));

        CLOTH_SACK = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "cloth_sack"),
                BlockEntityType.Builder.of(ClothSackBlockEntity::new, ModBlocks.CLOTH_SACK).build(null));

        GENERIC_DISPLAY_PLATE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "generic_display_plate"),
                BlockEntityType.Builder.<GenericDisplayPlateBlockEntity>of(
                        dev.averageanime.fabric.block.type.blockentity.GenericDisplayPlateBlockEntity::new,
                        ModDisplayBlocks.GENERIC_DISPLAY_PLATE_BLOCK).build(null));
    }
}
