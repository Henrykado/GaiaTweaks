package henrykado.gaiablossom.mixin.late.thaumcraft.foci;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import henrykado.gaiablossom.Config;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.foci.ItemFocusFire;

@Mixin(ItemFocusFire.class)
public class MixinFocusFire extends ItemFocusBasic {

    @Inject(method = "getVisCost", at = @At("HEAD"), remap = false, cancellable = true)
    public void getVisCostInject(ItemStack itemstack, CallbackInfoReturnable<AspectList> cir) {
        if (this.isUpgradedWith(itemstack, ItemFocusFire.firebeam) && Config.fireVisCost != 10) {
            cir.setReturnValue(
                (new AspectList()).add(Aspect.FIRE, Config.fireVisCost)
                    .add(Aspect.ORDER, 3));
        } else if (this.isUpgradedWith(itemstack, ItemFocusFire.fireball) && Config.fireballVisCost != 66) {
            cir.setReturnValue(
                (new AspectList()).add(Aspect.FIRE, Config.fireballVisCost)
                    .add(Aspect.ENTROPY, 33));
        } else if (Config.fireVisCost != 10) {
            cir.setReturnValue((new AspectList()).add(Aspect.FIRE, Config.fireVisCost));
        }
    }
}
