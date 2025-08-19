package henrykado.gaiablossom.quark.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import ganymedes01.etfuturum.ModBlocks;

public class UndergroundBiomePrismarine extends BasicUndergroundBiome {

    public static double seaLanternChance, waterChance;
    private Block lanternBlock;
    public static boolean spawnElderPrismarine;

    public UndergroundBiomePrismarine() {

        super(ModBlocks.PRISMARINE_BLOCK.get(), ModBlocks.PRISMARINE_BLOCK.get(), ModBlocks.PRISMARINE_BLOCK.get());
        lanternBlock = ModBlocks.SEA_LANTERN.get();
    }

    public void update() {
        // boolean elder = UndergroundBiomes.elderPrismarineEnabled && spawnElderPrismarine;

        Block prismarineBlock = /* (elder ? UndergroundBiomes.elder_prismarine : */ ModBlocks.PRISMARINE_BLOCK
            .get()/* ) */;
        ceilingBlock = floorBlock = wallBlock = prismarineBlock;

        lanternBlock = /* (elder ? UndergroundBiomes.elder_sea_lantern : */ ModBlocks.SEA_LANTERN.get()/* ) */;
    }

    @Override
    public void fillWall(World world, int x, int y, int z, Block block) {
        super.fillWall(world, x, y, z, block);

        if (world.rand.nextDouble() < seaLanternChance) world.setBlock(x, y, z, lanternBlock, 0, 2);
    }

    @Override
    public void fillFloor(World world, int x, int y, int z, Block block) {
        if (world.rand.nextDouble() < waterChance && !isBorder(world, x, y, z)) world.setBlock(x, y, z, Blocks.water);
        else super.fillFloor(world, x, y, z, block);
    }

    static {
        seaLanternChance = 0.0085;
        waterChance = 0.25;
        spawnElderPrismarine = true;
    }

}
