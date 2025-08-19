package henrykado.gaiablossom.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import henrykado.gaiablossom.Config;
import henrykado.gaiablossom.event.eep.GaiaPlayer;
import squeek.applecore.api.AppleCoreAPI;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.hunger.ExhaustionEvent;
import squeek.applecore.api.hunger.HealthRegenEvent;
import squeek.applecore.api.hunger.StarvationEvent;

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
        return 200;//(int) (150 + ((20 - hunger) * 15));
    }

    @SubscribeEvent
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer player) {
            if (!event.entity.worldObj.isRemote && !player.isSprinting()) {
                GaiaPlayer playerProperties = GaiaPlayer.get(event.entity);
                playerProperties.staminaTimer--;
                if (playerProperties.staminaTimer == 0) {
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
        EntityPlayer player = event.player;
        int hunger = player.getFoodStats()
            .getFoodLevel();

        if (hunger < 20) {
            AppleCoreAPI.mutator.setHunger(event.player, Math.min(hunger + event.foodValuesToBeAdded.hunger, 20));
            AppleCoreAPI.mutator.setSaturation(
                player,
                Math.min(
                    player.getFoodStats()
                        .getSaturationLevel() + (float) hunger * event.foodValuesToBeAdded.saturationModifier * 2.0F,
                    (float) hunger));
        }

        event.player.heal((float) event.foodValuesToBeAdded.hunger * Config.healMultiplier);

        event.setCanceled(true);
    }

    @SubscribeEvent
    public void disableHungerHealthRegen(HealthRegenEvent.AllowRegen event) {
        event.setResult(Event.Result.DENY);
    }

    @SubscribeEvent
    public void disableStarvation(StarvationEvent.AllowStarvation event) {
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
