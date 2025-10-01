package henrykado.gaiablossom.mixin.late.thaumcraft;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.gildedgames.the_aether.AetherConfig;

import thaumcraft.common.lib.world.WorldGenGreatwoodTrees;
import twilightforest.TwilightForestMod;

@Mixin(WorldGenGreatwoodTrees.class)
public abstract class MixinWorldGenGreatwood extends WorldGenerator {

    @Shadow
    private World worldObj;

    @Inject(method = "validTreeLocation", at = @At("HEAD"), remap = false)
    public void validTreeLocationInject(CallbackInfoReturnable<Boolean> cir) {
        if (worldObj.provider.dimensionId == AetherConfig.getAetherDimensionID() || worldObj.provider.dimensionId == TwilightForestMod.dimensionID) cir.setReturnValue(false);
    }
}
