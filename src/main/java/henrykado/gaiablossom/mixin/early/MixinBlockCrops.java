package henrykado.gaiablossom.mixin.early;

import java.util.ArrayList;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockCrops.class, remap = false)
public abstract class MixinBlockCrops extends BlockBush {

    @Shadow
    protected abstract Item func_149866_i();

    @Inject(method = "getDrops", at = @At("HEAD"), cancellable = true)
    public void getDropsInject(World world, int x, int y, int z, int metadata, int fortune,
        CallbackInfoReturnable<ArrayList<ItemStack>> cir) {
        ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);

        if (metadata >= 7) {
            ret.add(new ItemStack(this.func_149866_i(), 1, 0));
            for (int i = 0; i < 1 + fortune; ++i) {
                if (world.rand.nextInt(28) <= metadata) {
                    ret.add(new ItemStack(this.func_149866_i(), 1, 0));
                }
            }
        }

        cir.setReturnValue(ret);
    }
}
