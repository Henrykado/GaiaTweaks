package henrykado.gaiablossom.asm.replacements;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class NewModelTFDeer extends ModelQuadruped {

    public ModelRenderer headChild;
    public ModelRenderer headChild_1;
    public ModelRenderer headChild_2;
    public ModelRenderer headChild_3;
    public ModelRenderer headChild_4;
    public ModelRenderer bodyChild;
    public ModelRenderer bodyChild_1;

    public NewModelTFDeer() {
        super(12, 0.0f);

        // field_78145_g = 10F;

        this.leg3 = new ModelRenderer(this, 0, 17);
        this.leg3.setRotationPoint(0.0F, 12.0F, -5.0F);
        this.leg3.addBox(-3.0F, 0.0F, -3.0F, 2, 12, 3, 0.0F);
        this.headChild_4 = new ModelRenderer(this, 52, 0);
        this.headChild_4.setRotationPoint(0.0F, 0.5F, -6.0F);
        this.headChild_4.addBox(-1.5F, -2.5F, 0.0F, 3, 3, 3, 0.0F);
        this.bodyChild_1 = new ModelRenderer(this, 18, -3);
        this.bodyChild_1.setRotationPoint(-2.0F, 8.0F, -1.0F);
        this.bodyChild_1.addBox(1.0F, -0.5F, -0.5F, 0, 6, 3, 0.0F);
        this.setRotateAngle(bodyChild_1, 17.453292519943293F, 0.0F, 0.0F);
        this.leg2 = new ModelRenderer(this, 0, 17);
        this.leg2.setRotationPoint(2.0F, 12.0F, 9.0F);
        this.leg2.addBox(-1.0F, 0.0F, -2.0F, 2, 12, 3, 0.0F);
        this.headChild_3 = new ModelRenderer(this, 28, 1);
        this.headChild_3.mirror = true;
        this.headChild_3.setRotationPoint(5.0F, -8.9F, 1.5F);
        this.headChild_3.addBox(-4.0F, -5.0F, 0.0F, 8, 10, 0, 0.0F);
        this.headChild_2 = new ModelRenderer(this, 1, 0);
        this.headChild_2.mirror = true;
        this.headChild_2.setRotationPoint(-3.8F, -7.5F, 3.2F);
        this.headChild_2.addBox(-3.5F, 1.0F, 0.0F, 5, 4, 0, 0.0F);
        this.setRotateAngle(headChild_2, 0.0F, 0.3490658503988659F, 0.0F);
        this.headChild_1 = new ModelRenderer(this, 28, 1);
        this.headChild_1.setRotationPoint(-5.0F, -8.9F, 1.5F);
        this.headChild_1.addBox(-4.0F, -5.0F, 0.0F, 8, 10, 0, 0.0F);
        this.bodyChild = new ModelRenderer(this, 10, 19);
        this.bodyChild.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bodyChild.addBox(-2.5F, -9.0F, -11.0F, 3, 9, 4, 0.0F);
        this.setRotateAngle(bodyChild, -1.1344640254974365F, 0.0F, 0.0F);
        this.leg4 = new ModelRenderer(this, 0, 17);
        this.leg4.setRotationPoint(2.0F, 12.0F, -5.0F);
        this.leg4.addBox(-1.0F, 0.0F, -3.0F, 2, 12, 3, 0.0F);
        this.body = new ModelRenderer(this, 36, 6);
        this.body.setRotationPoint(1.0F, 5.0F, 2.0F);
        this.body.addBox(-4.0F, -10.0F, -7.0F, 6, 18, 8, 0.0F);
        this.setRotateAngle(body, 1.5707963705062866F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 5);
        this.head.setRotationPoint(0.0F, 2.0F, -10.5F);
        this.head.addBox(-2.5F, -5.0F, -3.0F, 5, 6, 6, 0.0F);
        this.leg1 = new ModelRenderer(this, 0, 17);
        this.leg1.setRotationPoint(0.0F, 12.0F, 9.0F);
        this.leg1.addBox(-3.0F, 0.0F, -2.0F, 2, 12, 3, 0.0F);
        this.headChild = new ModelRenderer(this, 1, 0);
        this.headChild.setRotationPoint(5.8F, -7.5F, 4.0F);
        this.headChild.addBox(-3.5F, 1.0F, 0.0F, 5, 4, 0, 0.0F);
        this.setRotateAngle(headChild, 0.0F, -0.3490658503988659F, 0.0F);
        this.head.addChild(this.headChild_4);
        this.body.addChild(this.bodyChild_1);
        this.head.addChild(this.headChild_3);
        this.head.addChild(this.headChild_2);
        this.head.addChild(this.headChild_1);
        this.body.addChild(this.bodyChild);
        this.head.addChild(this.headChild);
        this.head.rotateAngleX = 0.2618F;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_,
        float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
        float f6 = (180F / (float) Math.PI);
        // this.head.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);
        this.head.rotateAngleY = p_78087_4_ / (180F / (float) Math.PI);
        this.body.rotateAngleX = ((float) Math.PI / 2F);
        this.leg1.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
        this.leg2.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float) Math.PI) * 1.4F * p_78087_2_;
        this.leg3.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float) Math.PI) * 1.4F * p_78087_2_;
        this.leg4.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
    }
}
