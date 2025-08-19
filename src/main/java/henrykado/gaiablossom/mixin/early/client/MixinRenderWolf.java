package henrykado.gaiablossom.mixin.early.client;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.renderer.entity.RenderWolf.class)
public class MixinRenderWolf {

    @Unique
    private static final ResourceLocation blossomOfGaia$alfredoWolfTextures = new ResourceLocation(
        "gaiablossom",
        "textures/alfredo.png");

    @Inject(
        method = "getEntityTexture(Lnet/minecraft/entity/passive/EntityWolf;)Lnet/minecraft/util/ResourceLocation;",
        at = @At(value = "RETURN"),
        cancellable = true)
    private void getEntityTextureInject(EntityWolf wolf, CallbackInfoReturnable<ResourceLocation> cir) {
        if (wolf.isTamed() && EnumChatFormatting.getTextWithoutFormattingCodes(wolf.getCommandSenderName())
            .equals("Alfredo")) {
            cir.setReturnValue(blossomOfGaia$alfredoWolfTextures);
        }
        cir.setReturnValue(cir.getReturnValue());
    }
}
