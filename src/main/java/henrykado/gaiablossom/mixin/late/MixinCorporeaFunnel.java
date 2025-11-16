package henrykado.gaiablossom.mixin.late;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import vazkii.botania.common.block.tile.corporea.TileCorporeaFunnel;

@Mixin(TileCorporeaFunnel.class)
public class MixinCorporeaFunnel {

    @ModifyVariable(method = "getFilter", remap = false, at = @At("STORE"), ordinal = 1)
    private int[] rotationToStackSizeModify(int[] var) {
        return new int[] { 1, 2, 4, 8, 16, 32, 48, 64 };
    }
}
