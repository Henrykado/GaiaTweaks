package henrykado.gaiablossom.mixin.late.thaumcraft;

import java.util.Random;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import henrykado.gaiablossom.common.world.WorldGenTaintTree;
import thaumcraft.common.lib.world.WorldGenBigMagicTree;
import thaumcraft.common.lib.world.biomes.BiomeGenTaint;

@Mixin(BiomeGenTaint.class)
public abstract class MixinBiomeGenTaint extends BiomeGenBase {

    public MixinBiomeGenTaint(int p_i1971_1_) {
        super(p_i1971_1_);
    }

    @Shadow
    protected WorldGenBigMagicTree bigTree;

    @Inject(method = "func_150567_a", at = @At("HEAD"), cancellable = true)
    public void newTree(Random par1Random, CallbackInfoReturnable<WorldGenAbstractTree> cir) {
        int r = par1Random.nextInt(8);
        if (r == 0) {
            cir.setReturnValue(this.bigTree);
        } else if (r <= 2) {
            cir.setReturnValue(super.func_150567_a(par1Random));
        } else {
            cir.setReturnValue(new WorldGenTaintTree(false));
        }
    }
}
