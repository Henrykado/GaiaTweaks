package henrykado.gaiablossom.mixin.early.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

import henrykado.gaiablossom.CommonProxy;
import henrykado.gaiablossom.common.entity.eep.GaiaPlayer;

@Mixin(EntityPlayer.class)
public class MixinEntityPlayer {

    @ModifyArg(
        method = "jump()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;addExhaustion(F)V"))
    public float jumpExhaustionRedirect(float exhaustion) {
        return exhaustion == 0.8F ? 1.2F /* 0.4 * 3 */ : 0.0F;
    }

    @ModifyArg(
        method = "damageEntity(Lnet/minecraft/util/DamageSource;F)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;addExhaustion(F)V"))
    public float damageExhaustionRedirect(float exhaustion, @Local(argsOnly = true) float damage) {
        return damage * 4;
    }

    @ModifyArg(
        method = "addMovementStat(DDD)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;addExhaustion(F)V"))
    public float sprintExhaustionRedirect(float exhaustion) {
        EntityPlayer self = (EntityPlayer) (Object) this;
        return self.isSprinting() ? exhaustion * 3 : 0f;
    }

    @Inject(method = "canEat(Z)Z", at = @At(value = "HEAD"), cancellable = true)
    private void canEatInject(boolean canEat, CallbackInfoReturnable<Boolean> cir) {
        EntityPlayer player = (EntityPlayer) (Object) this;
        boolean flag = true;
        if (player.capabilities.isCreativeMode || (!player.getFoodStats()
            .needFood() && player.getHealth() >= player.getMaxHealth())) {
            flag = false;
        }
        cir.setReturnValue(flag);
    }

    @Inject(method = "setCurrentItemOrArmor(ILnet/minecraft/item/ItemStack;)V", at = @At(value = "HEAD"))
    public void setCurrentItemOrArmorInject(int slotIn, ItemStack itemStackIn, CallbackInfo ci) {
        if (itemStackIn != null && itemStackIn.getItem() instanceof ItemArmor
            && EnchantmentHelper.getEnchantmentLevel(CommonProxy.growth.effectId, itemStackIn) > 0) {
            EntityPlayer entityPlayer = (EntityPlayer) (Object) this;
            GaiaPlayer.recalculateHealth(entityPlayer, GaiaPlayer.get(entityPlayer), itemStackIn, slotIn);
        }
    }
}
