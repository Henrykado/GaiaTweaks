package henrykado.gaiablossom.mixin.late.aether;

import org.spongepowered.asm.mixin.Mixin;

import com.gildedgames.the_aether.world.dungeon.BronzeDungeon;

@Mixin(BronzeDungeon.class)
public class MixinBronzeDungeon {
    /**
     * @return
     * @author
     * @reason
     */
    /*
     * @Overwrite
     * public static ItemStack getBronzeLoot(Random random) {
     * int item = random.nextInt(7);
     * switch (item) {
     * case 0:
     * return new ItemStack(ItemsAether.gummy_swet, random.nextInt(7) + 1, random.nextInt(2));
     * case 1:
     * return new ItemStack(ItemsAether.notch_hammer);
     * case 2:
     * return new ItemStack(ItemsAether.lightning_knife, random.nextInt(20) + 1);
     * case 3:
     * return new ItemStack(ItemsAether.agility_cape);
     * case 4:
     * return new ItemStack(ItemsAether.sentry_boots);
     * case 5:
     * return new ItemStack(ItemsAether.repulsion_shield);
     * }
     * return new ItemStack(ItemsAether.cloud_staff);
     * }
     */

    /*
     * @Redirect(method = "generateBossRoom", at = @At(value = "INVOKE", target =
     * "Lcom/gildedgames/the_aether/world/util/RandomTracker;testRandom(Ljava/util/Random;I)I"))
     * public int generateChanceRedirect(RandomTracker instance, Random random, int bound) {
     * return bound == 15 ? 25 : bound;
     * // 40
     * }
     */
}
