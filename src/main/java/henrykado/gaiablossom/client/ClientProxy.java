package henrykado.gaiablossom.client;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import henrykado.gaiablossom.CommonProxy;
import henrykado.gaiablossom.Config;
import henrykado.gaiablossom.client.event.AccessoryButtonRemover;
import henrykado.gaiablossom.client.event.AchievementKeyHandler;
import henrykado.gaiablossom.client.event.BaubleRenderer;
import henrykado.gaiablossom.client.gui.AchievementTab;
import henrykado.gaiablossom.client.render.TileEntityMobSpawnerTowerRenderer;
import henrykado.gaiablossom.common.block.tile.TileEntityMobSpawnerTower;

public class ClientProxy extends CommonProxy {

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        /*
         * if (!Loader.isModLoaded("TConstruct")) {
         * final HealthBarRenderer healthBarRenderer = new HealthBarRenderer();
         * MinecraftForge.EVENT_BUS.register(healthBarRenderer);
         * FMLCommonHandler.instance()
         * .bus()
         * .register(healthBarRenderer);
         * }
         */

        if (Loader.isModLoaded("Thaumcraft") && Config.gogglesOfRevealingBauble) {
            MinecraftForge.EVENT_BUS.register(new BaubleRenderer());
        }

        FMLCommonHandler.instance()
            .bus()
            .register(new AchievementKeyHandler());

        ClientRegistry
            .bindTileEntitySpecialRenderer(TileEntityMobSpawnerTower.class, new TileEntityMobSpawnerTowerRenderer());

        // RenderingRegistry.registerEntityRenderingHandler(EntityGlassItemFrame.class, new RenderGlassItemFrame());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        if (Loader.isModLoaded("aether_legacy") && Loader.isModLoaded("baubles") && Config.aetherBaubles) {
            MinecraftForge.EVENT_BUS.register(new AccessoryButtonRemover());
        }
        // MinecraftForge.EVENT_BUS.register(new RenderEventHandler());

        if (Config.showAchievementsInventoryButton) MinecraftForge.EVENT_BUS.register(new AchievementTab());
    }
}
