package henrykado.gaiablossom.mixin.late.primitive;

import net.daveyx0.primitivemobs.core.PrimitiveMobsEntitySpawning;
import net.minecraft.entity.EnumCreatureType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.gildedgames.the_aether.world.AetherWorld;

import cpw.mods.fml.common.registry.EntityRegistry;

@Mixin(PrimitiveMobsEntitySpawning.class)
public abstract class MixinPrimitiveMobsSpawning {

    @Redirect(
        method = "postInit()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/daveyx0/primitivemobs/core/PrimitiveMobsEntitySpawning;addRegularMobSpawns(Ljava/lang/Class;FII)V"),
        remap = false)
    private static void addRegularMobSpawningRedirect(Class entityClass, float weightedProb, int min, int max) {
        PrimitiveMobsEntitySpawning.addRegularMobSpawns(entityClass, weightedProb, min, max);

        EntityRegistry.removeSpawn(entityClass, EnumCreatureType.monster, AetherWorld.aether_biome);
    }
}
