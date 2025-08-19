/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [22/10/2016, 11:51:06 (GMT)]
 */
package henrykado.gaiablossom.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import baubles.api.BaubleType;
import baubles.api.expanded.BaubleExpandedSlots;
import baubles.api.expanded.IBaubleExpanded;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.equipment.bauble.ItemBauble;

public class ItemInvisibilityCloak extends ItemBauble implements IManaUsingItem, IBaubleExpanded {

    public ItemInvisibilityCloak() {
        super("invisibility_cloak");
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return null;
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        PotionEffect effect = player.getActivePotionEffect(Potion.invisibility);
        if (effect != null && player instanceof EntityPlayer && effect.getAmplifier() == -42)
            player.removePotionEffect(Potion.invisibility.id);
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        super.onWornTick(stack, player);

        if (player instanceof EntityPlayer && !player.worldObj.isRemote) {
            int manaCost = 2;
            boolean hasMana = ManaItemHandler.requestManaExact(stack, (EntityPlayer) player, manaCost, false);
            if (!hasMana) onUnequipped(stack, player);
            else {
                if (player.getActivePotionEffect(Potion.invisibility) != null)
                    player.removePotionEffect(Potion.invisibility.id);

                player.addPotionEffect(new PotionEffect(Potion.invisibility.id, Integer.MAX_VALUE, -42, true));
            }

        }
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }

    @Override
    public String[] getBaubleTypes(ItemStack itemStack) {
        return new String[] { BaubleExpandedSlots.bodyType };
    }
}
