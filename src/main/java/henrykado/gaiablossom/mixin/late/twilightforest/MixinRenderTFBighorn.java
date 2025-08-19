package henrykado.gaiablossom.mixin.late.twilightforest;

import org.spongepowered.asm.mixin.Mixin;

import twilightforest.client.renderer.entity.RenderTFBighorn;

@Mixin(RenderTFBighorn.class)
public class MixinRenderTFBighorn {
    /*
     * @Inject(method = "getEntityTexture", at = @At("RETURN"), cancellable = true)
     * public void getEntityTextureInject(Entity par1Entity, CallbackInfoReturnable<ResourceLocation> cir) {
     * cir.setReturnValue(new ResourceLocation("gaiablossom:textures/bighorn.png"));
     * }
     */
}
