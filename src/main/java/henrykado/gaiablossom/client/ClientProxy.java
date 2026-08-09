package henrykado.gaiablossom.client;

import net.minecraft.client.Minecraft;
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
import henrykado.gaiablossom.common.block.tileentity.TileEntityMobSpawnerTower;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

        if (Config.generateStaminaResourcePack) generateTexturePack();

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

    // Taken from the Aether source code
    public void generateTexturePack()
    {
        try
        {
            File resourcePacks = Minecraft.getMinecraft().getResourcePackRepository().getDirResourcepacks().getCanonicalFile();

            File guiTexturesFolder = new File(resourcePacks + "/Stamina Bar/assets/minecraft/textures/gui");

            if (Config.generateStaminaResourcePack)
            {
                if (!guiTexturesFolder.exists())
                {
                    guiTexturesFolder.mkdirs();
                }

                generateFile("data/StaminaPack/pack.mcmeta", "pack.mcmeta", resourcePacks.getAbsolutePath() + "/Stamina Bar");
                generateFile("data/StaminaPack/pack.png", "pack.png", resourcePacks.getAbsolutePath() + "/Stamina Bar");
                generateFile("data/StaminaPack/icons.png", "icons.png", guiTexturesFolder.getAbsolutePath());
            }
        }
        catch (IOException ignore) { }
    }

    public void generateFile(String input, String name, String path)
    {
        try {
            File file = new File(path + "/" + name);

            if (!file.exists())
            {
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(input);
                FileOutputStream outputStream = new FileOutputStream(file);

                if (inputStream != null)
                {
                    int i;
                    while ((i = inputStream.read()) != -1)
                    {
                        outputStream.write(i);
                    }

                    inputStream.close();
                    outputStream.close();
                }
            }
        }
        catch (IOException ignore) { }
    }
}
