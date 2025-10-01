package henrykado.gaiablossom.client.event;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.client.event.GuiScreenEvent;

import com.gildedgames.the_aether.client.gui.button.GuiAccessoryButton;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AccessoryButtonRemover {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void removeAccessoryButton(GuiScreenEvent.InitGuiEvent.Post event) {
        for (GuiButton button : (List<GuiButton>) event.buttonList) if (button instanceof GuiAccessoryButton) {
            button.enabled = false;
            button.visible = false;
        }
    }
}
