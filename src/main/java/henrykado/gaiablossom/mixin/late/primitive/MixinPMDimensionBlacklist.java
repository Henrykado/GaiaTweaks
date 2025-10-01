package henrykado.gaiablossom.mixin.late.primitive;

import net.daveyx0.primitivemobs.entity.monster.EntityDEnchantedBook;
import net.daveyx0.primitivemobs.entity.monster.EntityTreasureSlime;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.gildedgames.the_aether.AetherConfig;

import twilightforest.TwilightForestMod;

@Mixin(value = { EntityTreasureSlime.class, EntityDEnchantedBook.class })
public abstract class MixinPMDimensionBlacklist extends EntityLiving {

    public MixinPMDimensionBlacklist(World p_i1595_1_) {
        super(p_i1595_1_);
    }

    @Inject(method = "getCanSpawnHere", at = @At("HEAD"), cancellable = true)
    public void getCanSpawnHereInject(CallbackInfoReturnable<Boolean> cir) {
        if (this.dimension == AetherConfig.getAetherDimensionID() || this.dimension == TwilightForestMod.dimensionID) {
            cir.setReturnValue(false);
        }
    }
}
