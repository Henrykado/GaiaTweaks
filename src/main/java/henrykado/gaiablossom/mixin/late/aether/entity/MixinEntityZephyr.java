package henrykado.gaiablossom.mixin.late.aether.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.gildedgames.the_aether.entities.hostile.EntityZephyr;

@Mixin(EntityZephyr.class)
public abstract class MixinEntityZephyr extends EntityLiving {

    public MixinEntityZephyr(World p_i1594_1_) {
        super(p_i1594_1_);
    }

    @Inject(method = "updateEntityActionState", at = @At("TAIL"))
    protected void updateEntityActionStateInject(CallbackInfo ci) {
        if (this.getAttackTarget() == null) {
            this.rotationYaw = (float) (-Math.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
            this.renderYawOffset = this.rotationYaw;
        } else if (this.getAttackTarget()
            .getDistanceSqToEntity(this) < 4096.0 && this.canEntityBeSeen(this.getAttackTarget())) {
                double x = this.getAttackTarget().posX - this.posX;
                double z = this.getAttackTarget().posZ - this.posZ;
                this.rotationYaw = (float) (-Math.atan2(x, z) * (180D / Math.PI));
                this.renderYawOffset = this.rotationYaw;
            }
    }
}
