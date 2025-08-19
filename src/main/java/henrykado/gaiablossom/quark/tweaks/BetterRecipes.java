package henrykado.gaiablossom.quark.tweaks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class BetterRecipes {

    public static void init() {
        List<ItemStack> newRecipes = new ArrayList<>();

        for (IRecipe recipe : CraftingManager.getInstance()
            .getRecipeList()) {
            if (recipe instanceof ShapedRecipes || recipe instanceof ShapedOreRecipe) {
                ItemStack output = recipe.getRecipeOutput();
                Block outputBlock = Block.getBlockFromItem(output.getItem());

                if (output.stackSize == 6 && outputBlock instanceof BlockSlab) {
                    Object[] recipeItems;
                    if (recipe instanceof ShapedRecipes) recipeItems = ((ShapedRecipes) recipe).recipeItems;
                    else recipeItems = ((ShapedOreRecipe) recipe).getInput();

                    ItemStack outStack = findResult(recipeItems, 3);

                    if (outStack != null) {
                        ItemStack outCopy = outStack.copy();
                        if (outCopy.getItemDamage() == OreDictionary.WILDCARD_VALUE) outCopy.setItemDamage(0);

                        ItemStack in = output.copy();
                        in.stackSize = 1;

                        newRecipes.add(outCopy);
                        newRecipes.add(in);
                    }
                } else if (output.stackSize == 4 && outputBlock instanceof BlockStairs) {
                    output.stackSize = 8;

                    Object[] recipeItems;
                    if (recipe instanceof ShapedRecipes) recipeItems = ((ShapedRecipes) recipe).recipeItems;
                    else recipeItems = ((ShapedOreRecipe) recipe).getInput();

                    ItemStack outStack = findResult(recipeItems, 6);

                    if (outStack != null) {
                        ItemStack outCopy = outStack.copy();
                        if (outCopy.getItemDamage() == OreDictionary.WILDCARD_VALUE) outCopy.setItemDamage(0);

                        outCopy.stackSize = 3;
                        ItemStack in = output.copy();
                        in.stackSize = 1;

                        newRecipes.add(outCopy);
                        newRecipes.add(in);
                    }
                }
            }
        }

        for (int i = 0; i < newRecipes.size(); i += 2) {
            Block block = Block.getBlockFromItem(
                newRecipes.get(i + 1)
                    .getItem());
            if (block instanceof BlockSlab) {
                CraftingManager.getInstance()
                    .addShapelessRecipe(newRecipes.get(i), newRecipes.get(i + 1), newRecipes.get(i + 1));
            } else { // BlockStairs
                CraftingManager.getInstance()
                    .addShapelessRecipe(
                        newRecipes.get(i),
                        newRecipes.get(i + 1),
                        newRecipes.get(i + 1),
                        newRecipes.get(i + 1),
                        newRecipes.get(i + 1));

                CraftingManager.getInstance()
                    .addRecipe(newRecipes.get(i + 1), "B  ", "BB ", 'B', newRecipes.get(i));
            }
        }
    }

    public static ItemStack findResult(Object[] ingredients, int expected) {
        ItemStack outStack = null;
        int inputItems = 0;

        for (Object ingredient : ingredients) {
            ItemStack recipeItem = null;
            if (ingredient instanceof ArrayList<?>) {
                Object[] matches = ((ArrayList<?>) ingredient).toArray();
                if (matches.length > 0 && matches[0] instanceof ItemStack match) recipeItem = match;
            }

            if (recipeItem != null) {
                if (outStack == null) outStack = recipeItem;

                if (outStack.getItem()
                    .equals(recipeItem.getItem())) inputItems++;
                else {
                    outStack = null;
                    break;
                }
            }
        }

        if (inputItems != expected) return null;

        return outStack;
    }
}
