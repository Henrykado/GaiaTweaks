package henrykado.gaiablossom.mixin.early;

import net.minecraft.world.biome.BiomeGenBase;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import henrykado.gaiablossom.Config;

@Mixin(BiomeGenBase.class)
public class MixinBiomeGenBase {

    @ModifyConstant(method = "<init>(IZ)V", constant = @Constant(intValue = 10, ordinal = 2))
    public int endermanSpawnWeight(int original) {
        return Config.endermanSpawnWeight;
    }

    @ModifyConstant(method = "<init>(IZ)V", constant = @Constant(intValue = 5))
    public int witchSpawnWeight(int original) {
        return Config.swampOnlyWitches ? 0 : original;
    }
}
