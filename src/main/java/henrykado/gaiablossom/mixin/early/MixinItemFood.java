package henrykado.gaiablossom.mixin.early;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import henrykado.gaiablossom.Config;

@Mixin(ItemFood.class)
public class MixinItemFood {

    @Inject(method = "getMaxItemUseDuration(Lnet/minecraft/item/ItemStack;)I", at = @At("HEAD"), cancellable = true)
    public void getMaxItemUseDurationInject(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        for (String item : Config.fastFood) {
            if (stack.getUnlocalizedName()
                .equals("item." + item)) {
                cir.setReturnValue(16);
            }
        }
    }
}
