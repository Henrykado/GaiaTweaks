package henrykado.gaiablossom.asm.replacements;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;

public interface IBaubleRendering {

    public void onPlayerBaubleRender(ItemStack itemStack, RenderPlayerEvent event);
}
