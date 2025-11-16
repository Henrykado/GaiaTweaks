package henrykado.gaiablossom.mixin.early;

import net.minecraft.world.WorldProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import henrykado.gaiablossom.Config;

@Mixin(WorldProvider.class)
public class MixinWorldProvider {

    @ModifyConstant(method = "getMovementFactor()D", constant = @Constant(doubleValue = 8.0), remap = false)
    public double getMovementFactorInject(double constant) {
        return Config.netherMovementFactor.getDouble();
    }
}
