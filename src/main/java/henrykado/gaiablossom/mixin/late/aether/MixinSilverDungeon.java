package henrykado.gaiablossom.mixin.late.aether;

import org.spongepowered.asm.mixin.Mixin;

import com.gildedgames.the_aether.world.gen.components.ComponentSilverDungeon;

@Mixin(ComponentSilverDungeon.class)
public class MixinSilverDungeon {
    /**
     * @author
     * @reason
     */
    /*
     * @Overwrite
     * public static ItemStack getSilverLoot(Random random) {
     * int item = random.nextInt(14);
     * switch (item) {
     * case 0:
     * return new ItemStack(ItemsAether.gummy_swet, random.nextInt(15) + 1, random.nextInt(2));
     * case 1:
     * return new ItemStack(ItemsAether.lightning_sword);
     * case 2:
     * if (random.nextBoolean()) {
     * return new ItemStack(ItemsAether.valkyrie_axe);
     * }
     * if (random.nextBoolean()) {
     * return new ItemStack(ItemsAether.valkyrie_shovel);
     * }
     * if (random.nextBoolean()) {
     * return new ItemStack(ItemsAether.valkyrie_pickaxe);
     * }
     * break;
     * case 3:
     * return new ItemStack(ItemsAether.holy_sword);
     * case 4:
     * return new ItemStack(ItemsAether.valkyrie_helmet);
     * case 5:
     * return new ItemStack(ItemsAether.regeneration_stone);
     * case 6:
     * if (random.nextBoolean()) {
     * return new ItemStack(ItemsAether.neptune_helmet);
     * }
     * if (random.nextBoolean()) {
     * return new ItemStack(ItemsAether.neptune_leggings);
     * }
     * if (random.nextBoolean()) {
     * return new ItemStack(ItemsAether.neptune_chestplate);
     * }
     * break;
     * case 7:
     * if (random.nextBoolean()) {
     * return new ItemStack(ItemsAether.neptune_boots);
     * }
     * return new ItemStack(ItemsAether.neptune_gloves);
     * case 8:
     * if (random.nextBoolean()) {
     * return new ItemStack(ItemsAether.valkyrie_boots);
     * }
     * return new ItemStack(ItemsAether.valkyrie_gloves);
     * case 9:
     * return new ItemStack(ItemsAether.valkyrie_leggings);
     * case 10:
     * if (random.nextBoolean()) {
     * return new ItemStack(ItemsAether.valkyrie_chestplate);
     * }
     * case 11:
     * if (random.nextBoolean()) {
     * return new ItemStack(ItemsAether.valkyrie_boots);
     * }
     * return new ItemStack(ItemsAether.valkyrie_gloves);
     * case 12:
     * if (AetherConfig.valkyrieCapeEnabled()) {
     * return new ItemStack(ItemsAether.valkyrie_cape);
     * }
     * case 13:
     * if (AetherConfig.goldenFeatherEnabled()) {
     * return new ItemStack(ItemsAether.golden_feather);
     * }
     * }
     * return new ItemStack(ItemsAether.invisibility_cape);
     * }
     */
}
