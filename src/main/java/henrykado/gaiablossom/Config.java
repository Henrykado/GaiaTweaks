package henrykado.gaiablossom;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static String greeting = "Hello World";
    public static int swamplandWaterColorOverride = 4718414;

    public static float healMultiplier = 1.0f;

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
