package henrykado.gaiablossom.asm.replacements;

import static baubles.api.expanded.BaubleExpandedSlots.*;
import static baubles.api.expanded.BaubleExpandedSlots.universalType;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gildedgames.the_aether.api.accessories.AccessoryType;
import com.gildedgames.the_aether.items.accessories.ItemAccessory;

import baubles.api.BaubleType;
import baubles.api.expanded.BaubleItemHelper;
import baubles.api.expanded.IBaubleExpanded;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BaubleItemAccessory extends ItemAccessory implements IBaubleExpanded {

    public BaubleItemAccessory(AccessoryType type) {
        super(type);
    }

    @Override
    public String[] getBaubleTypes(ItemStack itemstack) {
        AccessoryType accType = ((ItemAccessory) itemstack.getItem()).getType();

        String baubleType = switch (accType) {
            case PENDANT -> amuletType;
            case CAPE, SHIELD -> bodyType;
            case RING, GLOVES -> ringType;
            default -> universalType; // MISC
        };

        return new String[] { baubleType };
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return null;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {}

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStackIn, EntityPlayer player, List<String> tooltip, boolean p_77624_4_) {
        BaubleItemHelper.addSlotInformation(tooltip, getBaubleTypes(itemStackIn));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player) {
        return BaubleItemHelper.onBaubleRightClick(itemStackIn, worldIn, player);
    }
}
