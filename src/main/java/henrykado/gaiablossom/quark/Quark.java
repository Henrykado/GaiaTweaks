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
                8,
                32,
                12,
                32,
                12,
                6,
                12,
                30,
                54,
                BiomeDictionary.Type.SANDY));
        addUndergroundBiome(new UndergroundBiomeSlime(), BiomeDictionary.Type.SWAMP, 15, 16, 10);
        addUndergroundBiome(new UndergroundBiomeLush(), BiomeDictionary.Type.JUNGLE, 8, 22, 10);
        addUndergroundBiome(new UndergroundBiomeOvergrown(), BiomeDictionary.Type.CONIFEROUS, 40, 16, 10);
        addUndergroundBiome(new UndergroundBiomeSpiderNest(), BiomeDictionary.Type.PLAINS, 80, 16, 10);
        // addUndergroundBiome(new UndergroundBiomeIcy(), BiomeDictionary.Type.SNOWY, 15, 26, 10);
        biomes.add(
            new UndergroundBiomeGenerator(
                new UndergroundBiomeIcy(),
                12,
                32,
                10,
                32,
                12,
                4,
                12,
                38,
                58,
                BiomeDictionary.Type.SNOWY));

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
                6,
                xzVariation,
                26,
                54,
                type));
    }

    public static void init() {
        BetterRecipes.init();
    }
}
