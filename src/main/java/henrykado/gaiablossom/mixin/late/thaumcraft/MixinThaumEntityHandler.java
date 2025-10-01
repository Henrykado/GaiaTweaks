package henrykado.gaiablossom.mixin.late.thaumcraft;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.projectile.EntityPrimalArrow;
import thaumcraft.common.lib.events.EventHandlerEntity;
import thaumcraft.common.lib.utils.InventoryUtils;
import twilightforest.item.ItemTFTripleBow;

@Mixin(EventHandlerEntity.class)
public class MixinThaumEntityHandler {

    @Inject(method = "bowShot", at = @At("HEAD"), cancellable = true, remap = false)
    public void triBowCompat(ArrowLooseEvent event, CallbackInfo ci) {
        if (event.bow.getItem() instanceof ItemTFTripleBow
            && event.entityPlayer.inventory.hasItem(ConfigItems.itemPrimalArrow)) {
            float f = (float) event.charge / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if ((double) f < 0.1D) {
                return;
            }

            if (f > 1.0F) {
                f = 1.0F;
            }

            int type = 0;

            for (int j = 0; j < event.entityPlayer.inventory.mainInventory.length; ++j) {
                if (event.entityPlayer.inventory.mainInventory[j] != null
                    && event.entityPlayer.inventory.mainInventory[j].getItem() == ConfigItems.itemPrimalArrow) {
                    type = event.entityPlayer.inventory.mainInventory[j].getItemDamage();
                    break;
                }
            }

            EntityPrimalArrow entityarrow = new EntityPrimalArrow(
                event.entity.worldObj,
                event.entityPlayer,
                f * 2.0F,
                type);
            // other arrows with slight deviation
            EntityPrimalArrow entityarrow1 = new EntityPrimalArrow(
                event.entity.worldObj,
                event.entityPlayer,
                f * 2.0F,
                type);
            entityarrow1.motionY += 0.007499999832361937D * 20F;
            entityarrow1.posY += 0.025F;
            EntityPrimalArrow entityarrow2 = new EntityPrimalArrow(
                event.entity.worldObj,
                event.entityPlayer,
                f * 2.0F,
                type);
            entityarrow2.motionY -= 0.007499999832361937D * 20F;
            entityarrow2.posY -= 0.025F;

            if (f == 1.0F) {
                entityarrow.setIsCritical(true);
                entityarrow1.setIsCritical(true);
                entityarrow2.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, event.bow);

            if (k > 0) {
                entityarrow.setDamage(entityarrow.getDamage() + (double) k * 0.5D + 0.5D);
                entityarrow1.setDamage(entityarrow.getDamage() + (double) k * 0.5D + 0.5D);
                entityarrow2.setDamage(entityarrow.getDamage() + (double) k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, event.bow);
            if (type == 3) {
                ++l;
            }

            if (l > 0) {
                entityarrow.setKnockbackStrength(l);
                entityarrow1.setKnockbackStrength(l);
                entityarrow2.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, event.bow) > 0) {
                entityarrow.setFire(100);
                entityarrow1.setFire(100);
                entityarrow2.setFire(100);
            }

            event.bow.damageItem(1, event.entityPlayer);
            event.entity.worldObj.playSoundAtEntity(
                event.entityPlayer,
                "random.bow",
                1.0F,
                1.0F / (event.entity.worldObj.rand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            boolean flag = false;
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, event.bow) > 0
                && event.entityPlayer.worldObj.rand.nextFloat() < 0.33F) {
                flag = true;
            }

            if (!event.entityPlayer.capabilities.isCreativeMode || !flag) {
                InventoryUtils.consumeInventoryItem(event.entityPlayer, ConfigItems.itemPrimalArrow, type);
            }

            entityarrow1.canBePickedUp = 2;
            entityarrow2.canBePickedUp = 2;

            if (!event.entity.worldObj.isRemote) {
                event.entity.worldObj.spawnEntityInWorld(entityarrow);
                event.entity.worldObj.spawnEntityInWorld(entityarrow1);
                event.entity.worldObj.spawnEntityInWorld(entityarrow2);
            }

            event.setCanceled(true);
            ci.cancel();
        }
    }
}
