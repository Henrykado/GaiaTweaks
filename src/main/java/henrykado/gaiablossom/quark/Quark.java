package henrykado.gaiablossom.quark;

import static net.minecraft.block.Block.soundTypePiston;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.BiomeDictionary;

import henrykado.gaiablossom.Config;
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
        if (!Config.undergroundBiomes) return;

        addUndergroundBiome(new UndergroundBiomeSandstone(), BiomeDictionary.Type.SANDY, 8, 32, 12);
        addUndergroundBiome(new UndergroundBiomeSlime(), BiomeDictionary.Type.SWAMP, 15, 16, 10);
        addUndergroundBiome(new UndergroundBiomeLush(), BiomeDictionary.Type.JUNGLE, 8, 22, 10);
        addUndergroundBiome(new UndergroundBiomeOvergrown(), BiomeDictionary.Type.CONIFEROUS, 40, 16, 10);
        addUndergroundBiome(new UndergroundBiomeSpiderNest(), BiomeDictionary.Type.PLAINS, 80, 16, 10);
        addUndergroundBiome(new UndergroundBiomeIcy(), BiomeDictionary.Type.SNOWY, 15, 26, 14);

        icystone = new ModBlock(Material.rock).setHardness(2.0F)
            .setResistance(10.0F)
            .setStepSound(soundTypePiston)
            .setBlockName("icystone");
        cobbedstone = new ModBlock(Material.rock).setHardness(2.0F)
            .setResistance(10.0F)
            .setStepSound(soundTypePiston)
            .setBlockName("cobbedstone");
    }

    public static void addUndergroundBiome(UndergroundBiome biome, BiomeDictionary.Type type, int rarity, int minXZSize,
        int xzVariation) {
        biomes.add(
            new UndergroundBiomeGenerator(
                biome,
                rarity,
                minXZSize,
                10,
                minXZSize,
                xzVariation,
                7,
                xzVariation,
                18 + 8,
                48 - 8,
                type));
    }

    public static void init() {
        BetterRecipes.init();
    }
}
