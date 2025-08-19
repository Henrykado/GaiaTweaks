package henrykado.gaiablossom.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import baubles.api.BaublesApi;
import baubles.api.expanded.BaubleExpandedSlots;

public class BaublesUtils {

    public static ItemStack getStackInFirstSlotOfType(EntityPlayer player, String type) {
        if (BaubleExpandedSlots.getIndexesOfAssignedSlotsOfType(type).length > 0) {
            return BaublesApi.getBaubles(player)
                .getStackInSlot(BaubleExpandedSlots.getIndexesOfAssignedSlotsOfType(type)[0]);
        }
        return null;
    }
}
