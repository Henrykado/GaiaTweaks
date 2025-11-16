package henrykado.gaiablossom.mixin.late.primitive;

import net.daveyx0.primitivemobs.client.PrimitiveMobsAchievementPage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PrimitiveMobsAchievementPage.class)
public class MixinPMAchievements {

    @Inject(at = @At("HEAD"), method = "<clinit>", cancellable = true)
    private static void staticInject(CallbackInfo ci) {
        ci.cancel();
    }
}
