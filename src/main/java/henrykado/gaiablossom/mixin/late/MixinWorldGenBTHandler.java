package henrykado.gaiablossom.mixin.late;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import atomicstryker.battletowers.common.WorldGenHandler;

@Mixin(WorldGenHandler.class)
public class MixinWorldGenBTHandler {

    @Inject(method = "generate", at = @At("HEAD"), cancellable = true, remap = false)
    public void generateInject(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider, CallbackInfo ci) {
        if (world.provider.dimensionId != 0) ci.cancel();
    }
}
