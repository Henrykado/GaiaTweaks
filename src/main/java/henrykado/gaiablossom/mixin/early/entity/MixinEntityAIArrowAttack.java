package henrykado.gaiablossom.mixin.early.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntitySkeleton;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cpw.mods.fml.common.network.NetworkRegistry;
import henrykado.gaiablossom.network.GaiaPacketHandler;
import henrykado.gaiablossom.network.SkeletonPacket;

@Mixin(EntityAIArrowAttack.class)
public abstract class MixinEntityAIArrowAttack extends EntityAIBase {

    @Shadow
    public EntityLiving entityHost;

    @Shadow
    public int rangedAttackTime;

    @Shadow
    private EntityLivingBase attackTarget;

    @Inject(method = "updateTask", at = @At("TAIL"))
    public void updateTaskInject(CallbackInfo ci) {
        boolean flag = this.entityHost.getEntitySenses()
            .canSee(this.attackTarget);
        blossom_of_Gaia$sendPacketToAllAround(flag, 32);
    }

    @Inject(method = "resetTask", at = @At("HEAD"))
    public void resetTaskInject(CallbackInfo cir) {
        blossom_of_Gaia$sendPacketToAllAround(false, 32);
    }

    @Unique
    public void blossom_of_Gaia$sendPacketToAllAround(boolean isAttacking, int range) {
        if (entityHost instanceof EntitySkeleton) {
            GaiaPacketHandler.INSTANCE.sendToAllAround(
                new SkeletonPacket(entityHost.getEntityId(), rangedAttackTime, isAttacking),
                new NetworkRegistry.TargetPoint(
                    entityHost.dimension,
                    entityHost.posX,
                    entityHost.posY,
                    entityHost.posZ,
                    range));
        }
    }
}
