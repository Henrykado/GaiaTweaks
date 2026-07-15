package henrykado.gaiablossom.mixin.late.thaumcraft.foci;

import henrykado.gaiablossom.Config;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.foci.ItemFocusExcavation;

@Mixin(ItemFocusExcavation.class)
public class MixinFocusExcavation extends ItemFocusBasic {
    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public AspectList getVisCost(ItemStack itemstack) {
        AspectList cost = (new AspectList()).add(Aspect.EARTH, Config.excavationEarthVisCost);

        if (this.isUpgradedWith(itemstack, FocusUpgradeType.silktouch)) {
            AspectList cost2 = (new AspectList()).add(Aspect.AIR, 1).add(Aspect.FIRE, 1).add(Aspect.EARTH, 1).add(Aspect.WATER, 1).add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1);
            cost2.add(cost);

            return cost2;
        } else if (this.isUpgradedWith(itemstack, ItemFocusExcavation.dowsing)) {
            AspectList cost2 = (new AspectList()).add(Aspect.FIRE, 2).add(Aspect.ORDER, 2);
            cost2.add(cost);

            return cost2;
        } else {
            return cost;
        }
    }
}
