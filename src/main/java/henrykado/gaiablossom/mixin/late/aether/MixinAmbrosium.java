package henrykado.gaiablossom.mixin.late.aether;

import net.minecraft.item.Item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.gildedgames.the_aether.items.food.ItemAmbrosiumShard;

@Mixin(ItemAmbrosiumShard.class)
public abstract class MixinAmbrosium extends Item {

    @Inject(method = "<init>", at = @At("TAIL"))
    public void changeStackSize(CallbackInfo ci) {
        setMaxStackSize(8);
    }
}
