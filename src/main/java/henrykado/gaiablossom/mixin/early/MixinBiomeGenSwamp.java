package henrykado.gaiablossom.mixin.early;

import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenSwamp;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeGenSwamp.class)
public abstract class MixinBiomeGenSwamp extends BiomeGenBase {

    public MixinBiomeGenSwamp(int p_i1971_1_) {
        super(p_i1971_1_);
    }

    @Inject(method = "<init>(I)V", at = @At("TAIL"))
    public void constructorInject(int id, CallbackInfo ci) {
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 10, 1, 1));
    }
}
