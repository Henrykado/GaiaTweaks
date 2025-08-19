package henrykado.gaiablossom.mixin.late.aether.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.gildedgames.the_aether.entities.bosses.EntityValkyrie;

@Mixin(EntityValkyrie.class)
public class MixinEntityValkyrie {

    @Inject(method = "chatItUp", at = @At("HEAD"), cancellable = true, remap = false)
    private void chattingRemover(CallbackInfo ci) {
        ci.cancel();
    }
}
