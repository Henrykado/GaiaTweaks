package henrykado.gaiablossom;

import java.io.File;
import java.util.ArrayList;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import cpw.mods.fml.common.Loader;
import henrykado.gaiablossom.quark.world.UndergroundBiome;
import henrykado.gaiablossom.quark.world.UndergroundBiomeGenerator;
import henrykado.gaiablossom.quark.world.UndergroundBiomeIcy;
import henrykado.gaiablossom.quark.world.UndergroundBiomeLush;
import henrykado.gaiablossom.quark.world.UndergroundBiomeOvergrown;
import henrykado.gaiablossom.quark.world.UndergroundBiomeSandstone;
import henrykado.gaiablossom.quark.world.UndergroundBiomeSlime;
import henrykado.gaiablossom.quark.world.UndergroundBiomeSpiderNest;

public class Config {

    private static Configuration configuration;

    public static int swamplandWaterColorOverride = 4718414;

    public static boolean showAchievementsInventoryButton = true;
    public static boolean renderSkeletonChargingBow = true;
    public static int animalTemptDelay = 30;
    public static boolean enableSwordParry = true;
    public static boolean disableVillages = false;
    public static boolean enableFasterLadders = true;
    public static boolean itemFrame45Degrees = true;
    public static boolean noRepairCost = true;
    public static boolean betterStairRecipe = true;
    public static boolean compactStairRecipe = true;
    public static boolean reversalStairRecipe = true;
    public static boolean reversalSlabRecipe = true;
    public static boolean undergroundBiomes = true;

    public static boolean slowerCropGrowth = false;
    public static boolean enableStaminaSystem = false;
    public static boolean scarceMeat = false;
    public static float healMultiplier = 1.0f;
    public static String[] foodHealValues = new String[] {};
    public static String[] foodBuffs = new String[] { "golden_carrot:16:12", "baked_potato:5:6", "cookie:1:7",
        "etfuturum.honey_bottle:1:15", "pumpkin_pie:11:6", "mushroom_stew:10:5", "etfuturum.rabbit_stew:8:12" };
    public static String[] fastFood = new String[] { "melon", "spider_eye" };

    public static int endermanSpawnWeight = 15;
    public static boolean swampOnlyWitches = true;

    public static Property netherMovementFactor;

    public static boolean betterBattleTowerSpawner = true;
    public static boolean removeNetherBattleTower = true;
    public static int battleTowerGolemDrops = 2;
    public static int battleTowerGolemExtraDrops = 2;

    public static boolean aetherBaubles = true;
    public static boolean aerbunnyAetherOnly = true;
    public static boolean aerbunnySmokeParticles = false;
    public static boolean silentValkyries = false;
    public static boolean tweakedAetherLoot = false;
    public static boolean nerfSentryBoots = false;
    public static boolean moasDropEggs = false;
    public static boolean lessGoldenAmberDrops = true;
    public static int ambrosiumStackSize = 64;

    public static int gravititeOreSize = 6; // change to 5
    public static int gravititeOreChance = 8; // change to 2
    public static int gravititeOreMaxY = 32;
    public static int zaniteOreSize = 8;
    public static int zaniteOreChance = 12;
    public static int zaniteOreMaxY = 64;
    public static int ambrosiumOreSize = 16;
    public static int ambrosiumOreChance = 15;
    public static int ambrosiumOreMaxY = 128;
    public static int icestoneSize = 16;
    public static int icestoneChance = 10;
    public static int icestoneMaxY = 128;

    public static boolean nodeOreInfusion = true;
    public static boolean taintedTrees = true;
    public static boolean gogglesOfRevealingBauble = true;
    public static boolean thaumometerIgnoresItemFrame = true;
    public static boolean overworldOnlyGreatwood = true;
    public static boolean removeHarnessModel = false;
    public static boolean infernalFurnaceSteel = true;
    public static String[] greatwoodBiomeIDBlacklist = { "29" };

    public static boolean rocketCreeperRainSpawn = false;
    public static boolean overworldOnlyDimensionalDoors = true;
    public static boolean overworldOnlyPrimitiveMonsters = true;
    public static boolean removePrimitiveMobsAchievements = false;
    public static boolean replaceAetherMimic = false;
    public static boolean removeHammerzTorchPlacing = true;
    public static boolean shearNetherRoots = true;

    public static ArrayList<UndergroundBiomeGenerator.UndergroundBiomeData> undergroundBiomeList = new ArrayList<>();

    public static void synchronizeConfiguration(File configFile) {
        configuration = new Configuration(configFile);

        /*
         * swamplandWaterColorOverride = configuration.getInt(
         * "swamplandWaterColorOverride",
         * Configuration.CATEGORY_GENERAL,
         * swamplandWaterColorOverride,
         * 0,
         * Integer.MAX_VALUE,
         * "");
         */

        enableSwordParry = configuration.getBoolean(
            "enableSwordParry",
            Configuration.CATEGORY_GENERAL,
            true,
            "Enables the sword parry mechanic (blocking right when a projectile hits you)");

        enableFasterLadders = configuration.getBoolean(
            "enableFasterLadders",
            Configuration.CATEGORY_GENERAL,
            true,
            "Enables ladder climbing being faster when looking upwards");

        disableVillages = configuration
            .getBoolean("disableVillages", Configuration.CATEGORY_GENERAL, false, "Disables village generation");

        swampOnlyWitches = configuration.getBoolean(
            "swampOnlyWitches",
            Configuration.CATEGORY_GENERAL,
            true,
            "Witches only spawn in the Swampland biome");

        endermanSpawnWeight = configuration.getInt(
            "endermanSpawnWeight",
            Configuration.CATEGORY_GENERAL,
            10,
            0,
            Integer.MAX_VALUE,
            "Changes how often Endermen spawn");

        netherMovementFactor = configuration.get(
            Configuration.CATEGORY_GENERAL,
            "netherMovementFactor",
            8.0D,
            "How much moving one block in the Nether be equivalent to in the Overworld",
            1.0D,
            100.0D);

        noRepairCost = configuration.getBoolean(
            "noRepairCost",
            Configuration.CATEGORY_GENERAL,
            true,
            "Removes the experience repair cost of unenchanted items. Materials also always repair a 3rd of an item's durability");

        betterStairRecipe = configuration.getBoolean(
            "betterStairRecipe",
            Configuration.CATEGORY_GENERAL,
            true,
            "Makes stair recipes craft 8 stairs instead of just 4");

        compactStairRecipe = configuration.getBoolean(
            "compactStairRecipe",
            Configuration.CATEGORY_GENERAL,
            true,
            "Adds new three-block stair recipes that craft 4 stair blocks. Use this with betterStairRecipe");

        reversalStairRecipe = configuration.getBoolean(
            "reversalStairRecipe",
            Configuration.CATEGORY_GENERAL,
            true,
            "Allows you to turn 4 stairs into ");

        reversalSlabRecipe = configuration.getBoolean("reversalSlabRecipe", Configuration.CATEGORY_GENERAL, true, "");

        enableStaminaSystem = configuration
            .getBoolean("enableStaminaSystem", "hunger", false, "Enables the new stamina system");

        scarceMeat = configuration
            .getBoolean("scarceMeat", "hunger", false, "Slower egg drops and cows drop less beef");

        slowerCropGrowth = configuration
            .getBoolean("slowerCropGrowth", "hunger", false, "Enable to halve the speed crops grow at");

        healMultiplier = configuration
            .getFloat("healingFoodMultiplier", "hunger", 1.0f, 0.0f, 10.0f, "Requires enableStaminaSystem");

        foodHealValues = configuration
            .getStringList("foodHealValues", "hunger", foodHealValues, "Requires enableStaminaSystem\nItem:healAmount");

        foodBuffs = configuration.getStringList(
            "foodBuffs",
            "hunger",
            foodBuffs,
            "Item:effect:duration\n"
                + "1-speed; 2-slowness; 3-haste; 4-fatigue; 5-strength; 6-heal; 7-hurt; 8-jump; 9-confusion;\n"
                + "10-regen; 11-resistance; 12-fire; 13-water; 14-invisibility; 15-blindness;\n"
                + "16-night; 17-hunger; 18-weakness; 19-poison; 20-wither; 22-absorption");

        fastFood = configuration.getStringList("fastFood", "hunger", fastFood, "food that is eaten twice as fast");

        showAchievementsInventoryButton = configuration.getBoolean(
            "showAchievementsInventoryButton",
            Configuration.CATEGORY_GENERAL,
            true,
            "Whether to add a button to the inventory that shows the Achievements menu");

        itemFrame45Degrees = configuration.getBoolean(
            "itemFrame45Degrees",
            Configuration.CATEGORY_GENERAL,
            true,
            "Backports 1.8 item frame functionality. Corporea compatible");

        renderSkeletonChargingBow = configuration.getBoolean(
            "renderSkeletonChargingBow",
            Configuration.CATEGORY_GENERAL,
            true,
            "Skeletons will now visually pull their arrows before shooting, like in 1.8+");

        animalTemptDelay = configuration.getInt(
            "animalTemptDelay",
            Configuration.CATEGORY_GENERAL,
            30,
            0,
            Integer.MAX_VALUE,
            "The amount of ticks it takes for an animal to be tempted by food again after you switch to another item. Vanilla is 100");

        betterBattleTowerSpawner = configuration.getBoolean(
            "betterBattleTowerSpawner",
            "battletowers",
            true,
            "Makes the Battle Towers mod use a custom mob spawner that destroys itself after a certain amount of spawns, and that has a lower spawn range");

        removeNetherBattleTower = configuration.getBoolean(
            "removeNetherBattleTower",
            "battletowers",
            true,
            "Removes the nether battle tower and prevents battle towers from generating outside the overworld");

        battleTowerGolemDrops = configuration.getInt(
            "battleTowerGolemDrops",
            "battletowers",
            2,
            0,
            Integer.MAX_VALUE,
            "How much of each item will BT's golem drop");

        battleTowerGolemExtraDrops = configuration.getInt(
            "battleTowerGolemExtraDrops",
            "battletowers",
            2,
            0,
            Integer.MAX_VALUE,
            "How many extra items will Bt's golem drop (random amount from 0 to this value)");

        tweakedAetherLoot = configuration.getBoolean(
            "tweakedAetherLoot",
            "aether",
            false,
            "Requires my fork of Botania to work. Made for my modpack");

        aetherBaubles = configuration
            .getBoolean("aetherBaubles", "aether", true, "Replace Aether's accessories system with Baubles");

        aerbunnyAetherOnly = configuration
            .getBoolean("aerbunnyAetherOnly", "aether", true, "Aerbunnies will despawn outside the Aether dimension");

        aerbunnySmokeParticles = configuration.getBoolean(
            "aerbunnySmokeParticles",
            "aether",
            false,
            "Whether aerbunnies spawn black smoke particles when on a player's head");

        silentValkyries = configuration.getBoolean(
            "silentValkyries",
            "aether",
            false,
            "Removes Valkyrie dialogue and the Valkyrie Queen's interaction UI");

        nerfSentryBoots = configuration.getBoolean(
            "nerfSentryBoots",
            "aether",
            false,
            "Makes the Sentry Boots have iron level durability and defence");

        moasDropEggs = configuration
            .getBoolean("moasDropEggs", "aether", false, "Whether Moas will drop their eggs like chickens or not");

        lessGoldenAmberDrops = configuration.getBoolean(
            "lessGoldenAmberDrops",
            "aether",
            true,
            "Makes golden amber logs drop less amber. Also removes the requirement to use an Aether tool to get the amber");

        ambrosiumStackSize = configuration
            .getInt("ambrosiumStackSize", "aether", 64, 0, 64, "Changes Ambrosium's maximum stack size");

        replaceAetherMimic = configuration.getBoolean(
            "replaceAetherMimic",
            "aether",
            false,
            "Replaces the Aether's mimic mob with the mimic added by Primitive Mobs. Requires the Primitive Mobs mod, of course");

        gravititeOreSize = configuration.getInt("gravititeOreSize", "aetherOreGen", 6, 0, Integer.MAX_VALUE, "");
        gravititeOreChance = configuration.getInt("gravititeOreChance", "aetherOreGen", 8, 0, Integer.MAX_VALUE, "");
        gravititeOreMaxY = configuration.getInt("gravititeOreMaxY", "aetherOreGen", 32, 0, Integer.MAX_VALUE, "");
        zaniteOreChance = configuration.getInt("zaniteOreChance", "aetherOreGen", 12, 0, Integer.MAX_VALUE, "");
        zaniteOreSize = configuration.getInt("zaniteOreSize", "aetherOreGen", 8, 0, Integer.MAX_VALUE, "");
        zaniteOreMaxY = configuration.getInt("zaniteOreMaxY", "aetherOreGen", 64, 0, Integer.MAX_VALUE, "");
        ambrosiumOreChance = configuration.getInt("ambrosiumOreChance", "aetherOreGen", 15, 0, Integer.MAX_VALUE, "");
        ambrosiumOreSize = configuration.getInt("ambrosiumOreSize", "aetherOreGen", 16, 0, Integer.MAX_VALUE, "");
        ambrosiumOreMaxY = configuration.getInt("ambrosiumOreMaxY", "aetherOreGen", 128, 0, Integer.MAX_VALUE, "");
        icestoneChance = configuration.getInt("icestoneChance", "aetherOreGen", 10, 0, Integer.MAX_VALUE, "");
        icestoneSize = configuration.getInt("icestoneSize", "aetherOreGen", 16, 0, Integer.MAX_VALUE, "");
        icestoneMaxY = configuration.getInt("icestoneMaxY", "aetherOreGen", 128, 0, Integer.MAX_VALUE, "");

        gogglesOfRevealingBauble = configuration.getBoolean(
            "gogglesOfRevealingBauble",
            "thaumcraft",
            false,
            "Makes the Goggles of Revealing from TC4 wearable as a bauble. Requires Baubles Expanded");

        taintedTrees = configuration.getBoolean(
            "taintedTrees",
            "thaumcraft",
            true,
            "Enables the generation of tainted trees in the Tainted Lands biome, as well as the Tainted Log block");

        nodeOreInfusion = configuration.getBoolean(
            "nodeOreInfusion",
            "thaumcraft",
            true,
            "Makes aura nodes 'infuse' a few stone blocks in a 3 block radius with an aspect every 3 minutes");

        thaumometerIgnoresItemFrame = configuration
            .getBoolean("thaumometerIgnoresItemFrame", "thaumcraft", true, "Makes the Thaumometer ignore item frames");

        overworldOnlyGreatwood = configuration.getBoolean(
            "overworldOnlyGreatwood",
            "thaumcraft",
            true,
            "Limits greatwood tree generation to the overworld");

        removeHarnessModel = configuration
            .getBoolean("removeHarnessModel", "thaumcraft", false, "Removes the Thaumostatic Harness' .obj model");

        infernalFurnaceSteel = configuration.getBoolean(
            "infernalFurnaceSteel",
            "thaumcraft",
            true,
            "Allows the Infernal Furnace to smelt iron ingots into Railcraft's steel ingots. Requires Railcraft");

        greatwoodBiomeIDBlacklist = configuration.getStringList(
            "greatwoodBiomeIDBlacklist",
            "thaumcraft",
            greatwoodBiomeIDBlacklist,
            "A list of biome IDs where greatwood trees will not generate");

        overworldOnlyDimensionalDoors = configuration.getBoolean(
            "overworldOnlyDimensionalDoors",
            "dimdoors",
            true,
            "Limits Dimensional Door gateway generation to the overworld");

        rocketCreeperRainSpawn = configuration.getBoolean(
            "rocketCreeperRainSpawn",
            "primitivemobs",
            false,
            "Makes it so Rocket Creepers can only spawn when it's raining");

        overworldOnlyPrimitiveMonsters = configuration.getBoolean(
            "overworldOnlyPrimitiveMonsters",
            "primitivemobs",
            true,
            "Limits Primitive Mobs' monsters spawning to the overworld");

        removePrimitiveMobsAchievements = configuration.getBoolean(
            "removePrimitiveMobsAchievements",
            "primitivemobs",
            false,
            "Removes Primitive Mobs' achievements page");

        removeHammerzTorchPlacing = configuration.getBoolean(
            "removeHammerzTorchPlacing",
            "hammerz",
            true,
            "Removes the torch placing functionality from HammerZ's hammers");

        shearNetherRoots = configuration.getBoolean(
            "shearNetherRoots",
            "etfuturum",
            true,
            "Makes it so the Crimson Forest's and the Warped Forest's fungal roots need shears to be dropped, just like grass");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static void synchronizeConfigurationLate() {
        addUndergroundBiome(
            configuration,
            new UndergroundBiomeSandstone(),
            BiomeDictionary.Type.SANDY,
            8,
            32,
            "desert");

        if (Loader.isModLoaded("etfuturum")) {
            addUndergroundBiome(
                configuration,
                new UndergroundBiomeSlime(),
                BiomeDictionary.Type.SWAMP,
                15,
                16,
                "swampSlime");
        }

        addUndergroundBiome(configuration, new UndergroundBiomeLush(), BiomeDictionary.Type.JUNGLE, 8, 22, "jungle");

        addUndergroundBiome(
            configuration,
            new UndergroundBiomeOvergrown(),
            BiomeDictionary.Type.CONIFEROUS,
            40,
            16,
            "tundra");

        addUndergroundBiome(
            configuration,
            new UndergroundBiomeSpiderNest(),
            BiomeDictionary.Type.PLAINS,
            80,
            16,
            "plainsSpider");

        addUndergroundBiome(configuration, new UndergroundBiomeIcy(), BiomeDictionary.Type.SNOWY, 15, 26, "icyCave");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    private static void addUndergroundBiome(Configuration config, UndergroundBiome biome,
        BiomeDictionary.Type defaultType, int defaultRarity, int defaultWidth, String name) {
        boolean enabled = config.getBoolean(name + "Enable", "undergroundBiomes." + name, true, "");

        int rarity = config
            .getInt(name + "Rarity", "undergroundBiomes." + name, defaultRarity, 0, Integer.MAX_VALUE, "");
        int minWidth = config
            .getInt(name + "MinimumWidth", "undergroundBiomes." + name, defaultWidth, 0, Integer.MAX_VALUE, "");
        int widthVariation = config
            .getInt(name + "WidthVariation", "undergroundBiomes." + name, 12, 0, Integer.MAX_VALUE, "");
        int minHeight = config
            .getInt(name + "MinimumHeight", "undergroundBiomes." + name, 10, 0, Integer.MAX_VALUE, "");
        int heightVariation = config
            .getInt(name + "HeightVariation", "undergroundBiomes." + name, 7, 0, Integer.MAX_VALUE, "");
        int minYLevel = config
            .getInt(name + "MinimumYLevel", "undergroundBiomes." + name, 18 + 8, 0, Integer.MAX_VALUE, "");
        int maxYLevel = config
            .getInt(name + "MaximumYLevel", "undergroundBiomes." + name, 48 - 8, 0, Integer.MAX_VALUE, "");
        ArrayList<BiomeDictionary.Type> types = new ArrayList<>();
        for (String type : config
            .getStringList(name + "BiomeTypes", "undergroundBiomes." + name, new String[] { defaultType.name() }, "")) {
            types.add(BiomeDictionary.Type.valueOf(type));
        }

        UndergroundBiomeGenerator.UndergroundBiomeData undergroundBiomeData = new UndergroundBiomeGenerator.UndergroundBiomeData(
            biome,
            rarity,
            minWidth,
            widthVariation,
            minHeight,
            heightVariation,
            minYLevel,
            maxYLevel,
            types);

        if (enabled) undergroundBiomeList.add(undergroundBiomeData);
    }
}
