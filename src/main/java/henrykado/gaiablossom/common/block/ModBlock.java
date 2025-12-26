package henrykado.gaiablossom.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import henrykado.gaiablossom.Config;
import henrykado.gaiablossom.common.block.tile.TileEntityMobSpawnerTower;

public class ModBlock extends Block {

    public static Block blockTaintLog;
    public static Block blockTowerSpawner;

    public static void registerEmBlocks() {
        if (Loader.isModLoaded("Thaumcraft")) {
            blockTaintLog = new BlockTaintLog();
        }

        if (Config.betterBattleTowerSpawner && Loader.isModLoaded("BattleTowers")) {
            blockTowerSpawner = new BlockMobSpawnerTower();
            GameRegistry.registerTileEntity(TileEntityMobSpawnerTower.class, "gaiablossom:towerMobSpawner");
        }
    }

    public ModBlock(Material par2Material) {
        super(par2Material);
    }

    @Override
    public Block setBlockName(String par1Str) {
        this.setBlockTextureName("gaiablossom:" + par1Str);
        GameRegistry.registerBlock(this, par1Str);

        return super.setBlockName(par1Str);
    }
}
