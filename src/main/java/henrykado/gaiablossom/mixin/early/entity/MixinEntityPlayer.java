package henrykado.gaiablossom.mixin.early.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

import henrykado.gaiablossom.Config;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {

    @Shadow
    public PlayerCapabilities capabilities;

    @Shadow
    public abstract FoodStats getFoodStats();

    public MixinEntityPlayer(World worldIn) {
        super(worldIn);
    }

    @ModifyArg(
        method = "jump()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;addExhaustion(F)V"))
    public float jumpExhaustionRedirect(float exhaustion) {
        return Config.enableStaminaSystem ? (exhaustion == 0.8F ? 1.2F /* 0.4 * 3 */ : 0.0F) : exhaustion;
    }

    @ModifyArg(
        method = "damageEntity(Lnet/minecraft/util/DamageSource;F)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;addExhaustion(F)V"))
    public float damageExhaustionRedirect(float exhaustion, @Local(argsOnly = true) float damage) {
        return Config.enableStaminaSystem ? (damage * 4) : damage;
    }

    @ModifyArg(
        method = "addMovementStat(DDD)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;addExhaustion(F)V"))
    public float sprintExhaustionRedirect(float exhaustion) {
        return Config.enableStaminaSystem ? (isSprinting() ? exhaustion * 3 : 0f) : exhaustion;
    }

    @Inject(method = "canEat(Z)Z", at = @At(value = "HEAD"), cancellable = true)
    private void canEatInject(boolean canEat, CallbackInfoReturnable<Boolean> cir) {
        boolean flag = true;
        if (capabilities.isCreativeMode || (!getFoodStats().needFood() && getHealth() >= getMaxHealth())) {
            flag = false;
        }
        if (Config.enableStaminaSystem) cir.setReturnValue(flag);
    }

    /*
     * @Inject(method = "setCurrentItemOrArmor(ILnet/minecraft/item/ItemStack;)V", at = @At(value = "HEAD"))
     * public void setCurrentItemOrArmorInject(int slotIn, ItemStack itemStackIn, CallbackInfo ci) {
     * if (itemStackIn != null && itemStackIn.getItem() instanceof ItemArmor
     * && EnchantmentHelper.getEnchantmentLevel(CommonProxy.growth.effectId, itemStackIn) > 0) {
     * EntityPlayer entityPlayer = (EntityPlayer) (Object) this;
     * GaiaPlayer.recalculateHealth(entityPlayer, GaiaPlayer.get(entityPlayer), itemStackIn, slotIn);
     * }
     * }
     */
}
