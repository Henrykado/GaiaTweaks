package henrykado.gaiablossom.mixin.late.aether;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.gildedgames.the_aether.tileentity.TileEntityEnchanter;

@Mixin(TileEntityEnchanter.class)
public class MixinTileEntityEnchanter {

    @Inject(method = "updateEntity", at = @At("TAIL"))
    public void enchantArmor(CallbackInfo ci) {

    }
}
