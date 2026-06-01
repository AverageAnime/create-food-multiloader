package net.averageanime.createfood.block.display;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BowlFoodBlock extends DisplayFoodBlock {
    protected final VoxelShape shape;
    protected final double shapeHeight;

    private final boolean hasParticles;
    private final Supplier<ParticleOptions> particleType;

    public BowlFoodBlock(Supplier<Item> displayItem) {
        this(displayItem, 0.0, false, null);
    }

    public BowlFoodBlock(Supplier<Item> displayItem, double heightInPixels) {
        this(displayItem, heightInPixels, false, null);
    }

    public BowlFoodBlock(Supplier<Item> displayItem, double heightInPixels, boolean hasParticles,
                         Supplier<ParticleOptions> particleType) {
        super(Properties.copy(Blocks.OAK_PLANKS), displayItem, 1);
        this.shapeHeight = Mth.clamp(heightInPixels, 1.0, 16.0);
        this.shape = Block.box(5, 0.0, 5, 11, this.shapeHeight, 11);
        this.hasParticles = hasParticles;
        this.particleType = particleType;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                        @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return shape;
    }

    @Override
    protected boolean handlesOwnContainer() { return true; }

    @Override
    protected void dropContainerOnEat(Player player, Level level, BlockPos pos) {
        Block.popResource(level, pos, new ItemStack(Items.BOWL));
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level,
                            @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!hasParticles || particleType == null) return;
        if (random.nextInt(5) == 0) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + (7.5 / 16.0);
            double z = pos.getZ() + 0.5;
            double offsetX = (random.nextDouble() - 0.5) * 0.25;
            double offsetZ = (random.nextDouble() - 0.5) * 0.25;
            level.addParticle(particleType.get(), x + offsetX, y, z + offsetZ, 0.0, 0.02, 0.0);
        }
    }
}
