package henrykado.gaiablossom.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class EnchantmentGrowth extends Enchantment {

    public EnchantmentGrowth(int p_i1926_1_, int p_i1926_2_) {
        super(p_i1926_1_, p_i1926_2_, EnumEnchantmentType.armor);
        Enchantment.addToBookList(this);
        this.setName("growth");
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }
}
