package henrykado.gaiablossom.quark.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class UndergroundBiomeOvergrown extends BasicUndergroundBiome {

    public static double rootChance = 0.025;
    public static double dirtChance = 0.5;

    public UndergroundBiomeOvergrown() {
        super(Blocks.mossy_cobblestone, Blocks.leaves2, null, 0, 4, 0);
    }

    @Override
    public void finalCeilingPass(World world, int x, int y, int z) {
        if (world.rand.nextDouble() < rootChance) {
            int count = 0;
            for (int i = 0; i < 20; i++) {
                if (isFloor(world, x, y - i, z, world.getBlock(x, y - i, z))) {
                    count = i;
                    break;
                }
            }

            for (int i = 0; i <= count; i++) {
                world.setBlock(x, y - i, z, Block.getBlockFromName("log2"));
            }

        }
    }

    @Override
    public void fillFloor(World world, int x, int y, int z, Block block) {
        if (world.rand.nextDouble() < dirtChance) world.setBlock(x, y, z, Blocks.dirt, 2, 2); // grassless dirt
        else super.fillFloor(world, x, y, z, block);
    }

}
