package henrykado.gaiablossom.quark.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import ganymedes01.etfuturum.ModBlocks;

public class UndergroundBiomeSlime extends BasicUndergroundBiome {

    public static double slimeBlockChance;
    public static boolean waterFloor;

    public UndergroundBiomeSlime() {
        super(Blocks.water, null, null);
    }

    @Override
    public void fillCeiling(World world, int x, int y, int z, Block block) {
        Block setBlock = Blocks.stained_hardened_clay;
        int setMeta = 0;
        switch (world.rand.nextInt(7)) {
            case 0:
            case 1:
            case 2:
                // setBlock = setBlock.withProperty(BlockColored.COLOR, EnumDyeColor.GREEN);
                setMeta = 13;
                break;
            case 3:
            case 4:
            case 5:
                // setBlock = setBlock.withProperty(BlockColored.COLOR, EnumDyeColor.LIME);
                setMeta = 5;
                break;
            case 6:
                // setBlock = setBlock.withProperty(BlockColored.COLOR, EnumDyeColor.LIGHT_BLUE);
                setMeta = 3;
        }

        world.setBlock(x, y, z, setBlock, setMeta, 2);
    }

    @Override
    public void fillWall(World world, int x, int y, int z, Block block) {
        fillCeiling(world, x, y, z, block);
    }

    @Override
    public void fillFloor(World world, int x, int y, int z, Block block) {
        if (waterFloor) world.setBlock(x, y, z, floorBlock, floorMeta, 3);
        else fillCeiling(world, x, y, z, block);

        if (world.rand.nextDouble() < 0.05) world.setBlock(x, y, z, ModBlocks.SLIME.get());
    }

    static {
        waterFloor = false;
    }

}
