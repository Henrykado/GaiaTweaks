package henrykado.gaiablossom;

import java.util.Arrays;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.common.MinecraftForge;

import baubles.api.expanded.BaubleExpandedSlots;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import henrykado.gaiablossom.common.block.ModBlock;
import henrykado.gaiablossom.common.entity.eep.ExtendedPropertiesHandler;
import henrykado.gaiablossom.event.AnvilEventHandler;
import henrykado.gaiablossom.event.PlayerEventHandler;
import henrykado.gaiablossom.event.WorldEventHandler;
import henrykado.gaiablossom.network.GaiaPacketHandler;
import henrykado.gaiablossom.quark.Quark;

public class CommonProxy {

    // public static Enchantment growth = new EnchantmentGrowth(240, 1);

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        if (Config.disableVillages) MapGenVillage.villageSpawnBiomes = Arrays.asList(new BiomeGenBase[] {});

        // ModItems.init();
        ModBlock.registerEmBlocks();

        BaubleExpandedSlots.tryAssignSlotsUpToMinimum(BaubleExpandedSlots.headType, 1);
        BaubleExpandedSlots.tryAssignSlotsUpToMinimum(BaubleExpandedSlots.bodyType, 1);

        MinecraftForge.EVENT_BUS.register(new ExtendedPropertiesHandler());
        MinecraftForge.EVENT_BUS.register(new AnvilEventHandler());

        MinecraftForge.ORE_GEN_BUS.register(new WorldEventHandler());

        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());

        Quark.preInit();

        GaiaPacketHandler.init();
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        // ModEntityList.init();

        // AetherAPI.instance().register(new AetherEnchantment());
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        Quark.init();
    }
}
