package henrykado.gaiablossom.mixin.early.client;

import net.minecraft.client.renderer.entity.RenderGhast;
import net.minecraft.entity.monster.EntityGhast;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(RenderGhast.class)
public class MixinRenderGhast {

    /**
     * @author
     * @reason
     */
    @Overwrite
    protected void preRenderCallback(EntityGhast p_77041_1_, float p_77041_2_) {
        // GaiaBlossom.LOG.info(p_77041_1_.attackCounter);
        // GaiaBlossom.LOG.info(p_77041_1_.prevAttackCounter);
        float f1 = ((float) p_77041_1_.prevAttackCounter
            + (float) (p_77041_1_.attackCounter - p_77041_1_.prevAttackCounter) * p_77041_2_) / 20.0F;

        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        f1 = 1.0F / (f1 * f1 * f1 * f1 * f1 * 2.0F + 1.0F);
        float f2 = (8.0F + f1) / 2.0F;
        float f3 = (8.0F + 1.0F / f1) / 2.0F;
        GL11.glScalef(f3, f2, f3);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
