package henrykado.gaiablossom.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

public interface IRenderBauble {

    /**
     * Called for the rendering of the bauble on the player. The player instance can be
     * acquired through the event parameter. Transformations are already applied for
     * the RenderType passed in. Make sure to check against the type parameter for
     * rendering.
     */
    public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks);

    /**
     * A few helper methods for the render.
     */
    final class Helper {

        /**
         * Rotates the render for a bauble correctly if the player is sneaking.
         * Use for renders under {@link RenderType#BODY}.
         */
        public static void rotateIfSneaking(EntityPlayer player) {
            if (player.isSneaking()) applySneakingRotation();
        }

        /**
         * Rotates the render for a bauble correctly for a sneaking player.
         * Use for renders under {@link RenderType#BODY}.
         */
        public static void applySneakingRotation() {
            GL11.glTranslatef(0F, 0.2F, 0F);
            GL11.glRotatef(90F / (float) Math.PI, 1.0F, 0.0F, 0.0F);
        }

        /**
         * Shifts the render for a bauble correctly to the head, including sneaking rotation.
         * Use for renders under {@link RenderType#HEAD}.
         */
        public static void translateToHeadLevel(EntityPlayer player) {
            GL11.glTranslatef(0, -player.getDefaultEyeHeight(), 0);
            if (player.isSneaking()) GL11.glTranslatef(
                0.25F * MathHelper.sin(player.rotationPitch * (float) Math.PI / 180),
                0.25F * MathHelper.cos(player.rotationPitch * (float) Math.PI / 180),
                0F);
        }

        /**
         * Shifts the render for a bauble correctly to the face.
         * Use for renders under {@link RenderType#HEAD}, and usually after calling
         * {@link Helper#translateToHeadLevel(EntityPlayer)}.
         */
        public static void translateToFace() {
            GL11.glRotatef(90F, 0F, 1F, 0F);
            GL11.glRotatef(180F, 1F, 0F, 0F);
            GL11.glTranslatef(0f, -4.35f, -1.27f);
        }

        /**
         * Scales down the render to a correct size.
         * Use for any render.
         */
        public static void defaultTransforms() {
            GL11.glTranslated(0.0, 3.0, 1.0);
            GL11.glScaled(0.55, 0.55, 0.55);
        }

        /**
         * Shifts the render for a bauble correctly to the chest.
         * Use for renders under {@link RenderType#BODY}, and usually after calling
         * {@link Helper#rotateIfSneaking(EntityPlayer)}.
         */
        public static void translateToChest() {
            GL11.glRotatef(180F, 1F, 0F, 0F);
            GL11.glTranslatef(0F, -3.2F, -0.85F);
        }
    }

    public enum RenderType {
        /**
         * Render Type for the player's body, translations apply on the player's rotation.
         * Sneaking is not handled and should be done during the render.
         * 
         * @see IRenderBauble.Helper
         */
        BODY,

        /**
         * Render Type for the player's body, translations apply on the player's head rotations.
         * Sneaking is not handled and should be done during the render.
         * 
         * @see IRenderBauble.Helper
         */
        HEAD;
    }
}
