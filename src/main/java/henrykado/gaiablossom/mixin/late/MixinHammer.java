package henrykado.gaiablossom.mixin.late;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vapourdrive.hammerz.items.Hammer;

@Mixin(value = Hammer.class, remap = true)
public abstract class MixinHammer {

    @Shadow
    @SideOnly(Side.CLIENT)
    public abstract void addPermanentInfo(ItemStack stack, EntityPlayer player, List list, boolean useExtraInformation);

    @Shadow
    public abstract boolean onItemShiftUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z,
        int side, float floatx, float floaty, float floatz);

    @Inject(method = "addInformation", at = @At("HEAD"), cancellable = true)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean useExtraInformation,
        CallbackInfo ci) {
        addPermanentInfo(stack, player, list, useExtraInformation);
        ci.cancel();
    }

    @Inject(method = "onItemUse", at = @At("HEAD"), cancellable = true)
    public void onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float floatx, float floaty, float floatz, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(onItemShiftUse(stack, player, world, x, y, z, side, floatx, floaty, floatz));
    }
}
