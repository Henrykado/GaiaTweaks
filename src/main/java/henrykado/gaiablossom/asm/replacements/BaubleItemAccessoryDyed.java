package henrykado.gaiablossom.asm.replacements;

import static baubles.api.expanded.BaubleExpandedSlots.*;
import static baubles.api.expanded.BaubleExpandedSlots.universalType;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import com.gildedgames.the_aether.api.accessories.AccessoryType;
import com.gildedgames.the_aether.items.accessories.ItemAccessoryDyed;

import baubles.api.BaubleType;
import baubles.api.expanded.IBaubleExpanded;

public class BaubleItemAccessoryDyed extends ItemAccessoryDyed implements IBaubleExpanded {

    public BaubleItemAccessoryDyed(AccessoryType type) {
        super(type);
    }

    @Override
    public String[] getBaubleTypes(ItemStack itemstack) {
        AccessoryType accType = ((ItemAccessoryDyed) itemstack.getItem()).getType();

        String baubleType = switch (accType) {
            case PENDANT -> amuletType;
            case CAPE, SHIELD -> bodyType;
            case RING, GLOVES -> ringType;
            default -> universalType; // MISC
        };

        return new String[] { baubleType };
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return null;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return false;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return false;
    }
}
