package henrykado.gaiablossom.mixin.late.aether.dun;

import com.gildedgames.the_aether.items.ItemsAether;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import com.gildedgames.the_aether.world.gen.components.ComponentGoldenDungeon;

import java.util.Random;

@Mixin(ComponentGoldenDungeon.class)
public class MixinGoldDungeon {
    /**
     * @author
     * @reason
     */
    private static ItemStack getGoldLoot(Random random) {
        int item = random.nextInt(10);
        switch (item) {
            case 0:
                return new ItemStack(ItemsAether.iron_bubble);
            case 1:
                return new ItemStack(ItemsAether.vampire_blade);
            case 2:
                return new ItemStack(ItemsAether.pig_slayer);
            case 3:
                if (random.nextBoolean()) {
                    return new ItemStack(ItemsAether.phoenix_helmet);
                }

                if (random.nextBoolean()) {
                    return new ItemStack(ItemsAether.phoenix_leggings);
                }

                if (random.nextBoolean()) {
                    return new ItemStack(ItemsAether.phoenix_chestplate);
                }
                break;
            case 4:
                return new ItemStack(ItemsAether.phoenix_gloves);
            case 5:
                return new ItemStack(ItemsAether.phoenix_boots);
            case 6:
                if (random.nextBoolean()) {
                    return new ItemStack(ItemsAether.gravitite_helmet);
                }

                if (random.nextBoolean()) {
                    return new ItemStack(ItemsAether.gravitite_leggings);
                }

                if (random.nextBoolean()) {
                    return new ItemStack(ItemsAether.gravitite_chestplate);
                }
                break;
            case 7:
                if (random.nextBoolean()) {
                    return new ItemStack(ItemsAether.gravitite_boots);
                }

                return new ItemStack(ItemsAether.gravitite_gloves);
            case 8:
                return new ItemStack(ItemsAether.chain_gloves);
        }

        return new ItemStack(ItemsAether.obsidian_chestplate);
    }
}
