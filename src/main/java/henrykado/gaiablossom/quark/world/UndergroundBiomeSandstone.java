package henrykado.gaiablossom.quark.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class UndergroundBiomeSandstone extends BasicUndergroundBiome {

    public static double stalactiteChance, chiseledSandstoneChance, deadBushChance;
    public static boolean enableSand, allowGenInMesa;

    public UndergroundBiomeSandstone() {
        super(Blocks.sandstone, Blocks.sandstone, Blocks.sandstone, 0, 0, 0);
    }

    @Override
    public boolean isValidBiome(BiomeGenBase biome) {
        return allowGenInMesa || !BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MESA);
    }

    @Override
    public void fillCeiling(World world, int x, int y, int z, Block state) {
        if (world.rand.nextDouble() < stalactiteChance) world.setBlock(x, y - 1, z, ceilingBlock, ceilingMeta, 2);

        super.fillCeiling(world, x, y, z, state);
    }

    @Override
    public void fillFloor(World world, int x, int y, int z, Block state) {
        if (enableSand && world.rand.nextBoolean()) {
            world.setBlock(x, y, z, Blocks.sand, 0, 2);
            if (world.rand.nextDouble() < deadBushChance) world.setBlock(x, y + 1, z, Blocks.deadbush, 0, 2);
        } else super.fillFloor(world, x, y, z, state);
    }

    @Override
    public void fillWall(World world, int x, int y, int z, Block state) {
        if (world.rand.nextDouble() < chiseledSandstoneChance)
            world.setBlock(x, y, z, wallBlock /* chiseled sandstone */, wallMeta, 2);
        else super.fillWall(world, x, y, z, state);
    }

    static {
        stalactiteChance = 0.1;
        chiseledSandstoneChance = 0.1;
        deadBushChance = 0.05;
        enableSand = true;
        allowGenInMesa = false;
    }

}
