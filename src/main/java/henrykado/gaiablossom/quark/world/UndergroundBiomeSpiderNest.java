package henrykado.gaiablossom.quark.world;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import henrykado.gaiablossom.quark.Quark;
import henrykado.gaiablossom.util.BlockPos;

public class UndergroundBiomeSpiderNest extends BasicUndergroundBiome {

    public static double floorCobwebChance, ceilingCobwebChance, caveSpiderSpawnerChance, nestCobwebChance,
        nestCobwebRange, cobbedstoneChance;

    public UndergroundBiomeSpiderNest() {
        super(Blocks.cobblestone, Blocks.cobblestone, Blocks.cobblestone);
    }

    @Override
    public void fillCeiling(World world, int x, int y, int z, Block block) {
        super.fillCeiling(world, x, y, z, block);
        placeCobweb(world, x, y, z, EnumFacing.DOWN, ceilingCobwebChance);
    }

    private void placeCobweb(World world, int x, int y, int z, EnumFacing off, double chance) {
        if (world.rand.nextDouble() < chance) {
            BlockPos pos = new BlockPos(x, y, z);
            BlockPos placePos = off == null ? pos : pos.offset(off);
            world.setBlock(placePos.x, placePos.y, placePos.z, Blocks.web);
        }
    }

    @Override
    public void fillWall(World world, int x, int y, int z, Block block) {
        if (world.rand.nextDouble() < cobbedstoneChance) world.setBlock(x, y, z, Quark.cobbedstone);
        else super.fillWall(world, x, y, z, block);
    }

    @Override
    public void fillFloor(World world, int x, int y, int z, Block block) {
        if (world.rand.nextDouble() < cobbedstoneChance) world.setBlock(x, y, z, Quark.cobbedstone);
        else super.fillFloor(world, x, y, z, block);

        placeCobweb(world, x, y, z, EnumFacing.UP, floorCobwebChance);
    }

    @Override
    public boolean hasDungeon() {
        return true;
    }

    @Override
    public void spawnDungeon(WorldServer world, BlockPos pos, EnumFacing face) {
        BlockPos spawnerPos = pos.offset(face);
        world.setBlock(spawnerPos.x, spawnerPos.y, spawnerPos.z, Blocks.mob_spawner);

        Class<? extends Entity> e = EntitySpider.class;
        if (world.rand.nextDouble() < caveSpiderSpawnerChance) e = EntityCaveSpider.class;
        TileEntityMobSpawner spawner = (TileEntityMobSpawner) world
            .getTileEntity(spawnerPos.x, spawnerPos.y, spawnerPos.z);
        if (spawner != null) spawner.func_145881_a()
            .func_98267_a(EntityList.getEntityID(new EntitySpider(world)));

        int range = 3;
        for (int x = -range; x < range + 1; x++)
            for (int y = -range; y < range + 1; y++) for (int z = -range; z < range + 1; z++) {
                BlockPos cobwebPos = spawnerPos.add(x, y, z);
                Block blockAt = world.getBlock(cobwebPos.x, cobwebPos.y, cobwebPos.z);
                if (blockAt.isAir(world, cobwebPos.x, cobwebPos.y, cobwebPos.z)
                    || blockAt.isReplaceable(world, cobwebPos.x, cobwebPos.y, cobwebPos.z))
                    placeCobweb(world, cobwebPos.x, cobwebPos.y, cobwebPos.z, null, nestCobwebChance);
            }
    }

    static {
        floorCobwebChance = 0.033;
        ceilingCobwebChance = 0.1;
        caveSpiderSpawnerChance = 0.25;
        nestCobwebChance = 0.5;
        nestCobwebRange = Integer.MAX_VALUE;
        cobbedstoneChance = 0.3;
    }

}
