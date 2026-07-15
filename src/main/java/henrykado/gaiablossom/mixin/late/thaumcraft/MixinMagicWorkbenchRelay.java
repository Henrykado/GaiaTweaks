package henrykado.gaiablossom.mixin.late.thaumcraft;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.tiles.TileMagicWorkbenchCharger;
import thaumcraft.common.tiles.TileVisRelay;

@Mixin(TileMagicWorkbenchCharger.class)
public class MixinMagicWorkbenchRelay extends TileVisRelay {

    @Redirect(
        method = "updateEntity",
        at = @At(
            value = "INVOKE",
            target = "Lthaumcraft/common/items/wands/ItemWandCasting;addRealVis(Lnet/minecraft/item/ItemStack;Lthaumcraft/api/aspects/Aspect;IZ)I"))
    public int updateEntityInject(ItemWandCasting instance, ItemStack is, Aspect aspect, int amount, boolean doit) {
        if (worldObj.getWorldTime() % 5 == 0) {
            return instance.addRealVis(is, aspect, amount / 5, doit);
        }
        return 0;
    }
}
