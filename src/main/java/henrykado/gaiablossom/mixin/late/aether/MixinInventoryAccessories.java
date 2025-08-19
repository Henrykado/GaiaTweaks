package henrykado.gaiablossom.mixin.late.aether;

import static baubles.api.expanded.BaubleExpandedSlots.*;
import static baubles.api.expanded.BaubleExpandedSlots.getIndexesOfAssignedSlotsOfType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.gildedgames.the_aether.api.accessories.AccessoryType;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.inventory.InventoryAccessories;
import com.gildedgames.the_aether.items.accessories.ItemAccessory;
import com.gildedgames.the_aether.util.FilledList;

import baubles.api.BaublesApi;

@Mixin(InventoryAccessories.class)
public class MixinInventoryAccessories {

    @Unique
    private final AccessoryType[] ACC_TYPE_LOOKUP = { AccessoryType.PENDANT, AccessoryType.CAPE, AccessoryType.SHIELD,
        AccessoryType.MISC, AccessoryType.RING, AccessoryType.RING, AccessoryType.GLOVES, AccessoryType.MISC };

    @Shadow
    private IPlayerAether playerAether;

    @Redirect(
        method = { "func_70301_a", "func_70298_a", "damageWornStack" },
        at = @At(value = "INVOKE", target = "Lcom/gildedgames/the_aether/util/FilledList;get(I)Ljava/lang/Object;"),
        remap = false)
    private Object getStackRedirect(FilledList<ItemStack> stacks, int slot) {
        if (playerAether.getEntity() == null) return null;

        IInventory inv = BaublesApi.getBaubles((EntityPlayer) playerAether.getEntity());

        AccessoryType accType = ACC_TYPE_LOOKUP[slot];

        String baubleType = switch (accType) {
            case PENDANT -> amuletType;
            case CAPE, SHIELD -> bodyType;
            case RING, GLOVES -> ringType;
            default -> universalType; // MISC
        };

        boolean skipNext = slot == 5;

        int[] validSlots = getIndexesOfAssignedSlotsOfType(baubleType);
        for (int i : validSlots) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemAccessory
                && ((ItemAccessory) stack.getItem()).getType()
                    .equals(accType)) {
                if (skipNext) {
                    skipNext = false;
                    continue;
                }
                return inv.getStackInSlot(i);
            }
        }

        return null;
    }

    @Redirect(
        method = { "setAccessorySlot(Lnet/minecraft/item/ItemStack;)Z", "setInventorySlotContents" },
        at = @At(
            value = "INVOKE",
            target = "Lcom/gildedgames/the_aether/util/FilledList;set(ILjava/lang/Object;)Ljava/lang/Object;"),
        remap = false)
    private Object setStackRedirect(FilledList list, int slot, Object element) {
        if (playerAether.getEntity() == null) return null;

        IInventory inv = BaublesApi.getBaubles((EntityPlayer) playerAether.getEntity());

        String baubleType = switch (slot) {
            case 0 -> amuletType;
            case 1 -> bodyType; // cape
            case 2 -> bodyType; // shield
            case 4, 5 -> ringType;
            case 6 -> ringType; // gloves
            default -> universalType; // 3, 7, misc
        };

        ItemStack stack = (ItemStack) element;
        for (int i : getIndexesOfAssignedSlotsOfType(baubleType)) {
            if (inv.isItemValidForSlot(i, stack) && inv.getStackInSlot(i) == null) {
                inv.setInventorySlotContents(i, stack);
                return stack;
            }
        }

        return null;
    }
}
