package henrykado.gaiablossom.mixin.late.aether.dun;

import java.util.Random;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.world.gen.components.ComponentSilverDungeon;

import henrykado.gaiablossom.common.item.ModItems;

@Mixin(ComponentSilverDungeon.class)
public class MixinSilverDungeon {

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public static ItemStack getSilverLoot(Random random) {
        int item = random.nextInt(13);
        switch (item) {
            case 0:
                return new ItemStack(ItemsAether.gummy_swet, random.nextInt(15) + 1, random.nextInt(2));
            case 1:
                if (random.nextBoolean()) {
                    return new ItemStack(ItemsAether.valkyrie_axe);
                }

                if (random.nextBoolean()) {
                    return new ItemStack(ItemsAether.valkyrie_shovel);
                }

                if (random.nextBoolean()) {
                    return new ItemStack(ItemsAether.valkyrie_pickaxe);
                }
                break;
            case 2:
                return new ItemStack(ItemsAether.valkyrie_helmet);
            case 3:
                return new ItemStack(ItemsAether.regeneration_stone);
            case 4:
                return new ItemStack(ModItems.dodgeRing);
            case 5:
                return new ItemStack(ModItems.invisibilityCloak);
            case 6:
                if (random.nextBoolean()) {
                    return new ItemStack(ItemsAether.valkyrie_boots);
                }

                return new ItemStack(ItemsAether.valkyrie_gloves);
            case 7:
                return new ItemStack(ItemsAether.valkyrie_leggings);
            case 8:
                if (random.nextBoolean()) {
                    return new ItemStack(ItemsAether.valkyrie_chestplate);
                }
            case 9:
                if (random.nextBoolean()) {
                    return new ItemStack(ItemsAether.valkyrie_boots);
                }

                return new ItemStack(ItemsAether.valkyrie_gloves);
            case 10:
                if (AetherConfig.valkyrieCapeEnabled()) {
                    return new ItemStack(ItemsAether.valkyrie_cape);
                }
            case 11:
                if (AetherConfig.goldenFeatherEnabled()) {
                    return new ItemStack(ItemsAether.golden_feather);
                }
        }
        return new ItemStack(ItemsAether.holy_sword);
    }
}
