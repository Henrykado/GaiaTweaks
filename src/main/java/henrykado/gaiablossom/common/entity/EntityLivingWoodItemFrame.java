package henrykado.gaiablossom.common.entity;

import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.world.World;

public class EntityLivingWoodItemFrame extends EntityItemFrame {

    public EntityLivingWoodItemFrame(World p_i1591_1_, int p_i1591_2_, int p_i1591_3_, int p_i1591_4_, int p_i1591_5_) {
        super(p_i1591_1_, p_i1591_2_, p_i1591_3_, p_i1591_4_, p_i1591_5_);
    }

    @Override
    public void setItemRotation(int p_82336_1_) {
        this.getDataWatcher()
            .updateObject(3, Byte.valueOf((byte) (p_82336_1_ % 8)));
    }
}
