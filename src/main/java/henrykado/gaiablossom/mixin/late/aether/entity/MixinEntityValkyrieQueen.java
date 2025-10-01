package henrykado.gaiablossom.mixin.late.aether.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.gildedgames.the_aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;
import com.gildedgames.the_aether.entities.util.EntityBossMob;

@Mixin(EntityValkyrieQueen.class)
public abstract class MixinEntityValkyrieQueen extends EntityBossMob {

    public MixinEntityValkyrieQueen(World worldIn) {
        super(worldIn);
    }

    @Shadow
    public abstract boolean isBossReady();

    @Shadow
    private void chatItUp(EntityPlayer player, String s) {}

    // func_70085_c
    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public boolean interact(EntityPlayer entityplayer) {
        this.faceEntity(entityplayer, 180.0F, 180.0F);
        if (!this.isBossReady() && this.worldObj.isRemote) {
            this.chatItUp(entityplayer, StatCollector.translateToLocal("valkyrie_queen.notready"));
            return true;
        }

        return super.interact(entityplayer);
    }
}
