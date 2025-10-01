package henrykado.gaiablossom.mixin.early.entity;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import henrykado.gaiablossom.common.entity.eep.SkeletonProperties;

@Mixin(EntitySkeleton.class)
public abstract class MixinEntitySkeleton extends EntityMob {

    public MixinEntitySkeleton(World p_i1741_1_) {
        super(p_i1741_1_);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getItemIcon(ItemStack itemStackIn, int p_70620_2_) {
        SkeletonProperties data = SkeletonProperties.get(this);
        if (data != null && this.getEquipmentInSlot(0) != null && itemStackIn.getItem() == Items.bow) {
            int attackTime = 60 - data.rangedAttackTime;

            if (attackTime >= 57) {
                return Items.bow.getItemIconForUseDuration(2);
            } else if (attackTime >= 52) {
                return Items.bow.getItemIconForUseDuration(1);
            } else if (attackTime >= 44) {
                return Items.bow.getItemIconForUseDuration(0);
            }
        }
        return super.getItemIcon(itemStackIn, p_70620_2_);
    }
}
