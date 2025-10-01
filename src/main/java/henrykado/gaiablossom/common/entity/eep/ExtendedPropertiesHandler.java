package henrykado.gaiablossom.common.entity.eep;

import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ExtendedPropertiesHandler {

    @SubscribeEvent
    public void entityConstruct(EntityEvent.EntityConstructing e) {
        if (e.entity instanceof EntitySkeleton) {
            e.entity.registerExtendedProperties(SkeletonProperties.PROP_NAME, new SkeletonProperties());
        } else if (e.entity instanceof EntityPlayer) {
            e.entity.registerExtendedProperties(GaiaPlayer.PROP_NAME, new GaiaPlayer());
        }
    }
}
