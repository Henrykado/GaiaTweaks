package henrykado.gaiablossom.mixin.early.client;

import net.minecraft.client.gui.achievement.GuiAchievements;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiAchievements.class)
public class MixinGuiAchievements {

    @Redirect(method = "func_146552_b", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor4f(FFFF)V"))
    public void changeColor(CallbackInfo ci, float r, float g, float b, float a) {
        if (r == 0.75f) {
            GL11.glColor4f(r, 0.9f, b, a);
        }
        GL11.glColor4f(r, g, b, a);
    }
}
