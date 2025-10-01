package henrykado.gaiablossom.mixin.early;

import net.minecraft.world.gen.feature.WorldGenForest;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(WorldGenForest.class)
public class MixinWorldGenForest {

    @ModifyConstant(method = "generate", constant = @Constant(intValue = 5))
    public int minBirchTreeHeight() {
        return 6;
    }

    @ModifyConstant(method = "generate", constant = @Constant(intValue = 3))
    public int varBirchTreeHeight() {
        return 5;
    }
}
