package henrykado.gaiablossom.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.opengl.GL11;

import baubles.api.expanded.BaubleExpandedSlots;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import henrykado.gaiablossom.util.BaublesUtils;
import thaumcraft.common.items.armor.ItemGoggles;
import vazkii.botania.api.item.IBaubleRender;

public class RenderEventHandler {

    public static ModelBiped modelGoggles = new ModelBiped(0.5F);

    @SubscribeEvent
    public void onRenderBaubles(RenderPlayerEvent.SetArmorModel event) {
        if (event.entityLiving instanceof AbstractClientPlayer player) {
            ItemStack itemstack = BaublesUtils.getStackInFirstSlotOfType(player, BaubleExpandedSlots.headType);
            if (itemstack != null && itemstack.getItem() instanceof ItemGoggles) {
                GL11.glPushAttrib(-1);

                Minecraft.getMinecraft().renderEngine.bindTexture(
                    new ResourceLocation(
                        itemstack.getItem()
                            .getArmorTexture(itemstack, player, 0, null)));

                GL11.glPushMatrix();

                float yaw = player.prevRotationYawHead
                    + (player.rotationYawHead - player.prevRotationYawHead) * event.partialRenderTick;
                float yawOffset = player.prevRenderYawOffset
                    + (player.renderYawOffset - player.prevRenderYawOffset) * event.partialRenderTick;
                float pitch = player.prevRotationPitch
                    + (player.rotationPitch - player.prevRotationPitch) * event.partialRenderTick;
                GL11.glRotatef(yawOffset, 0, -1, 0);
                GL11.glRotatef(yaw, 0, 1, 0);
                GL11.glRotatef(pitch, 1, 0, 0);

                IBaubleRender.Helper.rotateIfSneaking(player);
                float scale = 0.07F; // 0.0625
                GL11.glTranslated(0.0F, (player.isSneaking() ? scale : 0.0) + 0.025F, 0.0F);

                // GL11.glTranslatef(0.0F, (-24.0F * scale - 0.0078125F), 0.0F);
                modelGoggles.bipedHead.render(scale);

                // if (itemstack.isItemEnchanted())
                // renderGlint(entityIn, modelElytra, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw,
                // headPitch, scale);

                GL11.glPopMatrix();
                GL11.glPopAttrib();
            }
        }
    }
}
