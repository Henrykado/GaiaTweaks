package henrykado.gaiablossom.mixin.early;

import java.util.List;

import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.world.biome.BiomeGenBase;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeGenBase.class)
public class MixinBiomeGenBase {
    @Shadow
    protected List<net.minecraft.world.biome.BiomeGenBase.SpawnListEntry> spawnableMonsterList;

    @Shadow
    protected List<BiomeGenBase.SpawnListEntry> spawnableWaterCreatureList;

    @Inject(method = "<init>(IZ)V", at = @At("TAIL"))
    public void constructorInject(int id, boolean register, CallbackInfo ci) {
        spawnableWaterCreatureList.clear();
        spawnableWaterCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySquid.class, 16, 1, 4));
        spawnableMonsterList.remove(6); // Witch
    }
}
