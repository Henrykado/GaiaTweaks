/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [22/10/2016, 16:52:36 (GMT)]
 */
package henrykado.gaiablossom.common.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import henrykado.gaiablossom.network.GaiaPacketHandler;
import io.netty.buffer.ByteBuf;
import vazkii.botania.common.item.equipment.bauble.ItemBauble;

public class ItemCloudPendant extends ItemBauble {

    private static int timesJumped;
    private static boolean jumpDown;

    public ItemCloudPendant(String name) {
        super(name);
    }

    public ItemCloudPendant() {
        this("cloud_pendant");
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.AMULET;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        if (player instanceof EntityPlayerSP playerSp && player == Minecraft.getMinecraft().thePlayer) {
            // UUID uuid = playerSp.getUniqueID();

            if (playerSp.onGround) {
                timesJumped = 0;
                jumpDown = true;
            } else {
                if (playerSp.movementInput.jump) {
                    if (!jumpDown && timesJumped < 1) {
                        playerSp.jump();
                        GaiaPacketHandler.INSTANCE.sendToServer(new PacketJump());
                        timesJumped++;
                    }
                    jumpDown = true;
                } else jumpDown = false;
            }
        }
    }

    /*
     * @Override
     * @SideOnly(Side.CLIENT)
     * public void onPlayerBaubleRender(ItemStack stack, RenderPlayerEvent event, RenderType type) {
     *//*
        * if(type == RenderType.BODY) {
        * Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        * Helper.rotateIfSneaking(player);
        * boolean armor = player.inventory.armorInventory[2] != null;
        * GL11.glRotatef(180F, 1F, 0F, 0F);
        * GL11.glTranslatef(-0.2F, -0.3F, armor ? 0.2F : 0.15F);
        * GL11.glScalef(0.5F, 0.5F, 0.5F);
        * TextureAtlasSprite gemIcon = MiscellaneousIcons.INSTANCE.cirrusGem;
        * float f = gemIcon.getMinU();
        * float f1 = gemIcon.getMaxU();
        * float f2 = gemIcon.getMinV();
        * float f3 = gemIcon.getMaxV();
        * IconHelper.renderIconIn3D(Tessellator.getInstance(), f1, f2, f, f3, gemIcon.getIconWidth(),
        * gemIcon.getIconHeight(), 1F / 32F);
        * }
        *//*
           * if (type == IBaubleRender.RenderType.BODY) {
           * Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
           * IBaubleRender.Helper.rotateIfSneaking(event.entityPlayer);
           * boolean armor = event.entityPlayer.getCurrentArmor(2) != null;
           * GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
           * GL11.glTranslatef(-0.26F, -0.4F, armor ? 0.2F : 0.15F);
           * GL11.glScalef(0.5F, 0.5F, 0.5F);
           * for(int i = 2; i < 4; ++i) {
           * IIcon icon = this.icons[i];
           * float f = icon.getMinU();
           * float f1 = icon.getMaxU();
           * float f2 = icon.getMinV();
           * float f3 = icon.getMaxV();
           * ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(),
           * 0.03125F);
           * Color color = new Color(this.getColorFromItemStack(stack, 1));
           * GL11.glColor3ub((byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue());
           * int light = 15728880;
           * int lightmapX = light % 65536;
           * int lightmapY = light / 65536;
           * OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lightmapX, (float)lightmapY);
           * }
           * }
           * }
           */

    public static class PacketJump implements IMessage {

        @Override
        public void fromBytes(ByteBuf buf) {}

        @Override
        public void toBytes(ByteBuf buf) {}

        public static class Handler implements IMessageHandler<PacketJump, IMessage> {

            @Override
            public IMessage onMessage(PacketJump message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                IInventory baublesInv = BaublesApi.getBaubles(player);
                ItemStack amuletStack = baublesInv.getStackInSlot(0);

                if (amuletStack != null && amuletStack.getItem() instanceof ItemCloudPendant) {
                    player.addExhaustion(0.3F);
                    player.fallDistance = 0;

                    /*
                     * ItemStack belt = BaublesApi.getBaubles(player).getStackInSlot(3);
                     * if(belt != null && belt.getItem() instanceof ItemTravelBelt)
                     * player.fallDistance = -((ItemTravelBelt) belt.getItem()). * ((CloudPendantShim)
                     * amuletStack.getItem()).getMaxAllowedJumps();
                     */
                }
                return null;
            }
        }

    }

}
