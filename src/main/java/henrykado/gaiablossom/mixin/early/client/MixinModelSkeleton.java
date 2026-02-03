package henrykado.gaiablossom.mixin.early.client;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.entity.EntityLivingBase;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import henrykado.gaiablossom.common.entity.eep.SkeletonProperties;

@Mixin(ModelSkeleton.class)
public class MixinModelSkeleton extends ModelBiped {

    @Inject(method = "setLivingAnimations", at = @At("TAIL"))
    public void setLivingAnimationsInject(EntityLivingBase entityLivingBase, float p_78086_2_, float p_78086_3_,
        float p_78086_4_, CallbackInfo ci) {
        SkeletonProperties data = SkeletonProperties.get(entityLivingBase);
        if (data != null) {
            aimedBow = data.isAttacking;
        }
    }
}
