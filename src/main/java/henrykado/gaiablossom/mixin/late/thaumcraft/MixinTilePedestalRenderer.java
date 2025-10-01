package henrykado.gaiablossom.mixin.late.thaumcraft;

import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import jss.notfine.core.SettingsManager;
import thaumcraft.client.renderers.tile.TilePedestalRenderer;

@Mixin(TilePedestalRenderer.class)
public class MixinTilePedestalRenderer {

    @Redirect(
        method = "renderTileEntityAt",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isFancyGraphicsEnabled()Z"))
    private static boolean isFancyGraphicsRedirect() {
        return Minecraft.isFancyGraphicsEnabled() && SettingsManager.droppedItemDetail;
    }
}
