package henrykado.gaiablossom.mixin.early;

import net.minecraft.entity.ai.EntityAITempt;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityAITempt.class)
public class MixinEntityAITempt {

    @Shadow
    private int delayTemptCounter;

    @Inject(method = "resetTask", at = @At(value = "TAIL"))
    public void resetTaskInject(CallbackInfo ci) {
        delayTemptCounter = 30;
    }
}
