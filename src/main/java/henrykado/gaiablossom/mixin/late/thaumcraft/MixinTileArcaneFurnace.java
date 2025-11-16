package henrykado.gaiablossom.mixin.late.thaumcraft;

import java.util.Objects;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import thaumcraft.common.tiles.TileArcaneFurnace;

@Mixin(TileArcaneFurnace.class)
public class MixinTileArcaneFurnace {

    @Redirect(
        method = "updateEntity",
        at = @At(
            value = "INVOKE",
            target = "net/minecraft/item/crafting/FurnaceRecipes.getSmeltingResult(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"))
    public ItemStack smeltingResultRedirect(FurnaceRecipes instance, ItemStack itemStack) {
        if (Objects.equals(
            itemStack.getItem()
                .getUnlocalizedName(),
            "item.ingotIron")) {
            // return railcraft steel
        }

        return instance.getSmeltingResult(itemStack);
    }
}
