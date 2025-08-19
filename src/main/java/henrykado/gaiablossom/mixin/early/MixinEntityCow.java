package henrykado.gaiablossom.mixin.early;

import net.minecraft.entity.passive.EntityCow;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntityCow.class)
public class MixinEntityCow {

    @ModifyConstant(method = "dropFewItems(ZI)V", constant = @Constant(intValue = 3, ordinal = 1))
    private int dropFewItemsDropChance(int value) {
        return 1;
    }
}
