package henrykado.gaiablossom;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config {

    public static String greeting = "Hello World";
    public static int swamplandWaterColorOverride = 4718414;

    public static boolean enableStaminaSystem = true;
    public static float healMultiplier = 1.0f;
    public static String[] foodHealValues = new String[] {};
    public static String[] foodBuffs = new String[] { "golden_carrot:16:12", "baked_potato:5:8", "cookie:1:7",
        "etfuturum.honey_bottle:1:15", "pumpkin_pie:10:2", "mushroom_stew:10:5", "etfuturum.rabbit_stew:8:12" };
    public static String[] fastFood = new String[] { "melon", "spider_eye", "etfuturum.sweet_berries" };

    public static Property netherMovementFactor;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        greeting = configuration.getString("greeting", Configuration.CATEGORY_GENERAL, greeting, "How shall I greet?");

        swamplandWaterColorOverride = configuration.getInt(
            "swamplandWaterColorOverride",
            Configuration.CATEGORY_GENERAL,
            swamplandWaterColorOverride,
            0,
            Integer.MAX_VALUE,
            "");

        enableStaminaSystem = configuration.getBoolean(
            "enableStaminaSystem",
            Configuration.CATEGORY_GENERAL,
            true,
            "Enable or disable the hunger rework / stamina system");

        healMultiplier = configuration
            .getFloat("healingFoodMultiplier", Configuration.CATEGORY_GENERAL, 1.0f, 0.0f, 5.0f, "");

        foodHealValues = configuration
            .getStringList("foodHealValues", Configuration.CATEGORY_GENERAL, foodHealValues, "Item:healAmount");
        foodBuffs = configuration.getStringList(
            "foodBuffs",
            Configuration.CATEGORY_GENERAL,
            foodBuffs,
            "Item:effect:duration\n"
                + "1-speed; 2-slowness; 3-haste; 4-fatigue; 5-strength; 6-heal; 7-hurt; 8-jump; 9-confusion;\n"
                + "10-regen; 11-resistance; 12-fire; 13-water; 14-invisibility; 15-blindness;\n"
                + "16-night; 17-hunger; 18-weakness; 19-poison; 20-wither; 22-absorption");
        fastFood = configuration
            .getStringList("fastFood", Configuration.CATEGORY_GENERAL, fastFood, "food that is eaten twice as fast");

        netherMovementFactor = configuration.get(
            Configuration.CATEGORY_GENERAL,
            "netherMovementFactor",
            1.0D,
            "How much moving one block in the Nether be equivalent to in the Overworld. Vanilla is 8.0",
            1.0D,
            100.0D);

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
