package henrykado.gaiablossom.mixin.late.botania;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import vazkii.botania.common.achievement.AchievementMod;
import vazkii.botania.common.achievement.ModAchievements;
import vazkii.botania.common.item.ModItems;

@Mixin(value = ModAchievements.class, remap = false)
public class MixinAchievements {

    @Unique
    private static Achievement blossomOfGaia$floralFertilizer;

    @Inject(method = "init()V", at = @At("HEAD"))
    private static void newAchievement(CallbackInfo ci) {
        blossomOfGaia$floralFertilizer = new AchievementMod(
            "floralFertilizer",
            -1,
            4,
            new ItemStack(ModItems.fertilizer),
            null);
    }

    @ModifyConstant(method = "init()V", constant = @Constant(nullValue = true, ordinal = 0))
    private static Object changeParent(Object constant) {
        return blossomOfGaia$floralFertilizer;
    }
}
