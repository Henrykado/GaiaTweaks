package henrykado.gaiablossom.mixin.late;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import atomicstryker.battletowers.common.AS_WorldGenTower;

@Mixin(AS_WorldGenTower.class)
public class MixinWorldGenTower {

    @Redirect(
        method = "getChosenTowerOrdinal",
        at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 0),
        remap = false)
    public int nextIntRedirect(Random instance, int i) {
        return 1; // if statement checks if rand(10) == 0, this makes the result always one, removing nether towers
    }
}
