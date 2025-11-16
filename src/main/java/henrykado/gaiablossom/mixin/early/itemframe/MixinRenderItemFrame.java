package henrykado.gaiablossom.mixin.early.itemframe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItemFrame.class)
public abstract class MixinRenderItemFrame extends Render {

    @Shadow
    private static ResourceLocation mapBackgroundTextures;
    @Shadow
    private Minecraft field_147917_g;

    @Inject(method = "func_82402_b", at = @At(value = "INVOKE", target = "org/lwjgl/opengl/GL11.glPushMatrix()V"))
    public void disableItemLighting(CallbackInfo ci) {
        GL11.glDisable(GL11.GL_LIGHTING);
    }

    @Inject(method = "func_82402_b", at = @At(value = "INVOKE", target = "org/lwjgl/opengl/GL11.glPopMatrix()V"))
    public void reEnableGlLighting(CallbackInfo ci) {
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    @Redirect(
        method = "func_82402_b",
        at = @At(value = "INVOKE", target = "net/minecraft/entity/item/EntityItemFrame.getRotation()I", ordinal = 1))
    private int getRotationRedirect(EntityItemFrame instance) {
        switch (instance.getRotation()) {
            case 1:
                GL11.glTranslatef(-0.113F, -0.047F, 0.0F);
                break;
            case 2:
                GL11.glTranslatef(-0.16F, -0.16F, 0.0F);
                break;
            case 3:
                GL11.glTranslatef(-0.113F, -0.273F, 0.0F);
                break;
            case 4:
                GL11.glTranslatef(0.0F, -0.32F, 0.0F);
                break;
            case 5:
                GL11.glTranslatef(0.113F, -0.273F, 0.0F);
                break;
            case 6:
                GL11.glTranslatef(0.16F, -0.16F, 0.0F);
                break;
            case 7:
                GL11.glTranslatef(0.113F, -0.047F, 0.0F);
        }
        return -1;
    }

    @ModifyConstant(method = "func_82402_b", constant = @Constant(intValue = -90))
    public int rotationMultiplier(int constant) {
        return -45;
    }
}
