package henrykado.gaiablossom.mixin.late.aether.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.gildedgames.the_aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;
import com.gildedgames.the_aether.entities.util.EntityBossMob;
import com.gildedgames.the_aether.items.ItemsAether;

@Mixin(EntityValkyrieQueen.class)
public abstract class MixinEntityValkyrieQueen extends EntityBossMob {

    public MixinEntityValkyrieQueen(World worldIn) {
        super(worldIn);
    }

    @Shadow
    public abstract boolean isBossReady();

    @Shadow
    public abstract void setBossReady(boolean isReady);

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
        if (!this.isBossReady()) {
            boolean flag = false;
            for (int slotId = 0; slotId < entityplayer.inventory.mainInventory.length; ++slotId) {
                ItemStack stack = entityplayer.inventory.mainInventory[slotId];
                if (stack != null && stack.getItem() == ItemsAether.victory_medal) {
                    flag = true;
                    if (stack.stackSize >= 10) {
                        entityplayer.inventory.mainInventory[slotId] = null;
                        setBossReady(true);
                        return true;
                    }
                }
            }
            if (flag) {
                chatItUp(entityplayer, StatCollector.translateToLocal("gui.valkyrie.dialog.player.lackmedals"));
            } else {
                chatItUp(entityplayer, StatCollector.translateToLocal("gui.queen.nomedals"));
            }
        }

        return super.interact(entityplayer);
    }
}
