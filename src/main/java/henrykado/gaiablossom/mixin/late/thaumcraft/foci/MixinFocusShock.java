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
import thaumcraft.common.items.wands.foci.ItemFocusShock;

@Mixin(ItemFocusShock.class)
public class MixinFocusShock extends ItemFocusBasic {

    @Inject(method = "getVisCost", at = @At(value = "HEAD"), cancellable = true, remap = false)
    public void getVisCostInject(ItemStack itemstack, CallbackInfoReturnable<AspectList> cir) {
        if (isUpgradedWith(itemstack, ItemFocusShock.chainlightning) && Config.chainShockAirVisCost != 40) {
            cir.setReturnValue(
                (new AspectList()).add(Aspect.AIR, Config.chainShockAirVisCost)
                    .add(Aspect.WATER, 10));
        } else if (isUpgradedWith(itemstack, ItemFocusShock.earthshock) && Config.earthShockAirVisCost != 75) {
            cir.setReturnValue(
                (new AspectList()).add(Aspect.AIR, Config.earthShockAirVisCost)
                    .add(Aspect.EARTH, 25));
        } else if (Config.shockAirVisCost != 15) {
            cir.setReturnValue((new AspectList()).add(Aspect.AIR, Config.shockAirVisCost));
        }
    }
}
