package henrykado.gaiablossom.client;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import henrykado.gaiablossom.CommonProxy;
import henrykado.gaiablossom.client.event.AccessoryButtonRemover;
import henrykado.gaiablossom.client.event.AchievementKeyHandler;
import henrykado.gaiablossom.client.event.RenderEventHandler;
import henrykado.gaiablossom.client.gui.AchievementTab;
import henrykado.gaiablossom.client.gui.HealthBarRenderer;

public class ClientProxy extends CommonProxy {

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        if (!Loader.isModLoaded("TConstruct")) {
            final HealthBarRenderer healthBarRenderer = new HealthBarRenderer();
            MinecraftForge.EVENT_BUS.register(healthBarRenderer);
            FMLCommonHandler.instance()
                .bus()
                .register(healthBarRenderer);
        }

        FMLCommonHandler.instance()
            .bus()
            .register(new AchievementKeyHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        MinecraftForge.EVENT_BUS.register(new AccessoryButtonRemover());
        MinecraftForge.EVENT_BUS.register(new RenderEventHandler());

        MinecraftForge.EVENT_BUS.register(new AchievementTab());
    }
}
