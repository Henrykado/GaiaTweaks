package henrykado.gaiablossom.mixin.late.thaumcraft;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import baubles.api.expanded.BaubleExpandedSlots;
import henrykado.gaiablossom.util.BaublesUtils;
import thaumcraft.api.IGoggles;
import thaumcraft.client.fx.beams.FXBeamPower;

@Mixin(FXBeamPower.class)
public class MixinFXBeamPower {

    @Redirect(
        method = { "func_70539_a", "renderFlare" },
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/InventoryPlayer;armorItemInSlot(I)Lnet/minecraft/item/ItemStack;"),
        remap = false)
    public ItemStack renderRedirect(InventoryPlayer instance, int slot) {
        ItemStack helmetStack = instance.armorItemInSlot(slot);
        if (helmetStack != null && helmetStack.getItem() instanceof IGoggles) {
            return instance.armorItemInSlot(slot);
        } else {
            ItemStack stack = BaublesUtils.getStackInFirstSlotOfType(instance.player, BaubleExpandedSlots.headType);
            if (stack != null && stack.getItem() instanceof IGoggles) {
                return stack;
            }
        }
        return null;
    }
}
