package henrykado.gaiablossom.mixin.late.botania;

import net.minecraft.init.Blocks;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import vazkii.botania.api.lexicon.multiblock.Multiblock;
import vazkii.botania.api.lexicon.multiblock.MultiblockSet;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.TileEnchanter;

@Mixin(TileEnchanter.class)
public class MixinTileEnchanter {

    @Unique
    private static final int[][] NPYLON_LOCATIONS = new int[][] { { 4, 2, 0 }, { 2, 2, -3 }, { -2, 2, -3 },
        { -4, 2, 0 }, { -2, 2, 3 }, { 2, 2, 3 } };

    @Inject(method = "makeMultiblockSet", at = @At("HEAD"), cancellable = true, remap = false)
    private static void makeMultiblockSet(CallbackInfoReturnable<MultiblockSet> cir) {
        Multiblock mb = new Multiblock();

        for (int[] p : NPYLON_LOCATIONS) {
            mb.addComponent(p[0], p[1], p[2], ModBlocks.pylon, 0);
        }
        mb.addComponent(0, 0, 0, Blocks.obsidian, 0);
        mb.addComponent(0, 1, 0, Blocks.lapis_block, 0);

        cir.setReturnValue(mb.makeSet());
    }
}
