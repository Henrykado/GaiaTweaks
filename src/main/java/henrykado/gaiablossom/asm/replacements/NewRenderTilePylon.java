/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * <p>
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * <p>
 * File Created @ [Feb 18, 2014, 10:18:36 PM (GMT)]
 */
package henrykado.gaiablossom.asm.replacements;

import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import vazkii.botania.api.lexicon.multiblock.IMultiblockRenderHook;
import vazkii.botania.api.lexicon.multiblock.Multiblock;
import vazkii.botania.api.lexicon.multiblock.component.MultiblockComponent;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.core.handler.MultiblockRenderHandler;
import vazkii.botania.client.core.helper.ShaderHelper;
import vazkii.botania.client.model.IPylonModel;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.TilePylon;

public class NewRenderTilePylon extends TileEntitySpecialRenderer implements IMultiblockRenderHook {

    private static final ResourceLocation MANA_TEXTURE = new ResourceLocation("botania:textures/model/pylon_mana.png");
    private static final ResourceLocation NATURA_TEXTURE = new ResourceLocation(
        "botania:textures/model/pylon_natura.png");
    private static final ResourceLocation GAIA_TEXTURE = new ResourceLocation("botania:textures/model/pylon_gaia.png");

    private final ModelPylonMana manaModel = new ModelPylonMana();
    private final ModelPylonNatura naturaModel = new ModelPylonNatura();
    private final ModelPylonGaia gaiaModel = new ModelPylonGaia();

    public void renderTileEntityAt(@Nonnull TileEntity pylon, double d0, double d1, double d2, float pticks) {
        if (/* !pylon.getWorldObj().isBlockLoaded(pylon.getPos(), false) || */ pylon.getWorldObj()
            .getBlock(pylon.xCoord, pylon.yCoord, pylon.zCoord) != ModBlocks.pylon) return;

        renderPylon(pylon, d0, d1, d2, pticks, false, pylon.getBlockMetadata());
    }

    public void renderPylon(@Nonnull TileEntity pylon, double d0, double d1, double d2, float pticks,
        boolean renderingItem, int metadata) {
        // byte type = renderingItem ? forceVariant :
        // ModBlocks.pylon.getStateFromMeta(pylon.getBlockMetadata()).getValue(BotaniaStateProps.PYLON_VARIANT);

        IPylonModel model;
        switch (metadata) {
            default:
            case 0: {
                model = manaModel;
                Minecraft.getMinecraft().renderEngine.bindTexture(MANA_TEXTURE);
                break;
            }
            case 1: {
                model = naturaModel;
                Minecraft.getMinecraft().renderEngine.bindTexture(NATURA_TEXTURE);
                break;
            }
            case 2: {
                model = gaiaModel;
                Minecraft.getMinecraft().renderEngine.bindTexture(GAIA_TEXTURE);
                break;
            }
        }

        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glEnable(3042);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        float a = MultiblockRenderHandler.rendering ? 0.6F : 1F;
        GL11.glColor4f(1F, 1F, 1F, a);

        double worldTime = pylon.getWorldObj() == null ? 0.0 : (double) (ClientTickHandler.ticksInGame + pticks);

        worldTime += renderingItem ? 0 : new Random((long) (pylon.xCoord ^ pylon.yCoord ^ pylon.zCoord)).nextInt(360);

        GL11.glTranslated(d0, d1 + (renderingItem ? 1.35 : 1.5), d2);
        GL11.glScalef(1.0F, -1.0F, -1.0F);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 0F, -0.5F);
        if (!renderingItem) GL11.glRotatef((float) worldTime * 1.5F, 0F, 1F, 0F);

        model.renderRing();
        if (!renderingItem) GL11.glTranslated(0D, Math.sin(worldTime / 20D) / 20 - 0.025, 0D);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        if (!renderingItem) GL11.glTranslated(0D, Math.sin(worldTime / 20D) / 17.5, 0D);

        GL11.glTranslatef(0.5F, 0F, -0.5F);
        if (!renderingItem) GL11.glRotatef((float) -worldTime, 0F, 1F, 0F);

        GL11.glDisable(3008); // GlStateManager.disableAlpha();
        GL11.glDisable(2884); // GlStateManager.disableCull();

        if (!renderingItem) ShaderHelper.useShader(ShaderHelper.pylonGlow);
        model.renderCrystal();
        if (!renderingItem) ShaderHelper.releaseShader();

        GL11.glEnable(3008);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
        GL11.glDisable(3042); // GlStateManager.disableBlend();
        GL11.glEnable(32826); // GlStateManager.enableRescaleNormal();
        GL11.glPopMatrix();
    }

    @Override
    public void renderBlockForMultiblock(IBlockAccess world, Multiblock mb, Block block, int meta, RenderBlocks var5,
        MultiblockComponent comp, float var7) {
        GL11.glTranslated(-0.5, -0.25, -0.5);
        renderPylon((TilePylon) comp.getTileEntity(), 0, 0, 0, 0, true, meta);
    }

    @Override
    public boolean needsTranslate(Block block) {
        return true;
    }
}
