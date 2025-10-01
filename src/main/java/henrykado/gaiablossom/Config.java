package henrykado.gaiablossom;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static String greeting = "Hello World";
    public static int swamplandWaterColorOverride = 4718414;

    public static float healMultiplier = 1.0f;

    public static String[] foodHealValues = {}; // Item:healAmount
    public static String[] foodBuffs = { "golden_carrot:16:12", "baked_potato:5:8", "cookie:1:7",
        "etfuturum.honey_bottle:1:15", "pumpkin_pie:10:2", "mushroom_stew:10:5", "etfuturum.rabbit_stew:8:12" }; // Item:effect:duration
    // 1-speed; 2-slowness; 3-haste; 4-fatigue; 5-strength; 6-heal; 7-hurt; 8-jump; 9-confusion;
    // 10-regen; 11-resistance; 12-fire; 13-water; 14-invisibility; 15-blindness;
    // 16-night; 17-hunger; 18-weakness; 19-poison; 20-wither; 22-absorption

    public static String[] fastFood = { "melon", "spider_eye", "etfuturum.sweet_berries" };

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

        healMultiplier = configuration
            .getFloat("healingFoodMultiplier", Configuration.CATEGORY_GENERAL, 1.0f, 0.0f, 5.0f, "");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
