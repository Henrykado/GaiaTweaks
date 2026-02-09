package henrykado.gaiablossom.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import henrykado.gaiablossom.Config;

public class PlayerEventHandler {

    @SubscribeEvent
    public void onPlayerAttacked(LivingAttackEvent event) {
        if (!(event.entity instanceof EntityPlayer player) || !player.isBlocking()
            || event.source.isUnblockable()
            || !Config.enableSwordParry) return;

        if ((player.itemInUse.getMaxItemUseDuration() - player.itemInUseCount) <= 3) {
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

    @SubscribeEvent
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer player) {
            if (Config.enableFasterLadders && player.isOnLadder() && !player.isSneaking()) {
                /*
                 * if (player.rotationPitch > 0 && player.moveForward == 0) { // looking down
                 * player.moveEntity(0, (float) Math.abs(player.rotationPitch / 90.0) * (climbSpeedModifier / 10f), 0);
                 * } else
                 */ if (player.rotationPitch < 0 && player.moveForward > 0) { // looking up
                    player.moveEntity(0, (float) Math.abs(player.rotationPitch / 90.0) * 0.14, 0);
                }
            }
        }
    }

    /*
     * @SubscribeEvent
     * public void customWaterColor(BiomeEvent.GetWaterColor event) {
     * if (event.biome == BiomeGenBase.swampland) {
     * //event.newColor = Config.swamplandWaterColorOverride;
     * }
     * }
     */

    /*
     * @SubscribeEvent
     * public void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
     * GaiaPlayer.recalculateHealth(event.player, GaiaPlayer.get(event.player));
     * }
     */

    // Wood tools
    /*
     * @SubscribeEvent
     * public void playerBreakSpeed(PlayerEvent.BreakSpeed event) {
     * event.newSpeed = event.originalSpeed + 1.0F - (event.entityPlayer.getHeldItem().getItemDamage() / 25.0F);
     * }
     */
}
