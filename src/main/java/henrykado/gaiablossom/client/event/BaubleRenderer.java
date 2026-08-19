package henrykado.gaiablossom.client.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.opengl.GL11;

import baubles.api.expanded.BaubleExpandedSlots;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import henrykado.gaiablossom.asm.replacements.IBaubleRendering;

// code adapted from Botania
public class BaubleRenderer {

    @SubscribeEvent
    public void onPlayerRender(RenderPlayerEvent.Specials.Post event) {
        if (event.entityLiving.getActivePotionEffect(Potion.invisibility) != null) return;

        EntityPlayer player = event.entityPlayer;
        InventoryBaubles inv = PlayerHandler.getPlayerBaubles(player);

        float yaw = player.prevRotationYawHead
            + (player.rotationYawHead - player.prevRotationYawHead) * event.partialRenderTick;
        float yawOffset = player.prevRenderYawOffset
            + (player.renderYawOffset - player.prevRenderYawOffset) * event.partialRenderTick;
        float pitch = player.prevRotationPitch
            + (player.rotationPitch - player.prevRotationPitch) * event.partialRenderTick;

        // dispatchRenders(inv, event, RenderType.HEAD);
        for (int slot : BaubleExpandedSlots.getIndexesOfAssignedSlotsOfType(BaubleExpandedSlots.headType)) {
            ItemStack stack = inv.getStackInSlot(slot);
            if (stack != null && (stack.getItem() instanceof IBaubleRendering)) {
                GL11.glPushMatrix();
                GL11.glRotatef(yawOffset, 0, -1, 0);
                GL11.glRotatef(yaw - 270, 0, 1, 0);
                GL11.glRotatef(pitch, 0, 0, 1);
                GL11.glColor4f(1F, 1F, 1F, 1F);
                if (stack.getItem() instanceof IBaubleRendering)
                    ((IBaubleRendering) stack.getItem()).onPlayerBaubleRender(stack, event);
                GL11.glPopMatrix();
            }
        }
    }
}
