package henrykado.gaiablossom.asm.replacements;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSheep2;

public class NewModelTFBighorn extends ModelSheep2 {

    public NewModelTFBighorn() {
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-3.0F, -4.0F, -6.0F, 6, 6, 7, 0.0F);
        this.head.setRotationPoint(0.0F, 6.0F, -8.0F);
        this.body = new ModelRenderer(this, 36, 10);
        this.body.addBox(-4.0F, -9.0F, -7.0F, 8, 16, 6, 0.0F);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);

        this.head.setTextureOffset(28, 16)
            .addBox(-5.0F, -4.0F, -4.0F, 2, 2, 2, 0.0F);
        this.head.setTextureOffset(16, 13)
            .addBox(-6.0F, -5.0F, -3.0F, 2, 2, 4, 0.0F);
        this.head.setTextureOffset(16, 19)
            .addBox(-7.0F, -4.0F, 0.0F, 2, 5, 2, 0.0F);
        this.head.setTextureOffset(18, 27)
            .addBox(-8.0F, 0.0F, -2.0F, 2, 2, 3, 0.0F);
        this.head.setTextureOffset(28, 27)
            .addBox(-9.0F, -1.0F, -3.0F, 2, 2, 1, 0.0F);
        this.head.setTextureOffset(28, 16)
            .addBox(3.0F, -4.0F, -4.0F, 2, 2, 2, 0.0F);
        this.head.setTextureOffset(16, 13)
            .addBox(4.0F, -5.0F, -3.0F, 2, 2, 4, 0.0F);
        this.head.setTextureOffset(16, 19)
            .addBox(5.0F, -4.0F, 0.0F, 2, 5, 2, 0.0F);
        this.head.setTextureOffset(18, 27)
            .addBox(6.0F, 0.0F, -2.0F, 2, 2, 3, 0.0F);
        this.head.setTextureOffset(28, 27)
            .addBox(7.0F, -1.0F, -3.0F, 2, 2, 1, 0.0F);

        /*
         * this.horn1 = new ModelRenderer(this, 50, -6);
         * this.horn1.setRotationPoint(0.0F, 0.0F, 0.0F);
         * this.horn1.addBox(4.8F, -3.9F, -0.9F, 0, 8, 7, 0.0F);
         * this.setRotateAngle(horn1, 0.17453292519943295F, 0.7853981634F, 0.0F);
         * this.horn2 = new ModelRenderer(this, 50, -6);
         * this.horn2.setRotationPoint(0.0F, 0.0F, 0.0F);
         * this.horn2.addBox(-4.8F, -3.9F, -0.9F, 0, 8, 7, 0.0F);
         * this.setRotateAngle(horn2, 0.17453292519943295F, -0.7853981634F, 0.0F);
         * this.head.addChild(this.horn2);
         * this.head.addChild(this.horn1);
         */
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
