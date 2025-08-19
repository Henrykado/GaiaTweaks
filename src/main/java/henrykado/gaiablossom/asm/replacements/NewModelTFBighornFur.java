package henrykado.gaiablossom.asm.replacements;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSheep1;

public class NewModelTFBighornFur extends ModelSheep1 {

    public NewModelTFBighornFur() {
        this.head = new ModelRenderer(this, 1, 1);
        this.head.setRotationPoint(0.0F, 6.0F, -8.0F);
        this.head.addBox(-3.0F, -4.0F, -4.0F, 6, 6, 5, 0.6F);
        this.body = new ModelRenderer(this, 28, 8);
        this.body.addBox(-4.0F, -9.0F, -7.0F, 8, 15, 6, 1.75F);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
        float f = 0.5F;
        this.leg1 = new ModelRenderer(this, 0, 16);
        this.leg1.setRotationPoint(-3.0F, 12.0F, 7.0F);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg2 = new ModelRenderer(this, 0, 16);
        this.leg2.setRotationPoint(3.0F, 12.0F, 7.0F);
        this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg3 = new ModelRenderer(this, 0, 16);
        this.leg3.setRotationPoint(-3.0F, 12.0F, -5.0F);
        this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg4 = new ModelRenderer(this, 0, 16);
        this.leg4.setRotationPoint(3.0F, 12.0F, -5.0F);
        this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
    }
}
