package henrykado.gaiablossom.mixin.late.primitive;

import net.daveyx0.primitivemobs.client.PrimitiveMobsAchievementPage;
import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PrimitiveMobs.class)
public class MixinPrimitiveMobs {

    @Unique
    Achievement[] blossom_of_Gaia$achievements = new Achievement[] { PrimitiveMobsAchievementPage.blazingJugger,
        PrimitiveMobsAchievementPage.camouflageDye, PrimitiveMobsAchievementPage.camouflageArmor,
        PrimitiveMobsAchievementPage.camouflageArmor, PrimitiveMobsAchievementPage.chameleonFriend,
        PrimitiveMobsAchievementPage.hauntedDiamond, PrimitiveMobsAchievementPage.hauntedPrecious,
        PrimitiveMobsAchievementPage.lostMinerHelp, PrimitiveMobsAchievementPage.mimicDungeon,
        PrimitiveMobsAchievementPage.mimicFake, PrimitiveMobsAchievementPage.redCreepSurvive,
        PrimitiveMobsAchievementPage.specialColor1, PrimitiveMobsAchievementPage.specialColor2,
        PrimitiveMobsAchievementPage.specialColor3, PrimitiveMobsAchievementPage.spiderAttack,
        PrimitiveMobsAchievementPage.spiderGrown, PrimitiveMobsAchievementPage.spiderOwner,
        PrimitiveMobsAchievementPage.spiderStealer, PrimitiveMobsAchievementPage.treasureSlimeFriend,
        PrimitiveMobsAchievementPage.treasureSlimeWhite };

    @Redirect(
        method = "Init",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/common/AchievementPage;registerAchievementPage(Lnet/minecraftforge/common/AchievementPage;)V"),
        remap = false)
    public void registerAchievementPageRedirect(AchievementPage achievementPage) {
        /*
         * for (Achievement achievement : blossom_of_Gaia$achievements) {
         * AchievementList.achievementList.remove(achievement);
         * }
         */
    }
}
