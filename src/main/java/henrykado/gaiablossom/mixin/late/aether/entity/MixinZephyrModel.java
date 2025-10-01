package henrykado.gaiablossom.mixin.late.aether.entity;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.gildedgames.the_aether.client.models.entities.ZephyrModel;

@Mixin(ZephyrModel.class)
public class MixinZephyrModel {

    @Shadow
    ModelRenderer Tail1;
    @Shadow
    ModelRenderer Tail2;
    @Shadow
    ModelRenderer Tail3;

    @Inject(method = "setRotationAngles", at = @At("TAIL"))
    public void setRotationAnglesInject(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
        float headPitch, float scaleFactor, Entity entityIn, CallbackInfo ci) {
        this.Tail1.rotateAngleY = (float) (Math.sin(ageInTicks * 0.1F) * limbSwingAmount * 0.75F);
        this.Tail2.rotateAngleY = this.Tail1.rotateAngleY + 0.25F;
        this.Tail3.rotateAngleY = this.Tail2.rotateAngleY + 0.35F;
    }
}
