package henrykado.gaiablossom;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import henrykado.gaiablossom.common.item.ModItems;

public class CustomCreativeTab extends CreativeTabs {

    public static CustomCreativeTab INSTANCE = new CustomCreativeTab();

    public CustomCreativeTab() {
        super(GaiaBlossom.MODID);
    }

    @Override
    public Item getTabIconItem() {
        return ModItems.recall_potion;
    }
}
