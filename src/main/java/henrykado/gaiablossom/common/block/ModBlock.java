package henrykado.gaiablossom.common.block;

import henrykado.gaiablossom.GaiaBlossom;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import henrykado.gaiablossom.Config;
import henrykado.gaiablossom.common.block.tileentity.TileEntityMobSpawnerTower;

public class ModBlock extends Block {

    public static Block blockTaintLog;
    public static Block blockTowerSpawner;
    public static Block redLeaves;
    public static Block orangeLeaves;
    public static Block deadLeaves;

    public static void registerEmBlocks() {
        if (Loader.isModLoaded("Thaumcraft") && Config.taintedTrees) {
            blockTaintLog = new BlockTaintLog("log_taintwood");
        }

        if (Config.betterBattleTowerSpawner && Loader.isModLoaded("BattleTowers")) {
            blockTowerSpawner = new BlockMobSpawnerTower("towerMobSpawner", "mob_spawner");
            GameRegistry.registerTileEntity(TileEntityMobSpawnerTower.class, "gaiablossom:towerMobSpawner");
        }

        if (Config.autumnForest) {
            redLeaves = new BlockModLeaves("autumn_red", 0xdf3e23);
            orangeLeaves = new BlockModLeaves("autumn_orange", 0xde662a);
            deadLeaves = new BlockDeadLeaves().register("dead_leaves");
        }
    }

    public static void registerBlock(Block block, String name, boolean setTextureName) {
        block.setBlockName(name);
        if (setTextureName) {
            block.setBlockTextureName(GaiaBlossom.MODID + ":" + name);
        }
        GameRegistry.registerBlock(block, name);
    }

    public static void registerBlock(Block block, String name) {
        registerBlock(block, name, true);
    }


    public ModBlock(Material material) {
        super(material);
    }

    public Block register(String name, boolean setTextureName) {
        setBlockName(name);
        if (setTextureName) {
            setBlockTextureName(GaiaBlossom.MODID + ":" + name);
        }
        GameRegistry.registerBlock(this, name);
        return this;
    }

    public Block register(String name) {
        return register(name, true);
    }
}
