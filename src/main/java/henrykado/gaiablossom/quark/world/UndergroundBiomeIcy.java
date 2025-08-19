package henrykado.gaiablossom.quark.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import henrykado.gaiablossom.quark.Quark;

public class UndergroundBiomeIcy extends BasicUndergroundBiome {

    public static boolean usePackedIce;
    public static double stalagmiteChance;

    public UndergroundBiomeIcy() {
        super(Blocks.ice, null, null, 0, 0, 0, true);
    }

    @Override
    public void fillCeiling(World world, int x, int y, int z, Block block) {
        // if(UndergroundBiomes.icystoneEnabled)
        world.setBlock(x, y, z, Quark.icystone, 0, 2);
    }

    @Override
    public void fillWall(World world, int x, int y, int z, Block block) {
        fillCeiling(world, x, y, z, block);
    }

    @Override
    public void fillFloor(World world, int x, int y, int z, Block block) {
        Block placeBlock = floorBlock;
        if (usePackedIce) placeBlock = Blocks.packed_ice;

        world.setBlock(x, y, z, placeBlock, 0, 2);

        if (stalagmiteChance > 0 && world.rand.nextDouble() < stalagmiteChance) {
            int height = 3 + world.rand.nextInt(3);
            for (int i = 0; i < height; i++) {
                if (world.getBlock(x, y + 1, z)
                    .isAir(world, x, y + 1, z)) world.setBlock(x, y + 1, z, placeBlock, 0, 2);
                else break;
            }
        }
    }

    static {
        usePackedIce = true;
        stalagmiteChance = 0.015;
    }

}
