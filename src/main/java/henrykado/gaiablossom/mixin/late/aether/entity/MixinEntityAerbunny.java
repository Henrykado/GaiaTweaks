package henrykado.gaiablossom.mixin.late.aether.entity;

import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.entities.passive.EntityAetherAnimal;
import com.gildedgames.the_aether.entities.passive.mountable.EntityAerbunny;

@Mixin(EntityAerbunny.class)
public abstract class MixinEntityAerbunny extends EntityAetherAnimal {

    public MixinEntityAerbunny(World worldIn) {
        super(worldIn);
    }

    @Redirect(
        method = "onLivingUpdate",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnParticle(Ljava/lang/String;DDDDDD)V"))
    public void spawnSmokeParticleRedirect(World instance, String s, double x, double y, double z, double vx, double vy,
        double vz) {
        return;
    }

    @Shadow
    public abstract void spawnExplosionParticle();

    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    public void onLivingUpdateInject(CallbackInfo ci) {
        if (this.dimension != AetherConfig.getAetherDimensionID()) {
            this.spawnExplosionParticle();
            this.setDead();
        }
    }
}
