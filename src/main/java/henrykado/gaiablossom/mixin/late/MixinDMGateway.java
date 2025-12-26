package henrykado.gaiablossom.mixin.late;

import net.minecraft.world.World;

import org.dimdev.dimdoors.world.gateways.BaseGateway;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseGateway.class)
public abstract class MixinDMGateway {

    @Inject(method = "isLocationValid", at = @At("HEAD"), cancellable = true, remap = false)
    public void isLocationValidInject(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        if (world.provider.dimensionId != 0) {
            cir.setReturnValue(false);
        }
    }
}
