package henrykado.gaiablossom.mixin.early.itemframe;

import net.minecraft.entity.item.EntityItemFrame;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntityItemFrame.class)
public abstract class MixinEntityItemFrame {
    /*
     * @Shadow
     * public abstract ItemStack getDisplayedItem();
     * @Inject(method = "interactFirst", at = @At("HEAD"), cancellable = true)
     * public void getHeldItemRedirect(EntityPlayer player, CallbackInfoReturnable<Boolean> cir) {
     * if (getDisplayedItem() == null && (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof
     * ItemThaumometer && player.isSneaking())) {
     * cir.setReturnValue(false);
     * }
     * else if (getDisplayedItem() != null && !player.isSneaking()) {
     * cir.setReturnValue(false);
     * }
     * }
     */

    @ModifyConstant(method = "setItemRotation", constant = @Constant(intValue = 4))
    public int setItemRotationModulo(int constant) {
        return 8;
    }
}
