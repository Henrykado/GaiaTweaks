package henrykado.gaiablossom.mixin.late.thaumcraft;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import henrykado.gaiablossom.common.block.ModBlock;
import thaumcraft.common.blocks.BlockTaintFibres;
import thaumcraft.common.lib.utils.Utils;

@Mixin(BlockTaintFibres.class)
public class MixinBlockTaintFibres {

    @Redirect(
        method = "func_149674_a",
        at = @At(
            value = "INVOKE",
            target = "Lthaumcraft/common/blocks/BlockTaintFibres;getAdjacentTaint(Lnet/minecraft/world/IBlockAccess;III)I"))
    public int spreadTaintRedirect(IBlockAccess world, int x, int y, int z) {
        int adjacentTaint = BlockTaintFibres.getAdjacentTaint(world, x, y, z);

        if (adjacentTaint >= 1 && (Utils.isWoodLog(world, x, y, z))) {
            ((World) world).setBlock(x, y, z, ModBlock.blockTaintLog, 0, 3);
            ((World) world).addBlockEvent(x, y, z, ModBlock.blockTaintLog, 1, 0);
        }

        return adjacentTaint;
    }
}
