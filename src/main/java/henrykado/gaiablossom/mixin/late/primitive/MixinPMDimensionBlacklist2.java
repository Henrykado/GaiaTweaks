package henrykado.gaiablossom.mixin.late.primitive;

import net.daveyx0.primitivemobs.entity.monster.EntityDSkeletonWarrior;
import net.daveyx0.primitivemobs.entity.monster.EntityFestiveCreeper;
import net.daveyx0.primitivemobs.entity.monster.EntityHauntedTool;
import net.daveyx0.primitivemobs.entity.monster.EntityMotherSpider;
import net.daveyx0.primitivemobs.entity.monster.EntitySupportCreeper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;

import com.gildedgames.the_aether.AetherConfig;

import twilightforest.TwilightForestMod;

@Mixin(
    value = { EntityFestiveCreeper.class, EntitySupportCreeper.class, EntityHauntedTool.class,
        EntityDSkeletonWarrior.class, EntityMotherSpider.class })
public abstract class MixinPMDimensionBlacklist2 extends EntityLiving {

    public MixinPMDimensionBlacklist2(World p_i1595_1_) {
        super(p_i1595_1_);
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.dimension == AetherConfig.getAetherDimensionID() || this.dimension == TwilightForestMod.dimensionID) {
            return false;
        }

        return super.getCanSpawnHere();
    }
}
