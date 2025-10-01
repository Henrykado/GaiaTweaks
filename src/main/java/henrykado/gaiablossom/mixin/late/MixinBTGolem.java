package henrykado.gaiablossom.mixin.late;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import atomicstryker.battletowers.common.AS_EntityGolem;
import cpw.mods.fml.common.FMLLog;

@Mixin(AS_EntityGolem.class)
public abstract class MixinBTGolem extends Entity {

    public MixinBTGolem(World worldIn) {
        super(worldIn);
    }

    @Shadow
    private int drops;

    @Inject(method = "updateGolemType", at = @At("TAIL"), remap = false)
    public void changeDropAmount(CallbackInfo ci) {
        FMLLog.info(String.valueOf(drops));
        drops = 2 + worldObj.rand.nextInt(2);
    }
}
