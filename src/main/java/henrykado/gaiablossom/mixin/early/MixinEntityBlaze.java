package henrykado.gaiablossom.mixin.early;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityBlaze.class)
public class MixinEntityBlaze extends EntityMob {

    public MixinEntityBlaze(World p_i1738_1_) {
        super(p_i1738_1_);
    }

    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, 12.0D);
        return entityplayer != null && this.canEntityBeSeen(entityplayer) ? entityplayer : null;
    }
}
