package dev.averageanime.compat.jade;

import dev.averageanime.block.type.display.EmptyBottleBlock;
import dev.averageanime.block.type.cake.CakeFoodBlock;
import dev.averageanime.block.type.cake.CakeCandleBlock;
import dev.averageanime.block.type.misc.ConsumableBlock;
import dev.averageanime.block.type.bowl.EmptyBowlBlock;
import dev.averageanime.block.type.bowl.EmptyLargeBowlBlock;
import dev.averageanime.block.type.bowl.GenericDisplayBowlBlock;
import dev.averageanime.block.type.cake.CakeBaseBlock;
import dev.averageanime.block.type.display.FoodBlock;
import dev.averageanime.block.type.pie.RawPieBlock;
import dev.averageanime.block.type.pie.RawPizzaBlock;
import dev.averageanime.block.type.plate.EmptyPlateBlock;
import dev.averageanime.block.type.plate.EmptySmallPlateBlock;
import dev.averageanime.block.type.plate.GenericDisplayPlateBlock;
import dev.averageanime.block.type.storage.ClothSackBlock;
import dev.averageanime.block.type.storage.RationBoxBlock;
import net.minecraft.world.entity.item.ItemEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class CreateFoodJadePlugin implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(PlateJadeProvider.INSTANCE, GenericDisplayPlateBlock.class);
        registration.registerBlockComponent(PlateJadeProvider.INSTANCE, GenericDisplayBowlBlock.class);
        registration.registerBlockIcon(PlateJadeProvider.INSTANCE, GenericDisplayPlateBlock.class);
        registration.registerBlockIcon(PlateJadeProvider.INSTANCE, GenericDisplayBowlBlock.class);
        registration.registerBlockIcon(EmptyDisplayJadeProvider.INSTANCE, EmptyPlateBlock.class);
        registration.registerBlockIcon(EmptyDisplayJadeProvider.INSTANCE, EmptySmallPlateBlock.class);
        registration.registerBlockIcon(EmptyDisplayJadeProvider.INSTANCE, EmptyBowlBlock.class);
        registration.registerBlockIcon(EmptyDisplayJadeProvider.INSTANCE, EmptyBottleBlock.class);
        registration.registerBlockComponent(LargeBowlJadeProvider.INSTANCE, EmptyLargeBowlBlock.class);
        registration.registerBlockIcon(LargeBowlJadeProvider.INSTANCE, EmptyLargeBowlBlock.class);
        registration.registerBlockComponent(ClothSackJadeProvider.INSTANCE, ClothSackBlock.class);
        registration.registerBlockComponent(RationBoxJadeProvider.INSTANCE, RationBoxBlock.class);
        registration.registerBlockComponent(FoodBlockJadeProvider.INSTANCE, FoodBlock.class);
        registration.registerBlockComponent(ConsumableBlockJadeProvider.INSTANCE, ConsumableBlock.class);
        registration.registerBlockComponent(ModCakeBlockJadeProvider.INSTANCE, CakeFoodBlock.class);
        registration.registerBlockComponent(CandleCakeJadeProvider.INSTANCE, CakeCandleBlock.class);
        registration.registerBlockComponent(BlockItemTooltipJadeProvider.INSTANCE, CakeBaseBlock.class);
        registration.registerBlockComponent(BlockItemTooltipJadeProvider.INSTANCE, RawPieBlock.class);
        registration.registerBlockComponent(BlockItemTooltipJadeProvider.INSTANCE, RawPizzaBlock.class);
        registration.registerEntityComponent(ItemEntityStorageJadeProvider.INSTANCE, ItemEntity.class);
    }
}
