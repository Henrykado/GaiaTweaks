package henrykado.gaiablossom;

import java.util.Arrays;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;

import baubles.api.expanded.BaubleExpandedSlots;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import henrykado.gaiablossom.common.block.ModBlock;
import henrykado.gaiablossom.common.entity.eep.ExtendedPropertiesHandler;
import henrykado.gaiablossom.common.event.AnvilEventHandler;
import henrykado.gaiablossom.common.event.AppleCoreEventHandler;
import henrykado.gaiablossom.common.event.PlayerEventHandler;
import henrykado.gaiablossom.common.event.WorldEventHandler;
import henrykado.gaiablossom.common.world.BiomeGenAutumnForest;
import henrykado.gaiablossom.network.GaiaPacketHandler;
import henrykado.gaiablossom.quark.Quark;

public class CommonProxy {

    public static BiomeGenBase autumnForest = new BiomeGenAutumnForest(Config.autumnForestBiomeID);

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfigurationLate();

        if (Config.disableVillages) MapGenVillage.villageSpawnBiomes = Arrays.asList(new BiomeGenBase[] {});

        // ModItems.init();
        ModBlock.registerEmBlocks();

        if (Loader.isModLoaded("Baubles")) {
            if (Config.gogglesOfRevealingBauble)
                BaubleExpandedSlots.tryAssignSlotsUpToMinimum(BaubleExpandedSlots.headType, 1);
            if (Config.aetherBaubles) BaubleExpandedSlots.tryAssignSlotsUpToMinimum(BaubleExpandedSlots.bodyType, 1);
        }

        MinecraftForge.EVENT_BUS.register(new ExtendedPropertiesHandler());
        if (Config.noRepairCost) {
            MinecraftForge.EVENT_BUS.register(new AnvilEventHandler());
        }

        if (Config.undergroundBiomes) {
            MinecraftForge.ORE_GEN_BUS.register(new WorldEventHandler());
        }

        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
        if (Loader.isModLoaded("AppleCore")) {
            MinecraftForge.EVENT_BUS.register(new AppleCoreEventHandler());
        }

        Quark.preInit();

        GaiaPacketHandler.init();
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        if (Config.autumnForest) {
            BiomeDictionary.registerBiomeType(autumnForest, BiomeDictionary.Type.FOREST);
            BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(autumnForest, 10));
        }
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        Quark.postInit();
    }
}
