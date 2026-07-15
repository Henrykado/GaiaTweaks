package henrykado.gaiablossom.mixin.late.thaumcraft.foci;

import henrykado.gaiablossom.Config;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.foci.ItemFocusFire;

@Mixin(ItemFocusFire.class)
public class MixinFocusFire extends ItemFocusBasic {
    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public AspectList getVisCost(ItemStack itemstack) {
        return this.isUpgradedWith(itemstack, ItemFocusFire.firebeam)
            ? (new AspectList()).add(Aspect.FIRE, Config.fireVisCost).add(Aspect.ORDER, 3)
            : (this.isUpgradedWith(itemstack, ItemFocusFire.fireball)
            ? (new AspectList()).add(Aspect.FIRE, Config.fireballVisCost).add(Aspect.ENTROPY, 33)
            : (new AspectList()).add(Aspect.FIRE, Config.fireVisCost));
    }
}
