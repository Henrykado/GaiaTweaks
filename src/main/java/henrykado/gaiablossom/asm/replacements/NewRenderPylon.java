package henrykado.gaiablossom.asm.replacements;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import vazkii.botania.client.lib.LibRenderIDs;
import vazkii.botania.common.block.tile.TilePylon;

public class NewRenderPylon implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        TilePylon tilePylon = new TilePylon();
        NewRenderTilePylon pylonRenderer = (NewRenderTilePylon) (TileEntityRendererDispatcher.instance
            .getSpecialRenderer(tilePylon));
        if (pylonRenderer != null) {
            pylonRenderer.renderPylon(tilePylon, 0.0, 0.0, 0.0, 0.0F, true, metadata);
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return LibRenderIDs.idPylon;
    }
}
