package henrykado.gaiablossom.mixin.late.aether.dungeon;

import java.util.Random;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.world.dungeon.BronzeDungeon;

import vazkii.botania.common.item.ModItems;

@Mixin(BronzeDungeon.class)
public class MixinBronzeDungeon {

    /**
     * @return
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public static ItemStack getBronzeLoot(Random random) {
        int item = random.nextInt(10);
        switch (item) {
            case 0:
                return new ItemStack(ItemsAether.gummy_swet, random.nextInt(7) + 1, random.nextInt(2));
            case 1:
                return new ItemStack(ModItems.rune, 2, random.nextInt(2) + 1);
            // return new ItemStack(ItemsAether.phoenix_bow);
            case 2:
                return new ItemStack(ModItems.rune, 3, random.nextInt(2) + 1);
            // return new ItemStack(ItemsAether.flaming_sword);
            case 3:
                return new ItemStack(ItemsAether.notch_hammer);
            case 4:
                return new ItemStack(ItemsAether.lightning_knife, random.nextInt(20) + 1);
            case 5:
                // return new ItemStack(ItemsAether.valkyrie_lance);
                return new ItemStack(ItemsAether.pig_slayer);
            case 6:
                return new ItemStack(ItemsAether.agility_cape);
            case 7:
                return new ItemStack(ItemsAether.sentry_boots);
            case 8:
                return new ItemStack(ItemsAether.repulsion_shield);
            default:
                // if (random.nextInt(3) == 0) {
                // return new ItemStack(ModItems.cloudPendant);
                // } else {
                return new ItemStack(ItemsAether.cloud_staff);
            // }
        }
    }

    /*
     * @Redirect(method = "generateBossRoom", at = @At(value = "INVOKE", target =
     * "Lcom/gildedgames/the_aether/world/util/RandomTracker;testRandom(Ljava/util/Random;I)I"))
     * public int generateChanceRedirect(RandomTracker instance, Random random, int bound) {
     * return bound == 15 ? 25 : bound;
     * // 40
     * }
     */
}
