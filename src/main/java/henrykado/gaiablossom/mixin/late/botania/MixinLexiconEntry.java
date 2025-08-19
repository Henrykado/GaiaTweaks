package henrykado.gaiablossom.mixin.late.botania;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.api.lexicon.LexiconEntry;

@Mixin(value = LexiconEntry.class, remap = false)
public abstract class MixinLexiconEntry {

    @Shadow
    public abstract String getUnlocalizedName();

    @Inject(method = "getTagline", at = @At("HEAD"), cancellable = true)
    public void getTaglineInject(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("botania.tagline." + getUnlocalizedName());
    }
}
