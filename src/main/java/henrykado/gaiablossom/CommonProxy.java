package henrykado.gaiablossom;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.MinecraftForge;

import baubles.api.expanded.BaubleExpandedSlots;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import henrykado.gaiablossom.common.enchantment.EnchantmentGrowth;
import henrykado.gaiablossom.common.entity.ModEntityList;
import henrykado.gaiablossom.common.item.ModItems;
import henrykado.gaiablossom.event.AnvilEventHandler;
import henrykado.gaiablossom.event.PlayerEventHandler;
import henrykado.gaiablossom.event.WorldEventHandler;
import henrykado.gaiablossom.event.eep.ExtendedPropertiesHandler;
import henrykado.gaiablossom.network.GaiaPacketHandler;
import henrykado.gaiablossom.quark.Quark;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.command.CommandSkyblockSpread;
import vazkii.botania.common.lexicon.LexiconData;
import vazkii.botania.common.world.SkyblockWorldEvents;
import vazkii.botania.common.world.WorldTypeSkyblock;

public class CommonProxy {

    public static Enchantment growth = new EnchantmentGrowth(240, 1);

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        ModItems.init();

        BaubleExpandedSlots.tryAssignSlotsUpToMinimum(BaubleExpandedSlots.headType, 1);
        BaubleExpandedSlots.tryAssignSlotsUpToMinimum(BaubleExpandedSlots.bodyType, 1);
        BaubleExpandedSlots.tryAssignSlotsUpToMinimum(BaubleExpandedSlots.charmType, 2);

        GaiaBlossom.LOG.info(Config.greeting);
        // GaiaBlossom.LOG.info("I am GaiaBlossom at version " + Tags.VERSION);

        MinecraftForge.EVENT_BUS.register(new ExtendedPropertiesHandler());
        MinecraftForge.EVENT_BUS.register(new AnvilEventHandler());

        MinecraftForge.ORE_GEN_BUS.register(new WorldEventHandler());

        Quark.preInit();

        GaiaPacketHandler.init();

        if (!Botania.gardenOfGlassLoaded) {
            new WorldTypeSkyblock();
        }
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        ModEntityList.init();

        Quark.init();

        if (!Botania.gardenOfGlassLoaded) {
            MinecraftForge.EVENT_BUS.register(new SkyblockWorldEvents());
        }
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());

        // AetherAPI.instance().register(new AetherEnchantment());
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        LexiconData.endoflame.setPriority();
        LexiconData.hydroangeas.setPriority();
        LexiconData.nightshade.setPriority();

        LexiconData.jadedAmaranthus.setPriority();
    }

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        if (!Botania.gardenOfGlassLoaded) {
            event.registerServerCommand(new CommandSkyblockSpread());
        }
    }
}
