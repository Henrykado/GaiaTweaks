package henrykado.gaiablossom.asm.replacements;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import baubles.api.BaubleType;
import baubles.api.expanded.BaubleExpandedSlots;
import baubles.api.expanded.BaubleItemHelper;
import baubles.api.expanded.IBaubleExpanded;
import henrykado.gaiablossom.util.IRenderBauble;
import thaumcraft.common.items.armor.ItemGoggles;

public class BaubleItemGoggles extends ItemGoggles implements IBaubleExpanded, IRenderBauble {

    public BaubleItemGoggles(ArmorMaterial enumarmormaterial, int j, int k) {
        super(enumarmormaterial, j, k);
    }

    @Override
    public String[] getBaubleTypes(ItemStack itemstack) {

        return new String[] { BaubleExpandedSlots.headType };
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return null;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean debug) {
        super.addInformation(stack, player, tooltip, debug);
        BaubleItemHelper.addSlotInformation(tooltip, getBaubleTypes(stack));
    }

    public ModelBiped goggleModel = new ModelBiped(1.0F);

    @Override
    public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
        if (type == RenderType.HEAD) {
            boolean armor = false;
            Minecraft.getMinecraft().renderEngine
                .bindTexture(new ResourceLocation("thaumcraft:textures/models/goggles.png"));
            IRenderBauble.Helper.translateToHeadLevel(player);
            IRenderBauble.Helper.translateToFace();
            IRenderBauble.Helper.defaultTransforms();
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            GL11.glTranslated(-0.5, -0.5, armor ? 0.11999999731779099 : 0.0);
            goggleModel.bipedHead.render(0.0625F);
            // UtilsFX.renderTextureIn3D(0.0f, 0.0f, 1.0f, 1.0f, 16, 26, 0.1f);
        }
    }
}
