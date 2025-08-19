package henrykado.gaiablossom.event.eep;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import henrykado.gaiablossom.GaiaBlossom;

public class SkeletonProperties implements IExtendedEntityProperties {

    public static final String PROP_NAME = GaiaBlossom.MODID + "_SkeletonEntityData";

    public static SkeletonProperties get(Entity p) {
        return (SkeletonProperties) p.getExtendedProperties(PROP_NAME);
    }

    public int rangedAttackTime = 60;

    @Override
    public void saveNBTData(NBTTagCompound compound) {}

    @Override
    public void loadNBTData(NBTTagCompound compound) {}

    @Override
    public void init(Entity entity, World world) {}
}
