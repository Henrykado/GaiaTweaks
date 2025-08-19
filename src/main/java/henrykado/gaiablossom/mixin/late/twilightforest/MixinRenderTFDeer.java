package henrykado.gaiablossom.mixin.late.twilightforest;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import twilightforest.client.renderer.entity.RenderTFDeer;

@Mixin(RenderTFDeer.class)
public class MixinRenderTFDeer {

    @Inject(method = "getEntityTexture", at = @At("RETURN"), cancellable = true)
    public void getEntityTextureInject(Entity par1Entity, CallbackInfoReturnable<ResourceLocation> cir) {
        cir.setReturnValue(new ResourceLocation("gaiablossom:textures/wilddeer.png"));
    }
}
