package dev.averageanime.neoforge.block.type.display;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BowlFoodBlock extends FoodBlock {
    protected final VoxelShape shape;
    protected final double shapeHeight; // Height in pixels (0-16)

    private final boolean hasParticles;
    private final Supplier<ParticleOptions> particleType;

    // Constructor without particles and default height
    public BowlFoodBlock(Supplier<Item> displayItem) {
        this(displayItem, 0.0, false, null);
    }

    // Constructor with custom height, no particles
    public BowlFoodBlock(Supplier<Item> displayItem, double heightInPixels) {
        this(displayItem, heightInPixels, false, null);
    }

    // Full constructor with height and particle support
    public BowlFoodBlock(Supplier<Item> displayItem, double heightInPixels, boolean hasParticles, Supplier<ParticleOptions> particleType) {
        super(Properties.ofFullCopy(Blocks.OAK_PLANKS), displayItem, 1);
        this.shapeHeight = Math.max(1.0, Math.min(16.0, heightInPixels));
        this.shape = Block.box(5, 0.0, 5, 11, this.shapeHeight, 11);
        this.hasParticles = hasParticles;
        this.particleType = particleType;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return shape;
    }

    @Override
    protected void handleLastItemRemoved(BlockState state, Level level, BlockPos pos) {
        level.removeBlock(pos, false);
    }

    @Override
    public SoundEvent getAddSound() {
        return SoundEvents.WOOD_PLACE;
    }

    @Override
    protected SoundEvent getRemoveSound() {
        return SoundEvents.WOOD_BREAK;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!hasParticles || particleType == null) {
            return;
        }

        // Spawn particles at the top of the bottle
        if (random.nextInt(5) == 0) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + (7.5 / 16.0);
            double z = pos.getZ() + 0.5;

            // Add slight randomness to x and z position
            double offsetX = (random.nextDouble() - 0.5) * 0.25;
            double offsetZ = (random.nextDouble() - 0.5) * 0.25;

            level.addParticle(particleType.get(),
                    x + offsetX,
                    y,
                    z + offsetZ,
                    0.0, 0.02, 0.0); // Small upward velocity
        }
    }
}