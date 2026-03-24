package henrykado.gaiablossom.mixin.late.dimdoors;

import org.dimdev.dimdoors.config.DimensionFilter;
import org.dimdev.dimdoors.world.gateways.GatewayGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.gildedgames.the_aether.AetherConfig;

import twilightforest.TwilightForestMod;

@Mixin(GatewayGenerator.class)
public abstract class MixinDMGateway {

    @Redirect(
        method = "generate",
        at = @At(value = "INVOKE", target = "Lorg/dimdev/dimdoors/config/DimensionFilter;isAccepted(I)Z"),
        remap = false)
    public boolean dimensionBlacklistInject(DimensionFilter instance, int dimensionID) {
        if (dimensionID == AetherConfig.getAetherDimensionID() || dimensionID == TwilightForestMod.dimensionID) {
            return false;
        }
        return instance.isAccepted(dimensionID);
    }
}
