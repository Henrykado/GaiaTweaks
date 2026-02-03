package henrykado.gaiablossom.quark.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
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
        if (world.rand.nextDouble() < grassChance && world.getBlock(x, y, z) != null)
            applyBonemeal(world, world.rand, x, y, z);

        if (world.rand.nextDouble() < shrubChance) shrubGen.generate(world, world.rand, x, y + 1, z);
    }

    // mc code without BonemealEvent, for mod compat reasons
    private void applyBonemeal(World worldIn, Random random, int x, int y, int z) {
        int l = 0;

        while (l < 128) {
            int i1 = x;
            int j1 = y + 1;
            int k1 = z;
            int l1 = 0;

            while (true) {
                if (l1 < l / 16) {
                    i1 += random.nextInt(3) - 1;
                    j1 += (random.nextInt(3) - 1) * random.nextInt(3) / 2;
                    k1 += random.nextInt(3) - 1;

                    if (worldIn.getBlock(i1, j1 - 1, k1) == Blocks.grass && !worldIn.getBlock(i1, j1, k1)
                        .isNormalCube()) {
                        ++l1;
                        continue;
                    }
                } else if (worldIn.getBlock(i1, j1, k1)
                    .getMaterial() == Material.air) {
                        if (random.nextInt(8) != 0) {
                            if (Blocks.tallgrass.canBlockStay(worldIn, i1, j1, k1)) {
                                worldIn.setBlock(i1, j1, k1, Blocks.tallgrass, 1, 3);
                            }
                        } else {
                            worldIn.getBiomeGenForCoords(i1, k1)
                                .plantFlower(worldIn, random, i1, j1, k1);
                        }
                    }

                ++l;
                break;
            }
        }
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
