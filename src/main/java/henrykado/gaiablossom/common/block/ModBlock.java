package henrykado.gaiablossom.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import cpw.mods.fml.common.registry.GameRegistry;
import henrykado.gaiablossom.CustomCreativeTab;

public class ModBlock extends Block {

    public static Block blockTaintLog;

    public static void registerEmBlocks() {
        blockTaintLog = new BlockTaintLog();
    }

    public ModBlock(Material par2Material) {
        super(par2Material);
        this.setCreativeTab(CustomCreativeTab.INSTANCE);
    }

    @Override
    public Block setBlockName(String par1Str) {
        this.setBlockTextureName("gaiablossom:" + par1Str);
        GameRegistry.registerBlock(this, par1Str);

        return super.setBlockName(par1Str);
    }
}
