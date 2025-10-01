package henrykado.gaiablossom.mixin.early.entity;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntitySheep.class)
public class MixinEntitySheep {

    /**
     * @author Henrykado
     * @reason balance
     */
    @Overwrite
    protected Item getDropItem() {
        return Items.string;
    }
}
