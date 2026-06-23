package dev.averageanime.block.type.display;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BottleFoodBlock extends DisplayFoodBlock {
    protected final VoxelShape shape;
    protected final double shapeHeight;

    private final boolean hasParticles;
    private final Supplier<ParticleOptions> particleType;

    public BottleFoodBlock(Supplier<Item> displayItem) {
        this(displayItem, 0.0, false, null);
    }

    public BottleFoodBlock(Supplier<Item> displayItem, double heightInPixels) {
        this(displayItem, heightInPixels, false, null);
    }

    public BottleFoodBlock(Supplier<Item> displayItem, double heightInPixels, boolean hasParticles,
                           Supplier<ParticleOptions> particleType) {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS), displayItem, 1);
        this.shapeHeight = Math.clamp(heightInPixels, 1.0, 16.0);
        this.shape = Block.box(5.5, 0.0, 5.5, 10.5, this.shapeHeight, 10.5);
        this.hasParticles = hasParticles;
        this.particleType = particleType;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return shape;
    }

    @Override
    protected boolean handlesOwnContainer() { return true; }

    @Override
    protected void applyEatEffects(ItemStack copy, FoodProperties props, Player player, Level level, BlockPos pos) {
        player.getFoodData().eat(props.nutrition(), props.saturation());
        for (FoodProperties.PossibleEffect entry : props.effects()) {
            if (level.random.nextFloat() < entry.probability()) {
                player.addEffect(entry.effect());
            }
        }
    }

    @Override
    protected void dropContainerOnEat(Player player, Level level, BlockPos pos) {
        Block.popResource(level, pos, new ItemStack(Items.GLASS_BOTTLE));
    }

    @Override
    protected SoundEvent getEatSound() { return SoundEvents.HONEY_DRINK; }

    @Override
    public SoundEvent getAddSound() {
        return SoundEvents.BOTTLE_FILL;
    }

    @Override
    protected SoundEvent getRemoveSound() {
        return SoundEvents.BOTTLE_EMPTY;
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!hasParticles || particleType == null) return;

        if (random.nextInt(5) == 0) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;
            double offsetX = (random.nextDouble() - 0.5) * 0.25;
            double offsetZ = (random.nextDouble() - 0.5) * 0.25;
            level.addParticle(particleType.get(), x + offsetX, y, z + offsetZ, 0.0, 0.02, 0.0);
        }
    }
}