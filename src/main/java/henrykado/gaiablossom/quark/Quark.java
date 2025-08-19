package henrykado.gaiablossom.quark;

import static net.minecraft.block.Block.soundTypePiston;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.BiomeDictionary;

import henrykado.gaiablossom.common.block.ModBlock;
import henrykado.gaiablossom.quark.tweaks.BetterRecipes;
import henrykado.gaiablossom.quark.world.UndergroundBiome;
import henrykado.gaiablossom.quark.world.UndergroundBiomeGenerator;
import henrykado.gaiablossom.quark.world.UndergroundBiomeIcy;
import henrykado.gaiablossom.quark.world.UndergroundBiomeLush;
import henrykado.gaiablossom.quark.world.UndergroundBiomeOvergrown;
import henrykado.gaiablossom.quark.world.UndergroundBiomeSandstone;
import henrykado.gaiablossom.quark.world.UndergroundBiomeSlime;
import henrykado.gaiablossom.quark.world.UndergroundBiomeSpiderNest;

public class Quark {

    public static ArrayList<UndergroundBiomeGenerator> biomes = new ArrayList<>();

    public static Block icystone;
    public static Block cobbedstone;

    public static void preInit() {
        biomes.add(
            new UndergroundBiomeGenerator(
                new UndergroundBiomeSandstone(),
                10,
                32,
                12,
                32,
                12,
                6,
                12,
                30,
                58,
                BiomeDictionary.Type.SANDY));
        addUndergroundBiome(new UndergroundBiomeSlime(), BiomeDictionary.Type.SWAMP);
        addUndergroundBiome(new UndergroundBiomeLush(), BiomeDictionary.Type.JUNGLE);
        addUndergroundBiome(new UndergroundBiomeOvergrown(), BiomeDictionary.Type.FOREST);
        biomes.add(
            new UndergroundBiomeGenerator(
                new UndergroundBiomeSpiderNest(),
                80,
                26,
                12,
                26,
                14,
                6,
                14,
                30,
                58,
                BiomeDictionary.Type.PLAINS));
        addUndergroundBiome(new UndergroundBiomeIcy(), BiomeDictionary.Type.SNOWY);

        icystone = new ModBlock(Material.rock).setHardness(2.0F)
            .setResistance(10.0F)
            .setStepSound(soundTypePiston)
            .setBlockName("icystone");
        cobbedstone = new ModBlock(Material.rock).setHardness(2.0F)
            .setResistance(10.0F)
            .setStepSound(soundTypePiston)
            .setBlockName("cobbedstone");
    }

    public static void addUndergroundBiome(UndergroundBiome biome, BiomeDictionary.Type type) {
        biomes.add(new UndergroundBiomeGenerator(biome, 15, 26, 12, 26, 14, 6, 14, 30, 54, type));
    }

    public static void init() {
        BetterRecipes.init();
    }
}
