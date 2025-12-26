package henrykado.gaiablossom.mixin.late.thaumcraft;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import thaumcraft.common.lib.utils.EntityUtils;

@Mixin(value = EntityUtils.class, remap = false)
public class MixinThaumometer {

    @Redirect(
        method = {
            "getPointedEntity(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;DDFZ)Lnet/minecraft/entity/Entity;",
            "getPointedEntity(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;DDF)Lnet/minecraft/entity/Entity;" },
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;getEntitiesWithinAABBExcludingEntity(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;"))
    private static List ignoreItemFrameRedirect(World instance, Entity entity, AxisAlignedBB axisAlignedBB) {
        List<Entity> list = instance.getEntitiesWithinAABBExcludingEntity(entity, axisAlignedBB);
        list.removeIf(e -> e instanceof EntityItemFrame);
        return list;
    }
}
