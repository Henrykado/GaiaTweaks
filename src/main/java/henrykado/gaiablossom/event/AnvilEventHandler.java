package henrykado.gaiablossom.event;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

// Code adapted from LumberWizard's Anvil Patch
public class AnvilEventHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.left;
        ItemStack right = event.right;
        ItemStack outputItem = left.copy();
        event.cost = 0;

        int repairCost = 0;
        Map enchantmentsOutputItem = EnchantmentHelper.getEnchantments(outputItem);

        boolean shouldIncreaseCost = false;
        boolean isRightItemEnchantedBook = right.getItem() == Items.enchanted_book;

        int materialCost = 0;

        if (left.isItemStackDamageable() && outputItem.getItem()
            .getIsRepairable(left, right)) {
            int amountRepairedByMat = Math.min(outputItem.getItemDamage(), outputItem.getMaxDamage() / 4);

            if (amountRepairedByMat <= 0) {
                return;
            }

            for (materialCost = 0; amountRepairedByMat > 0 && materialCost < right.stackSize; ++materialCost) {
                int newDamage = outputItem.getItemDamage() - amountRepairedByMat;
                outputItem.setItemDamage(newDamage);

                amountRepairedByMat = Math.min(outputItem.getItemDamage(), outputItem.getMaxDamage() / 4);
            }

            repairCost += enchantmentsOutputItem.size();
        } else {
            if (!isRightItemEnchantedBook
                && (outputItem.getItem() != right.getItem() || !outputItem.isItemStackDamageable())) {
                return;
            }

            if (outputItem.isItemStackDamageable() && !isRightItemEnchantedBook) {
                int leftDurability = left.getMaxDamage() - left.getItemDamage();
                int rightDurability = right.getMaxDamage() - right.getItemDamage();
                int newDurability = leftDurability + rightDurability + outputItem.getMaxDamage() * 12 / 100;
                int newDamage = outputItem.getMaxDamage() - newDurability;

                if (newDamage < 0) {
                    newDamage = 0;
                }

                if (newDamage < outputItem.getItemDamage()) // vanilla uses metadata here instead of damage.
                {
                    outputItem.setItemDamage(newDamage);
                    // addedRepairCost += 2;
                }
            }

            boolean rightItemHasIncompatibleEnchantments = false;

            Map enchantmentsToApply = EnchantmentHelper.getEnchantments(right);

            Enchantment enchantmentToApply;

            for (Object e : enchantmentsToApply.keySet()) {
                int enchantmentToApplyID = (Integer) e;
                enchantmentToApply = Enchantment.enchantmentsList[enchantmentToApplyID];

                int currentEnchantmentLevel = enchantmentsOutputItem.containsKey(enchantmentToApplyID)
                    ? (Integer) enchantmentsOutputItem.get(enchantmentToApplyID)
                    : 0;

                int newEnchantmentLevel = (Integer) enchantmentsToApply.get(enchantmentToApplyID);
                if (newEnchantmentLevel == currentEnchantmentLevel
                    && newEnchantmentLevel < enchantmentToApply.getMaxLevel()) newEnchantmentLevel++;

                newEnchantmentLevel = Math.max(newEnchantmentLevel, currentEnchantmentLevel);

                boolean canEnchantmentBeAppliedToLeftItem = enchantmentToApply.canApply(left);

                if (Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode
                    || left.getItem() == Items.enchanted_book
                    || left.getItem() == Items.stick) {
                    canEnchantmentBeAppliedToLeftItem = true;
                }

                for (Object e2 : enchantmentsOutputItem.keySet()) {
                    Enchantment enchantment = Enchantment.enchantmentsList[(Integer) e2];
                    if ((Integer) e2 != enchantmentToApplyID && (!enchantmentToApply.canApplyTogether(enchantment))
                        && enchantment.canApplyTogether(enchantmentToApply)) {
                        canEnchantmentBeAppliedToLeftItem = false;
                        ++repairCost; // pointless?
                    }
                }

                if (!canEnchantmentBeAppliedToLeftItem) {
                    rightItemHasIncompatibleEnchantments = true;
                } else {
                    if (newEnchantmentLevel > enchantmentToApply.getMaxLevel()) {
                        newEnchantmentLevel = enchantmentToApply.getMaxLevel();
                    }

                    enchantmentsOutputItem.put(enchantmentToApplyID, newEnchantmentLevel);
                    int repairCostAddedByEnchantmentRarity = 0;

                    switch (enchantmentToApply.getWeight()) {
                        case 1:
                            repairCostAddedByEnchantmentRarity = 8;
                            break;
                        case 2:
                            repairCostAddedByEnchantmentRarity = 4;
                            break;
                        case 5:
                            repairCostAddedByEnchantmentRarity = 2;
                            break;
                        case 10:
                            repairCostAddedByEnchantmentRarity = 1;
                        default:
                            break;
                    }

                    if (isRightItemEnchantedBook) {
                        repairCostAddedByEnchantmentRarity = Math.max(1, repairCostAddedByEnchantmentRarity / 2);
                    }

                    repairCost += repairCostAddedByEnchantmentRarity * newEnchantmentLevel;

                    if (left.stackSize > 1) {
                        return;
                    }
                }
            }

            if (rightItemHasIncompatibleEnchantments) {
                return;
            }

            int swappedRepairCost = 0;
            if (isRightItemEnchantedBook && left.getItem()
                .equals(Items.enchanted_book)) {
                for (Object e : enchantmentsOutputItem.keySet()) {
                    int enchantmentID = (Integer) e;
                    Enchantment enchantment = Enchantment.enchantmentsList[enchantmentID];

                    int currentEnchantmentLevel = enchantmentsToApply.containsKey(enchantmentID)
                        ? (Integer) enchantmentsOutputItem.get(enchantmentID)
                        : 0;

                    int newEnchantmentLevel = (Integer) enchantmentsOutputItem.get(enchantmentID);
                    if (newEnchantmentLevel == currentEnchantmentLevel
                        && newEnchantmentLevel < enchantment.getMaxLevel()) newEnchantmentLevel++;

                    newEnchantmentLevel = Math.max(newEnchantmentLevel, currentEnchantmentLevel);

                    if (newEnchantmentLevel > enchantment.getMaxLevel()) {
                        newEnchantmentLevel = enchantment.getMaxLevel();
                    }

                    int repairCostAddedByEnchantmentRarity = 0;

                    switch (enchantment.getWeight()) {
                        case 1:
                            repairCostAddedByEnchantmentRarity = 8;
                            break;
                        case 2:
                            repairCostAddedByEnchantmentRarity = 4;
                            break;
                        case 5:
                            repairCostAddedByEnchantmentRarity = 2;
                            break;
                        case 10:
                            repairCostAddedByEnchantmentRarity = 1;
                        default:
                            break;
                    }

                    repairCostAddedByEnchantmentRarity = Math.max(1, repairCostAddedByEnchantmentRarity / 2);

                    swappedRepairCost += repairCostAddedByEnchantmentRarity * newEnchantmentLevel;
                }
            }

            repairCost = Math.max(repairCost, swappedRepairCost);

            shouldIncreaseCost = true;
        }

        String repairedItemName = event.name;

        if (StringUtils.isBlank(repairedItemName)) {
            if (left.hasDisplayName()) {
                outputItem.func_135074_t();
            }
        } else if (!repairedItemName.equals(left.getDisplayName())) {
            outputItem.setStackDisplayName(repairedItemName);
        }

        if (isRightItemEnchantedBook && !outputItem.getItem()
            .isBookEnchantable(outputItem, right)) outputItem = null;

        if (repairCost < 0) {
            outputItem = null;
        } else if (repairCost > 30) {
            repairCost = 30;
        }

        if (outputItem.stackSize != 0) {
            if (shouldIncreaseCost) {
                int newCost = outputItem.getRepairCost();
                if (right.stackSize != 0 && newCost < right.getRepairCost()) {
                    newCost = right.getRepairCost();
                }
                outputItem.setRepairCost(newCost);
            }

            EnchantmentHelper.setEnchantments(enchantmentsOutputItem, outputItem);

            if (outputItem.isItemStackDamageable() && outputItem.getItem()
                .getIsRepairable(left, right)) {
                event.materialCost = materialCost;
            }

            event.cost = repairCost;
            event.output = outputItem;
        }
    }

    @SubscribeEvent
    public void onAnvilRepair(AnvilRepairEvent event) {
        event.breakChance = 0.0f;
    }
}
