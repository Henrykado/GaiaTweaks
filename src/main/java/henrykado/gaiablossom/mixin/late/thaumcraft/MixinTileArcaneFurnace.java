package henrykado.gaiablossom.mixin.late.thaumcraft;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import mods.railcraft.common.items.RailcraftItem;
import thaumcraft.common.tiles.TileArcaneFurnace;

@Mixin(TileArcaneFurnace.class)
public class MixinTileArcaneFurnace {

    @Redirect(
        method = "func_145845_h",
        at = @At(
            value = "INVOKE",
            target = "net/minecraft/item/crafting/FurnaceRecipes.getSmeltingResult(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"))
    public ItemStack smeltingResultRedirect(FurnaceRecipes instance, ItemStack itemStack) {
        if (itemStack.getItem() == Items.iron_ingot) {
            return new ItemStack(RailcraftItem.ingot.item(), 1, 0);
        }

        return instance.getSmeltingResult(itemStack);
    }

    @Redirect(
        method = "canSmelt",
        at = @At(
            value = "INVOKE",
            target = "net/minecraft/item/crafting/FurnaceRecipes.getSmeltingResult(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"),
        remap = false)
    public ItemStack canSmeltRedirect(FurnaceRecipes instance, ItemStack itemStack) {
        if (itemStack.getItem() == Items.iron_ingot) {
            return new ItemStack(RailcraftItem.ingot.item(), 1, 0);
        }

        return instance.getSmeltingResult(itemStack);
    }
}
