package henrykado.gaiablossom.mixin.late.thaumcraft.foci;

import henrykado.gaiablossom.Config;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.foci.ItemFocusShock;

@Mixin(ItemFocusShock.class)
public class MixinFocusShock extends ItemFocusBasic {
    /**
     * @author
     * @reason
     */
    @Inject(method = "getVisCost", at = @At(value = "HEAD"), cancellable = true)
    public void getVisCostInject(ItemStack itemstack, CallbackInfoReturnable<AspectList> cir) {
        cir.setReturnValue(isUpgradedWith(itemstack, ItemFocusShock.chainlightning)
            ? (new AspectList()).add(Aspect.AIR, Config.chainShockAirVisCost).add(Aspect.WATER, 10) : (this.isUpgradedWith(itemstack, ItemFocusShock.earthshock)
            ? (new AspectList()).add(Aspect.AIR, Config.earthShockAirVisCost).add(Aspect.EARTH, 25) : (new AspectList()).add(Aspect.AIR, Config.shockAirVisCost)));
    }
}
