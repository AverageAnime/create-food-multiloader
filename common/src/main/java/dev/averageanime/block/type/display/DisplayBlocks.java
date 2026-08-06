package dev.averageanime.block.type.display;

import dev.averageanime.block.type.bowl.BowlBlock;
import dev.averageanime.block.type.bowl.BowlFoodBlock;
import dev.averageanime.block.type.bowl.SmallBowlFoodBlock;
import dev.averageanime.block.type.plate.PlateBlock;
import dev.averageanime.block.type.plate.PlateFoodBlock;
import dev.averageanime.block.type.plate.SmallPlateBlock;
import dev.averageanime.platform.Services;
import dev.averageanime.registry.DisplayRegistry.DisplayType;
import dev.averageanime.registry.type.DisplayEntry;
import net.minecraft.world.level.block.Block;

public final class DisplayBlocks {

    private DisplayBlocks() {}

    public static Block createBlock(java.util.function.Supplier<net.minecraft.world.item.Item> itemSupplier,
                                    DisplayEntry config) {
        return switch (config.type()) {
            case PLATE -> new PlateBlock(itemSupplier, config.maxStack(), Services.PLATFORM::getPlateBlock);
            case SMALL_PLATE -> new SmallPlateBlock(itemSupplier, Services.PLATFORM::getSmallPlateBlock);
            case BOTTLE -> new BottleFoodBlock(itemSupplier, config.height(), config.hasParticles(), config.particleType());
            case BOWL -> new BowlBlock(itemSupplier, config.maxStack(), Services.PLATFORM::getBowlBlock);
            case BOWL_FOOD -> new BowlFoodBlock(itemSupplier, config.height(), config.hasParticles(), config.particleType());
            case SMALL_BOWL -> new SmallBowlFoodBlock(itemSupplier);
            case PLATE_FOOD -> new PlateFoodBlock(itemSupplier);
        };
    }

    public static String suffixKey(DisplayType type) {
        return switch (type) {
            case PLATE, SMALL_PLATE, PLATE_FOOD -> "display.createfood.suffix.plate";
            case BOWL, BOWL_FOOD -> "display.createfood.suffix.bowl";
            case SMALL_BOWL -> "display.createfood.suffix.small_bowl";
            case BOTTLE -> "display.createfood.suffix.bottle";
        };
    }
}
