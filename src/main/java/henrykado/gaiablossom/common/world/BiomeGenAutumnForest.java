package henrykado.gaiablossom.common.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import henrykado.gaiablossom.common.block.ModBlock;

public class BiomeGenAutumnForest extends BiomeGenBase {

    public static WorldGenAbstractTree redLeavesTree = new WorldGenModTree(false, 5, ModBlock.redLeaves);
    public static WorldGenAbstractTree orangeLeavesTree = new WorldGenModTree(false, 5, ModBlock.orangeLeaves);

    public BiomeGenAutumnForest(int p_i1971_1_) {
        super(p_i1971_1_);

        setColor(353825);
        setBiomeName("Autumnal Forest");
        // setTemperatureRainfall();

        theBiomeDecorator.treesPerChunk = 11;
        theBiomeDecorator.grassPerChunk = 3;
    }

    /*
     * @SideOnly(Side.CLIENT)
     * public int getBiomeFoliageColor(int p_150571_1_, int p_150571_2_, int p_150571_3_) {
     * return 5882165;
     * }
     */

    @Override
    public WorldGenAbstractTree func_150567_a(Random random) {
        return switch (random.nextInt(3)) {
            case 0 -> redLeavesTree;
            case 1 -> orangeLeavesTree;
            default -> random.nextInt(10) == 0 ? worldGeneratorBigTree : worldGeneratorTrees;
        };
    }

    public void decorate(World world, Random random, int x, int z) {
        int k = random.nextInt(5) - 3;

        int l = 0;

        while (l < k) {
            int i1 = random.nextInt(3);

            if (i1 == 0) {
                genTallFlowers.func_150548_a(1);
            } else if (i1 == 1) {
                genTallFlowers.func_150548_a(4);
            } else {
                genTallFlowers.func_150548_a(5);
            }

            int j1 = 0;

            while (true) {
                if (j1 < 5) {
                    int k1 = x + random.nextInt(16) + 8;
                    int i2 = z + random.nextInt(16) + 8;
                    int l1 = random.nextInt(world.getHeightValue(k1, i2) + 32);

                    if (!genTallFlowers.generate(world, random, k1, l1, i2)) {
                        ++j1;
                        continue;
                    }
                }

                ++l;
                break;
            }
        }

        super.decorate(world, random, x, z);
    }
}
