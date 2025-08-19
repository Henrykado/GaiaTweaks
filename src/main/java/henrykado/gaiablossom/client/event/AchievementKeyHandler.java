package henrykado.gaiablossom.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

// taken from Baubles code
public class AchievementKeyHandler {

    public KeyBinding key = new KeyBinding(
        StatCollector.translateToLocal("keybind.achievements"),
        Keyboard.KEY_L,
        "key.categories.misc");

    public AchievementKeyHandler() {
        ClientRegistry.registerKeyBinding(key);
    }

    @SubscribeEvent
    public void onKeyEvent(InputEvent.KeyInputEvent event) {
        if (key.getIsKeyPressed()) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(mc.thePlayer.openContainer.windowId));
            GuiAchievements achievements = new GuiAchievements(
                new GuiInventory(mc.thePlayer),
                mc.thePlayer.getStatFileWriter());
            mc.displayGuiScreen(achievements);
        }
    }
}
