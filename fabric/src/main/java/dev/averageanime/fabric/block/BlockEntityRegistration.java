package dev.averageanime.fabric.block;

import dev.averageanime.CreateFoodCommon;
import dev.averageanime.block.type.blockentity.GenericDisplayBlockEntity;
import dev.averageanime.block.type.blockentity.SmallBowlBlockEntity;
import dev.averageanime.fabric.block.type.blockentity.ClothSackBlockEntity;
import dev.averageanime.fabric.block.type.blockentity.RationBoxBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;


public class BlockEntityRegistration {

    public static BlockEntityType<RationBoxBlockEntity> RATION_BOX;
    public static BlockEntityType<ClothSackBlockEntity> CLOTH_SACK;
    public static BlockEntityType<GenericDisplayBlockEntity> GENERIC_DISPLAY_PLATE;
    public static BlockEntityType<SmallBowlBlockEntity> SMALL_BOWL;

    @SuppressWarnings("DataFlowIssue")
    public static void init() {
        RATION_BOX = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "ration_box"),
                BlockEntityType.Builder.of(RationBoxBlockEntity::new, BlockRegistration.RATION_BOX).build(null));

        CLOTH_SACK = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "cloth_sack"),
                BlockEntityType.Builder.of(ClothSackBlockEntity::new, BlockRegistration.CLOTH_SACK).build(null));

        GENERIC_DISPLAY_PLATE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "generic_display_plate"),
                BlockEntityType.Builder.<GenericDisplayBlockEntity>of(
                        dev.averageanime.fabric.block.type.blockentity.GenericDisplayBlockEntity::new,
                        DisplayBlockRegistration.GENERIC_DISPLAY_PLATE_BLOCK,
                        DisplayBlockRegistration.GENERIC_DISPLAY_BOWL_BLOCK).build(null));

        SMALL_BOWL = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(CreateFoodCommon.MOD_ID, "small_bowl"),
                BlockEntityType.Builder.<SmallBowlBlockEntity>of(
                        dev.averageanime.fabric.block.type.blockentity.SmallBowlBlockEntity::new,
                        DisplayBlockRegistration.SMALL_BOWL_BLOCK).build(null));
    }
}
