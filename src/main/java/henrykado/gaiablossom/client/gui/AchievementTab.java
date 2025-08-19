package henrykado.gaiablossom.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// code adapted from Tinkers' Construct
public class AchievementTab extends GuiButton {

    public AchievementTab() {
        super(0, 0, 0, 28, 32, "");
    }

    ResourceLocation texture = new ResourceLocation("gaiablossom", "textures/gui/tab.png");
    ItemStack renderStack = new ItemStack(Blocks.grass);
    RenderItem itemRenderer = new RenderItem();

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            mc.renderEngine.bindTexture(this.texture);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 2, 28, 32);
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        boolean inWindow = this.enabled && this.visible
            && mouseX >= this.xPosition
            && mouseY >= this.yPosition
            && mouseX < this.xPosition + this.width
            && mouseY < this.yPosition + this.height;

        if (inWindow) {
            mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(mc.thePlayer.openContainer.windowId));
            GuiAchievements achievements = new GuiAchievements(
                new GuiInventory(mc.thePlayer),
                mc.thePlayer.getStatFileWriter());
            mc.displayGuiScreen(achievements);
        }

        return inWindow;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void guiPostInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if ((event.gui instanceof GuiInventory)) {
            // int xSize = 176;
            int ySize = 166;
            int guiLeft = (event.gui.width / 2);
            int guiTop = (event.gui.height - ySize) / 2;
            guiLeft += getPotionOffset();

            id = 2;
            xPosition = guiLeft + 56;
            yPosition = guiTop - 31;

            event.gui.buttonList.add(this);
        }
    }

    public static int getPotionOffset() {
        // If at least one potion is active...
        if (!Minecraft.getMinecraft().thePlayer.getActivePotionEffects()
            .isEmpty()) {
            if (Loader.isModLoaded("NotEnoughItems")) {
                try {
                    // Check whether NEI is hidden and enabled
                    Class<?> c = Class.forName("codechicken.nei.NEIClientConfig");
                    Object hidden = c.getMethod("isHidden")
                        .invoke(null);
                    Object enabled = c.getMethod("isEnabled")
                        .invoke(null);
                    if (hidden != null && hidden instanceof Boolean && enabled != null && enabled instanceof Boolean) {
                        if ((Boolean) hidden || !((Boolean) enabled)) {
                            // If NEI is disabled or hidden, offset the tabs by 60
                            return 60;
                        }
                    }
                } catch (Exception e) {}
            } else {
                // If NEI is not installed, offset the tabs
                return 60;
            }
        }

        // No potions, no offset needed
        return 0;
    }
}
