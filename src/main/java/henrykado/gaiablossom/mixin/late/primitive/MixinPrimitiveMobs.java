package henrykado.gaiablossom.mixin.late.primitive;

import net.daveyx0.primitivemobs.common.PrimitiveMobs;
import net.minecraftforge.common.AchievementPage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PrimitiveMobs.class)
public class MixinPrimitiveMobs {

    @Redirect(
        method = "Init",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/common/AchievementPage;registerAchievementPage(Lnet/minecraftforge/common/AchievementPage;)V"),
        remap = false)
    public void registerAchievementPageRedirect(AchievementPage achievementPage) {
        return;
    }
}
