package henrykado.gaiablossom.quark;

import static net.minecraft.block.Block.soundTypePiston;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import henrykado.gaiablossom.Config;
import henrykado.gaiablossom.common.block.ModBlock;
import henrykado.gaiablossom.quark.tweaks.BetterRecipes;
import henrykado.gaiablossom.quark.world.UndergroundBiomeGenerator;

public class Quark {

    public static Block icystone;
    public static Block cobbedstone;

    public static ArrayList<UndergroundBiomeGenerator> undergroundBiomes = new ArrayList<>();

    public static void preInit() {
        icystone = new ModBlock(Material.rock, "icystone").setHardness(2.0F)
            .setResistance(10.0F)
            .setStepSound(soundTypePiston);
        cobbedstone = new ModBlock(Material.rock, "cobbedstone").setHardness(2.0F)
            .setResistance(10.0F)
            .setStepSound(soundTypePiston);

        for (UndergroundBiomeGenerator.UndergroundBiomeData data : Config.undergroundBiomeList) {
            undergroundBiomes.add(
                new UndergroundBiomeGenerator(
                    data.biome,
                    data.rarity,
                    data.minWidth,
                    data.minHeight,
                    data.minWidth,
                    data.widthVariation,
                    data.heightVariation,
                    data.widthVariation,
                    data.minY,
                    data.maxY,
                    data.types));
        }
    }

    public static void postInit() {
        BetterRecipes.init();
    }
}
