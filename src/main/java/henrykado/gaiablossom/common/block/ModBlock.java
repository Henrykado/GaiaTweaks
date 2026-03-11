package henrykado.gaiablossom.common.block;

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
            blockTaintLog = new BlockTaintLog();
        }

        if (Config.betterBattleTowerSpawner && Loader.isModLoaded("BattleTowers")) {
            blockTowerSpawner = new BlockMobSpawnerTower();
            GameRegistry.registerTileEntity(TileEntityMobSpawnerTower.class, "gaiablossom:towerMobSpawner");
        }

        if (Config.autumnForest) {
            redLeaves = new BlockModLeaves("autumn_red");
            orangeLeaves = new BlockModLeaves("autumn_orange");
            deadLeaves = new BlockDeadLeaves().setBlockName("dead_leaves")
                .setBlockTextureName("dead_leaves");
        }
    }

    public ModBlock(Material material, String name) {
        super(material);

        setBlockName(name);
        GameRegistry.registerBlock(this, name);
    }

    public static void registerBlock(Block block, String name, boolean setTextureName) {
        block.setBlockName(name);
        GameRegistry.registerBlock(block, name);
        if (setTextureName) {
            block.setBlockTextureName("gaiablossom:" + name);
        }
    }

    public static void registerBlock(Block block, String name) {
        registerBlock(block, name, true);
    }
}
