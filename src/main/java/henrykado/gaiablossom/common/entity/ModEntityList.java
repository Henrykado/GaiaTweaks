package henrykado.gaiablossom.common.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import henrykado.gaiablossom.GaiaBlossom;

public final class ModEntityList {

    public static void init() {
        int id = 0;

        EntityRegistry.registerModEntity(
            EntityLivingWoodItemFrame.class,
            "gaiablossom:livingFrame",
            id,
            GaiaBlossom.INSTANCE,
            64,
            10,
            false);
    }
}
