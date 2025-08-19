package henrykado.gaiablossom.event;

import static henrykado.gaiablossom.quark.Quark.biomes;
import static vazkii.botania.api.BotaniaAPI.*;
import static vazkii.botania.common.crafting.ModPetalRecipes.*;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.world.WorldEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import henrykado.gaiablossom.quark.world.UndergroundBiomeGenerator;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.Botania;
import vazkii.botania.common.crafting.ModCraftingRecipes;
import vazkii.botania.common.crafting.ModManaInfusionRecipes;
import vazkii.botania.common.crafting.ModPetalRecipes;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lexicon.ALexiconEntry;
import vazkii.botania.common.lexicon.BLexiconEntry;
import vazkii.botania.common.lexicon.LexiconData;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageManaInfusionRecipe;
import vazkii.botania.common.lexicon.page.PagePetalRecipe;
import vazkii.botania.common.lexicon.page.PageText;
import vazkii.botania.common.lib.LibLexicon;
import vazkii.botania.common.world.WorldTypeSkyblock;

public class WorldEventHandler {

    @SubscribeEvent
    public void onOreGenerate(OreGenEvent.GenerateMinable event) {
        if (event.type == OreGenEvent.GenerateMinable.EventType.DIRT) {
            World world = event.world;
            int chunkX = event.worldX;
            int chunkZ = event.worldZ;
            // GaiaBlossom.LOG.info("chunkX: {}", chunkX);
            // GaiaBlossom.LOG.info("chunkZ: {}", chunkZ);

            Chunk chunk = world.getChunkFromBlockCoords(chunkX, chunkZ);

            for (UndergroundBiomeGenerator gen : biomes) {
                gen.generate(chunk.xPosition, chunk.zPosition, world);
            }
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        Botania.gardenOfGlassLoaded = WorldTypeSkyblock.isWorldSkyblock(event.world);

        LexiconData.orechid = Botania.gardenOfGlassLoaded ? new BLexiconEntry("orechid", categoryFunctionalFlowers)
            : new ALexiconEntry("orechid", categoryFunctionalFlowers);
        LexiconData.orechid
            .setLexiconPages(new PageText("0"), new PagePetalRecipe<>("1", ModPetalRecipes.orechidRecipe));

        LexiconData.cocoon = Botania.gardenOfGlassLoaded ? new BLexiconEntry("cocoon", categoryDevices)
            : new ALexiconEntry("cocoon", categoryDevices);
        LexiconData.cocoon.setLexiconPages(
            new PageText("0"),
            new PageText("1"),
            new PageCraftingRecipe("2", ModCraftingRecipes.recipeCocoon));

        BotaniaAPI.petalRecipes.remove(orechidRecipe);

        if (Botania.gardenOfGlassLoaded) {
            ModManaInfusionRecipes.sugarCaneRecipe = BotaniaAPI
                .registerManaInfusionRecipe(new ItemStack(Items.reeds), new ItemStack(Blocks.hay_block), 2000);

            orechidRecipe = BotaniaAPI.registerPetalRecipe(
                ItemBlockSpecialFlower.ofType("orechid"),
                gray,
                gray,
                yellow,
                yellow,
                green,
                green,
                red,
                red);

            LexiconData.gardenOfGlass = new BLexiconEntry(LibLexicon.BASICS_GARDEN_OF_GLASS, categoryBasics);
            LexiconData.gardenOfGlass.setLexiconPages(
                new PageText("0"),
                new PageText("1"),
                new PageText("2"),
                new PageCraftingRecipe("3", ModCraftingRecipes.recipeRootToSapling),
                new PageCraftingRecipe("4", ModCraftingRecipes.recipeRootToFertilizer),
                new PageCraftingRecipe("5", ModCraftingRecipes.recipePebbleCobblestone),
                new PageText("6"),
                new PageManaInfusionRecipe("7", ModManaInfusionRecipes.sugarCaneRecipe),
                new PageCraftingRecipe("8", ModCraftingRecipes.recipeMagmaToSlimeball),
                new PageText("9"),
                new PageText("11"),
                new PageCraftingRecipe("12", ModCraftingRecipes.recipeEndPortal));
            LexiconData.gardenOfGlass.setPriority()
                .setIcon(new ItemStack(ModItems.manaResource, 1, 20));
            LexiconData.orechid.setPriority();
        } else {
            if (ModManaInfusionRecipes.sugarCaneRecipe != null) {
                manaInfusionRecipes.remove(ModManaInfusionRecipes.sugarCaneRecipe);
            }

            orechidRecipe = BotaniaAPI.registerPetalRecipe(
                ItemBlockSpecialFlower.ofType("orechid"),
                gray,
                gray,
                yellow,
                green,
                red,
                runePride,
                runeGreed,
                "redstoneRoot",
                "elvenPixieDust");

            ModManaInfusionRecipes.sugarCaneRecipe = null;
            LexiconData.gardenOfGlass = null;
        }

        /*
         * List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
         * for (IRecipe recipe : recipeList) {
         * if (recipe.getRecipeOutput().get) {
         * }
         * }
         */
    }
}
