package henrykado.gaiablossom.mixin.late.aether;

import org.spongepowered.asm.mixin.Mixin;

import com.gildedgames.the_aether.world.gen.MapGenSilverDungeon;

@Mixin(MapGenSilverDungeon.class)
public class MixinGenSilverDungeon {
    // @Redirect(method = "canSpawnStructureAtCoords", at = @At(value = "INVOKE", target =
    // //"Lcom/gildedgames/the_aether/world/util/RandomTracker;testRandom(Ljava/util/Random;I)I"))
    // public int generateChanceRedirect(RandomTracker instance, Random random, int bound) {
    // return bound == 110 ? 160 : bound;
    // 150
    // }
}
