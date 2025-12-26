package henrykado.gaiablossom.asm.replacements;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.opengl.GL11;

import baubles.api.BaubleType;
import baubles.api.expanded.BaubleExpandedSlots;
import baubles.api.expanded.BaubleItemHelper;
import baubles.api.expanded.IBaubleExpanded;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.items.armor.ItemGoggles;
import vazkii.botania.api.item.IBaubleRender;

public class BaubleItemGoggles extends ItemGoggles implements IBaubleExpanded, IBaubleRender {

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
        tooltip.add(
            EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount")
                + " "
                + StatCollector.translateToLocal("tc.visdiscountGoggles")
                + ": "
                + this.getVisDiscount(stack, player, (Aspect) null)
                + "%");
        BaubleItemHelper.addSlotInformation(tooltip, getBaubleTypes(stack));
    }

    public ModelBiped goggleModel = new ModelBiped(1.0F);

    @Override
    public void onPlayerBaubleRender(ItemStack itemStack, RenderPlayerEvent event, RenderType renderType) {
        if (renderType == RenderType.HEAD) {
            EntityPlayer player = event.entityPlayer;

            Minecraft.getMinecraft().renderEngine
                .bindTexture(new ResourceLocation("thaumcraft:textures/models/goggles.png"));

            GL11.glRotatef(-90, 0, 1, 0);
            IBaubleRender.Helper.rotateIfSneaking(player);
            goggleModel.isRiding = player.isRiding();
            // GL11.glTranslated(-0.5, -0.5, armor ? 0.11999999731779099 : 0.0);
            float scale = player.inventory.armorInventory[3] != null ? 0.07F : 0.0625F;

            GL11.glTranslated(0.0F, (player.isSneaking() ? scale : 0.0) + 0.025F, 0.0F);
            goggleModel.bipedHead.render(scale);
        }
    }
}
