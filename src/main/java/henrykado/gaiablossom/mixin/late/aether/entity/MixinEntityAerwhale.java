package henrykado.gaiablossom.mixin.late.aether.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.gildedgames.the_aether.entities.passive.EntityAerwhale;

@Mixin(EntityAerwhale.class)
public abstract class MixinEntityAerwhale extends Entity {

    public MixinEntityAerwhale(World worldIn) {
        super(worldIn);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void flyHigh(World world, CallbackInfo ci) {
        posY += 6 + world.rand.nextInt(14);
    }
}
