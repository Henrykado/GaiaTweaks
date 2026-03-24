package henrykado.gaiablossom.common.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import henrykado.gaiablossom.Config;
import henrykado.gaiablossom.common.entity.eep.GaiaPlayer;
import squeek.applecore.api.AppleCoreAPI;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.hunger.ExhaustionEvent;
import squeek.applecore.api.hunger.HealthRegenEvent;
import squeek.applecore.api.hunger.StarvationEvent;
import thaumcraft.common.lib.potions.PotionUnnaturalHunger;

public class AppleCoreEventHandler {

    @SubscribeEvent
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (Config.enableStaminaSystem && event.entity instanceof EntityPlayer player) {
            if (!event.entity.worldObj.isRemote && !player.isSprinting()) {
                GaiaPlayer playerProperties = GaiaPlayer.get(event.entity);
                if (playerProperties != null && playerProperties.staminaTimer-- == 0) {
                    int hunger = player.getFoodStats()
                        .getFoodLevel();
                    playerProperties.staminaTimer = Config.staminaTimer;
                    AppleCoreAPI.mutator.setHunger(player, Math.min(hunger + 1, 20));
                    AppleCoreAPI.mutator.setExhaustion(player, 0);
                }
            }
        }
    }

    @SubscribeEvent
    public void onExhausted(ExhaustionEvent.Exhausted event) {
        if (!event.player.worldObj.isRemote && Config.enableStaminaSystem) {
            GaiaPlayer playerProperties = GaiaPlayer.get(event.player);
            playerProperties.staminaTimer = Config.staminaTimer;
        }
    }

    @SubscribeEvent
    @Optional.Method(modid = "AppleCore")
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

        if (!Config.enableStaminaSystem) return;

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

        if (!event.player.getFoodStats()
            .needFood()) event.setCanceled(true);
    }

    @SubscribeEvent
    public void disableHungerHealthRegen(HealthRegenEvent.AllowRegen event) {
        event.setResult(Config.enableStaminaSystem ? Event.Result.DENY : Event.Result.DEFAULT);
    }

    @SubscribeEvent
    public void disableStarvation(StarvationEvent.AllowStarvation event) {
        event.setResult(Config.enableStaminaSystem ? Event.Result.DENY : Event.Result.DEFAULT);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @Optional.Method(modid = "Thaumcraft")
    public void disableStarvationTC(StarvationEvent.AllowStarvation event) {
        if (event.player.isPotionActive(PotionUnnaturalHunger.instance)) {
            event.setResult(Event.Result.DEFAULT);
        } else {
            event.setResult(Config.enableStaminaSystem ? Event.Result.DENY : Event.Result.DEFAULT);
        }
    }
}
