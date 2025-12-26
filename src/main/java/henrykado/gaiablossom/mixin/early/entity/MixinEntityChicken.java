package henrykado.gaiablossom.mixin.early.entity;

import net.minecraft.entity.passive.EntityChicken;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntityChicken.class)
public class MixinEntityChicken {

    @ModifyConstant(method = "onLivingUpdate", constant = @Constant(intValue = 6000, ordinal = 1))
    public int changeBaseEggDropRate(int constant) {
        return constant * 6;
    }

    @ModifyConstant(method = "dropFewItems(ZI)V", constant = @Constant(intValue = 3))
    private int dropFewItemsDropChance(int value) {
        return 2;
    }
}
