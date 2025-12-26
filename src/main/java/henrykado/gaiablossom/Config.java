package henrykado.gaiablossom;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config {

    public static int swamplandWaterColorOverride = 4718414;

    public static boolean showAchievementsInventoryButton = true;
    public static boolean enableSwordParry = true;
    public static boolean disableVillages = true;
    public static boolean enableFasterLadders = true;
    public static boolean slowerCropGrowth = true;

    public static boolean enableStaminaSystem = true;
    public static boolean scarceMeat = true;
    public static float healMultiplier = 1.0f;
    public static String[] foodHealValues = new String[] {};
    public static String[] foodBuffs = new String[] { "golden_carrot:16:12", "baked_potato:5:8", "cookie:1:7",
        "etfuturum.honey_bottle:1:15", "pumpkin_pie:10:2", "mushroom_stew:10:5", "etfuturum.rabbit_stew:8:12" };
    public static String[] fastFood = new String[] { "melon", "spider_eye", "etfuturum.sweet_berries" };

    public static int endermanSpawnWeight = 15;
    public static boolean swampOnlyWitches = true;

    public static Property netherMovementFactor;

    public static boolean betterBattleTowerSpawner = true;
    public static boolean removeNetherBattleTower = true;
    public static int battleTowerGolemDrops = 2;
    public static int battleTowerGolemExtraDrops = 2;
    public static boolean aetherBaubles = true;
    public static boolean aetherMasterToggle = true;
    public static boolean gogglesOfRevealingBauble = true; //
    public static boolean nodeOreInfusion = true; //
    public static boolean tweakedAetherLoot = true;
    public static boolean removeHammerzTorchPlacing = true; //

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        /*
         * swamplandWaterColorOverride = configuration.getInt(
         * "swamplandWaterColorOverride",
         * Configuration.CATEGORY_GENERAL,
         * swamplandWaterColorOverride,
         * 0,
         * Integer.MAX_VALUE,
         * "");
         */

        enableStaminaSystem = configuration
            .getBoolean("enableStaminaSystem", Configuration.CATEGORY_GENERAL, true, "Enables the new stamina system");

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
            .getBoolean("disableVillages", Configuration.CATEGORY_GENERAL, true, "Disables village generation");

        swampOnlyWitches = configuration.getBoolean(
            "swampOnlyWitches",
            Configuration.CATEGORY_GENERAL,
            true,
            "Witches only spawn in the Swampland biome");

        endermanSpawnWeight = configuration.getInt(
            "endermanSpawnWeight",
            Configuration.CATEGORY_GENERAL,
            15,
            0,
            Integer.MAX_VALUE,
            "Changes how often Enderman spawn");

        netherMovementFactor = configuration.get(
            Configuration.CATEGORY_GENERAL,
            "netherMovementFactor",
            1.0D,
            "How much moving one block in the Nether be equivalent to in the Overworld. Vanilla is 8.0",
            1.0D,
            100.0D);

        scarceMeat = configuration.getBoolean("scarceMeat", "hunger", true, "Slower egg drops and cows drop less beef");

        slowerCropGrowth = configuration
            .getBoolean("slowerCropGrowth", "hunger", true, "Enable to halve the speed crops grow at");

        healMultiplier = configuration
            .getFloat("healingFoodMultiplier", "hunger", 1.0f, 0.0f, 5.0f, "Requires enableStaminaSystem");

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

        showAchievementsInventoryButton = configuration
            .getBoolean("showAchievementsInventoryButton", "client", true, "");

        betterBattleTowerSpawner = configuration.getBoolean(
            "betterBattleTowerSpawner",
            "modded",
            true,
            "Makes the Battle Towers mod use a custom mob spawner that destroys itself after a certain amount of spawns, and that has a lower spawn range");

        removeNetherBattleTower = configuration.getBoolean(
            "removeNetherBattleTower",
            "modded",
            true,
            "Removes the nether battle tower and prevents battle towers from generating outside the overworld");

        battleTowerGolemDrops = configuration.getInt(
            "battleTowerGolemDrops",
            "modded",
            2,
            0,
            Integer.MAX_VALUE,
            "How much of each item will BT's golem drop");
        battleTowerGolemExtraDrops = configuration.getInt(
            "battleTowerGolemExtraDrops",
            "modded",
            2,
            0,
            Integer.MAX_VALUE,
            "How many extra items will Bt's golem drop (random amount from 0 to this value)");

        tweakedAetherLoot = configuration
            .getBoolean("tweakedAetherLoot", "modded", false, "Requires my fork of Botania to work");

        aetherBaubles = configuration
            .getBoolean("aetherBaubles", "modded", true, "Replace Aether's accessories system with Baubles");

        aetherMasterToggle = configuration
            .getBoolean("aetherMasterToggle", "modded", true, "Enables all other Aether tweaks");

        gogglesOfRevealingBauble = configuration.getBoolean(
            "gogglesOfRevealingBauble",
            "modded",
            true,
            "Makes the Goggles of Revealing from TC4 wearable as a bauble");

        nodeOreInfusion = configuration.getBoolean(
            "nodeOreInfusion",
            "modded",
            true,
            "Makes aura nodes 'infuse' stone blocks in a 3 block radius with an aspect");

        removeHammerzTorchPlacing = configuration.getBoolean(
            "removeHammerzTorchPlacing",
            "modded",
            true,
            "Removes the torch placing functionality from HammerZ's hammers");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
