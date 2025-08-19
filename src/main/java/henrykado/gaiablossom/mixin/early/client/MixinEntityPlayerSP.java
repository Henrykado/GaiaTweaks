package henrykado.gaiablossom.mixin.early.client;

import net.minecraft.client.entity.EntityPlayerSP;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

    @ModifyConstant(method = "onLivingUpdate", constant = @Constant(floatValue = 6.0F))
    public float hungerNeededToSprint(float constant) {
        return 0.0f;
    }
}
