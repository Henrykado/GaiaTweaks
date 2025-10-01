package henrykado.gaiablossom.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import henrykado.gaiablossom.Config;
import henrykado.gaiablossom.common.entity.eep.GaiaPlayer;
import squeek.applecore.api.AppleCoreAPI;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.hunger.ExhaustionEvent;
import squeek.applecore.api.hunger.HealthRegenEvent;
import squeek.applecore.api.hunger.StarvationEvent;
import thaumcraft.common.lib.potions.PotionUnnaturalHunger;

public class PlayerEventHandler {

    @SubscribeEvent
    public void onPlayerAttacked(LivingAttackEvent event) {
        if (!(event.entity instanceof EntityPlayer player) || !((EntityPlayer) event.entity).isBlocking()
            || event.source.isUnblockable()) return;

        if (player.getItemInUseDuration() <= 3) {
            Entity damagingEntity = event.source.getSourceOfDamage();

            if (damagingEntity != null) {
                if (event.source.isProjectile()) {
                    damagingEntity.motionX = -damagingEntity.motionX / 1.5;
                    damagingEntity.motionY = -damagingEntity.motionY / 1.5;
                    damagingEntity.motionZ = -damagingEntity.motionZ / 1.5;

                    if (damagingEntity instanceof EntityArrow arrow) arrow.shootingEntity = player;
                } else {
                    damagingEntity.motionX = -damagingEntity.motionX * 5;
                    damagingEntity.motionY = 0.42;
                    damagingEntity.motionZ = -damagingEntity.motionZ * 5;
                }
            }

            event.entity.worldObj.playSoundAtEntity(
                event.entity,
                "gaiablossom:parry",
                1.0f,
                Math.max(1.4f - (event.ammount / 10.0f), 0.4f));
            event.setCanceled(true);
        }
    }

    int resetStaminaTimer(int hunger) {
        return 200;// (int) (150 + ((20 - hunger) * 15));
    }

    @SubscribeEvent
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer player) {
            if (player.isOnLadder() && !player.isSneaking()) {
                /*
                 * if (player.rotationPitch > 0 && player.moveForward == 0) { // looking down
                 * player.moveEntity(0, (float) Math.abs(player.rotationPitch / 90.0) * (climbSpeedModifier / 10f), 0);
                 * } else
                 */ if (player.rotationPitch < 0 && player.moveForward > 0) { // looking up
                    player.moveEntity(0, (float) Math.abs(player.rotationPitch / 90.0) * 0.14, 0);
                }
            }

            if (!event.entity.worldObj.isRemote && !player.isSprinting()) {
                GaiaPlayer playerProperties = GaiaPlayer.get(event.entity);
                if (playerProperties != null && playerProperties.staminaTimer-- == 0) {
                    int hunger = player.getFoodStats()
                        .getFoodLevel();
                    playerProperties.staminaTimer = resetStaminaTimer(hunger);
                    AppleCoreAPI.mutator.setHunger(player, Math.min(hunger + 1, 20));
                }
            }
        }
    }

    @SubscribeEvent
    public void onExhausted(ExhaustionEvent.Exhausted event) {
        if (!event.player.worldObj.isRemote) {
            GaiaPlayer playerProperties = GaiaPlayer.get(event.player);
            playerProperties.staminaTimer = resetStaminaTimer(
                event.player.getFoodStats()
                    .getFoodLevel());
        }
    }

    @SubscribeEvent
    public void foodHealing(FoodEvent.FoodStatsAddition event) {
        Item foodItem = event.player.itemInUse == null ? null : event.player.itemInUse.getItem();

        for (String s : Config.foodBuffs) {
            String[] split = s.split(":");
            String itemName = split[0];
            int effectID = Integer.parseInt(split[1]);
            int duration = Integer.parseInt(split[2]);

            if (foodItem != null && foodItem.equals(Item.itemRegistry.getObject(itemName))) {
                event.player.addPotionEffect(new PotionEffect(effectID, duration * 20, 0, true));
            }
        }

        for (String s : Config.foodHealValues) {
            String[] split = s.split(":");
            String itemName = split[0];
            int healAmount = Integer.parseInt(split[1]);

            if (foodItem != null && foodItem.equals(Item.itemRegistry.getObject(itemName))) {
                event.player.heal((float) healAmount);
                return;
            }
        }

        event.player.heal((float) event.foodValuesToBeAdded.hunger * Config.healMultiplier);
    }

    @SubscribeEvent
    public void disableHungerHealthRegen(HealthRegenEvent.AllowRegen event) {
        event.setResult(Event.Result.DENY);
    }

    @SubscribeEvent
    public void disableStarvation(StarvationEvent.AllowStarvation event) {
        if (event.player.isPotionActive(PotionUnnaturalHunger.instance)) {
            event.setResult(Event.Result.DEFAULT);
            return;
        }
        event.setResult(Event.Result.DENY);
    }

    @SubscribeEvent
    public void customWaterColor(BiomeEvent.GetWaterColor event) {
        if (event.biome == BiomeGenBase.swampland) {
            event.newColor = Config.swamplandWaterColorOverride;
        }
    }

    @SubscribeEvent
    public void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        GaiaPlayer.recalculateHealth(event.player, GaiaPlayer.get(event.player));
    }

    // Wood tools
    /*
     * @SubscribeEvent
     * public void playerBreakSpeed(PlayerEvent.BreakSpeed event) {
     * event.newSpeed = event.originalSpeed + 1.0F - (event.entityPlayer.getHeldItem().getItemDamage() / 25.0F);
     * }
     */
}
