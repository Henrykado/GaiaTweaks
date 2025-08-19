package henrykado.gaiablossom.quark.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenShrub;

import henrykado.gaiablossom.util.BlockPos;
import henrykado.gaiablossom.util.Constants;

public class UndergroundBiomeLush extends BasicUndergroundBiome {

    private final WorldGenShrub shrubGen = new WorldGenShrub(3, 0);

    public static double grassChance, shrubChance, vineChance;

    public UndergroundBiomeLush() {
        super(Blocks.grass, null, null);
    }

    @Override
    public void finalFloorPass(World world, int x, int y, int z) {
        if (world.rand.nextDouble() < grassChance)
            ItemDye.applyBonemeal(new ItemStack(Items.dye, 1, 14), world, x, y, z, null);

        if (world.rand.nextDouble() < shrubChance) shrubGen.generate(world, world.rand, x, y + 1, z);
    }

    @Override
    public void finalWallPass(World world, int x, int y, int z) {
        for (EnumFacing facing : Constants.HORIZONTALS) {
            BlockPos pos = new BlockPos(x, y, z);
            BlockPos off = pos.offset(facing);
            BlockPos up = off.up();
            if (isCeiling(world, up.x, up.y, up.z, world.getBlock(up.x, up.y, up.z))
                && world.rand.nextDouble() < vineChance) {
                Block blockAt = world.getBlock(off.x, off.y, off.z);
                boolean did = false;
                while (blockAt.isAir(world, off.x, off.y, off.z) && off.y > 0) {
                    world.setBlock(
                        off.x,
                        off.y,
                        off.z,
                        Blocks.vine /* .withProperty(BlockVine.getPropertyFor(facing.getOpx, y, zite()), true) */,
                        0,
                        2);
                    off = off.down();
                    blockAt = world.getBlock(off.x, off.y, off.z);
                    did = true;
                }

                if (did) return;
            }
        }
    }

    static {
        grassChance = 0.05;
        shrubChance = 0.01;
        vineChance = 0.125;
    }

}
