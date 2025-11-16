package henrykado.gaiablossom.mixin.late.thaumcraft;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import thaumcraft.common.lib.utils.EntityUtils;

@Mixin(value = EntityUtils.class, remap = false)
public class MixinThaumometer {

    /**
     * @author
     * @reason
     */
    @Overwrite
    public static Entity getPointedEntity(World world, Entity entityplayer, double minrange, double range,
        float padding, boolean nonCollide) {
        Entity pointedEntity = null;
        double d = range;
        Vec3 vec3d = Vec3.createVectorHelper(
            entityplayer.posX,
            entityplayer.posY + (double) entityplayer.getEyeHeight(),
            entityplayer.posZ);
        Vec3 vec3d1 = entityplayer.getLookVec();
        Vec3 vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
        float f1 = padding;
        List list = world.getEntitiesWithinAABBExcludingEntity(
            entityplayer,
            entityplayer.boundingBox.addCoord(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d)
                .expand((double) f1, (double) f1, (double) f1));
        double d2 = 0.0;

        for (int i = 0; i < list.size(); ++i) {
            Entity entity = (Entity) list.get(i);
            if (!((double) entity.getDistanceToEntity(entityplayer) < minrange)
                && (entity.canBeCollidedWith() || nonCollide)
                && world.func_147447_a(
                    Vec3.createVectorHelper(
                        entityplayer.posX,
                        entityplayer.posY + (double) entityplayer.getEyeHeight(),
                        entityplayer.posZ),
                    Vec3.createVectorHelper(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ),
                    false,
                    true,
                    false) == null
                && !(entity instanceof EntityItemFrame)) {
                float f2 = Math.max(0.8F, entity.getCollisionBorderSize());
                AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double) f2, (double) f2, (double) f2);
                if (entity instanceof EntityItemFrame) axisalignedbb = entity.boundingBox;
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3d, vec3d2);
                if (axisalignedbb.isVecInside(vec3d)) {
                    if (0.0 < d2 || d2 == 0.0) {
                        pointedEntity = entity;
                        d2 = 0.0;
                    }
                } else if (movingobjectposition != null) {
                    double d3 = vec3d.distanceTo(movingobjectposition.hitVec);
                    if (d3 < d2 || d2 == 0.0) {
                        pointedEntity = entity;
                        d2 = d3;
                    }
                }
            }
        }

        return pointedEntity;
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public static Entity getPointedEntity(World world, EntityPlayer entityplayer, double range, Class<?> clazz) {
        Entity pointedEntity = null;
        double d = range;
        Vec3 vec3d = Vec3.createVectorHelper(
            entityplayer.posX,
            entityplayer.posY + (double) entityplayer.getEyeHeight(),
            entityplayer.posZ);
        Vec3 vec3d1 = entityplayer.getLookVec();
        Vec3 vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
        float f1 = 1.1F;
        List list = world.getEntitiesWithinAABBExcludingEntity(
            entityplayer,
            entityplayer.boundingBox.addCoord(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d)
                .expand((double) f1, (double) f1, (double) f1));
        double d2 = 0.0;

        for (int i = 0; i < list.size(); ++i) {
            Entity entity = (Entity) list.get(i);
            if (entity.canBeCollidedWith() && world.func_147447_a(
                Vec3.createVectorHelper(
                    entityplayer.posX,
                    entityplayer.posY + (double) entityplayer.getEyeHeight(),
                    entityplayer.posZ),
                Vec3.createVectorHelper(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ),
                false,
                true,
                false) == null && !clazz.isInstance(entity) && !(entity instanceof EntityItemFrame)) {
                float f2 = Math.max(0.8F, entity.getCollisionBorderSize());
                AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double) f2, (double) f2, (double) f2);
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3d, vec3d2);
                if (axisalignedbb.isVecInside(vec3d)) {
                    if (0.0 < d2 || d2 == 0.0) {
                        pointedEntity = entity;
                        d2 = 0.0;
                    }
                } else if (movingobjectposition != null) {
                    double d3 = vec3d.distanceTo(movingobjectposition.hitVec);
                    if (d3 < d2 || d2 == 0.0) {
                        pointedEntity = entity;
                        d2 = d3;
                    }
                }
            }
        }

        return pointedEntity;
    }
}
