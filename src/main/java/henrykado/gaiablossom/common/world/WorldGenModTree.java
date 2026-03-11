package henrykado.gaiablossom.common.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

import henrykado.gaiablossom.common.block.ModBlock;

public class WorldGenModTree extends WorldGenAbstractTree {

    public int minTreeHeight = 5;
    public Block leaves;

    public WorldGenModTree(boolean p_i45448_1_) {
        super(p_i45448_1_);
    }

    public WorldGenModTree(boolean p_i45448_1_, int minTreeHeight, Block leaves) {
        super(p_i45448_1_);
        this.minTreeHeight = minTreeHeight;
        this.leaves = leaves;
    }

    public boolean generate(World world, Random random, int x, int y, int z) {
        int l = random.nextInt(3) + minTreeHeight;
        boolean flag = true;

        if (y >= 1 && y + l + 1 <= 256) {
            byte b0;
            int k1;
            Block block;

            for (int i1 = y; i1 <= y + 1 + l; ++i1) {
                b0 = 1;

                if (i1 == y) {
                    b0 = 0;
                }

                if (i1 >= y + 1 + l - 2) {
                    b0 = 2;
                }

                for (int j1 = x - b0; j1 <= x + b0 && flag; ++j1) {
                    for (k1 = z - b0; k1 <= z + b0 && flag; ++k1) {
                        if (i1 >= 0 && i1 < 256) {
                            block = world.getBlock(j1, i1, k1);

                            if (!this.isReplaceable(world, j1, i1, k1)) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else {
                Block block2 = world.getBlock(x, y - 1, z);

                boolean isSoil = block2
                    .canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (BlockSapling) Blocks.sapling);
                if (isSoil && y < 256 - l - 1) {
                    block2.onPlantGrow(world, x, y - 1, z, x, y, z);
                    b0 = 3;
                    byte b1 = 0;
                    int l1;
                    int i2;
                    int j2;
                    int i3;

                    for (k1 = y - b0 + l; k1 <= y + l; ++k1) {
                        i3 = k1 - (y + l);
                        l1 = b1 + 1 - i3 / 2;

                        for (i2 = x - l1; i2 <= x + l1; ++i2) {
                            j2 = i2 - x;

                            for (int k2 = z - l1; k2 <= z + l1; ++k2) {
                                int l2 = k2 - z;

                                if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || random.nextInt(2) != 0 && i3 != 0) {
                                    Block block1 = world.getBlock(i2, k1, k2);

                                    if (block1.isAir(world, i2, k1, k2) || block1.isLeaves(world, i2, k1, k2)) {
                                        this.setBlockAndNotifyAdequately(world, i2, k1, k2, leaves, 0);
                                    }
                                }
                            }
                        }
                    }

                    for (k1 = 0; k1 < l; ++k1) {
                        block = world.getBlock(x, y + k1, z);

                        if (block.isAir(world, x, y + k1, z) || block.isLeaves(world, x, y + k1, z)) {
                            this.setBlockAndNotifyAdequately(world, x, y + k1, z, Blocks.log2, 1);
                        }
                    }

                    // yes i know this code is ass ok im sorry
                    for (int lx = -3; lx <= 3; lx++) {
                        if (lx == 0) continue;
                        for (int lz = -3; lz <= 3; lz++) {
                            if (lz == 0 || (Math.abs(lx) == 3 && Math.abs(lz) == 3) || random.nextInt(3) > 0) continue;
                            Block blockBelow = world.getBlock(x + lx, y - 1, z + lz);
                            if (blockBelow != null && blockBelow.getMaterial() == Material.grass) {
                                world.setBlock(x + lx, y, z + lz, ModBlock.deadLeaves);
                            }
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
}
