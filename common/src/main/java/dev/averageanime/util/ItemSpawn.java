package dev.averageanime.util;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class ItemSpawn {

    private ItemSpawn() {}

    public static void spawnItemEntity(Level level, ItemStack stack,
                                       double x, double y, double z,
                                       double motionX, double motionY, double motionZ) {
        if (level.isClientSide) return;
        ItemEntity entity = new ItemEntity(level, x, y, z, stack);
        entity.setDeltaMovement(motionX, motionY, motionZ);
        entity.setDefaultPickUpDelay();
        level.addFreshEntity(entity);
    }
}
