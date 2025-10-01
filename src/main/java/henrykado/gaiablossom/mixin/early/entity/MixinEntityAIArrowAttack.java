package henrykado.gaiablossom.mixin.early.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.monster.EntitySkeleton;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cpw.mods.fml.common.network.NetworkRegistry;
import henrykado.gaiablossom.network.GaiaPacketHandler;
import henrykado.gaiablossom.network.SkeletonPacket;

@Mixin(EntityAIArrowAttack.class)
public abstract class MixinEntityAIArrowAttack {

    @Shadow
    public EntityLiving entityHost;

    @Shadow
    public int rangedAttackTime;

    @Inject(method = "updateTask", at = @At("TAIL"))
    public void updateTaskInject(CallbackInfo ci) {
        if (entityHost instanceof EntitySkeleton) {
            GaiaPacketHandler.INSTANCE.sendToAllAround(
                new SkeletonPacket(entityHost.getEntityId(), rangedAttackTime),
                new NetworkRegistry.TargetPoint(
                    entityHost.dimension,
                    entityHost.posX,
                    entityHost.posY,
                    entityHost.posZ,
                    32));
        }
    }
}
