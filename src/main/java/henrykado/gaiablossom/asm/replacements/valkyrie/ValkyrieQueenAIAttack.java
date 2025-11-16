package henrykado.gaiablossom.asm.replacements.valkyrie;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class ValkyrieQueenAIAttack extends EntityAIBase {

    private EntityCreature attacker;
    private int attackTick;
    double speedTowardsTarget;
    private double targetX;
    private double targetY;
    private double targetZ;

    public ValkyrieQueenAIAttack(EntityCreature creature, double speedIn) {
        this.attacker = creature;
        this.speedTowardsTarget = speedIn;
        this.setMutexBits(3);
    }

    public boolean shouldExecute() {
        EntityLivingBase entitylivingbase = (EntityLivingBase) this.attacker.getEntityToAttack();
        if (entitylivingbase == null) {
            return false;
        } else {
            return entitylivingbase.isEntityAlive();
        }
    }

    public boolean continueExecuting() {
        EntityLivingBase entitylivingbase = (EntityLivingBase) this.attacker.getEntityToAttack();
        return entitylivingbase != null && entitylivingbase.isEntityAlive()
            && this.attacker.isWithinHomeDistance(
                MathHelper.floor_double(entitylivingbase.posX),
                MathHelper.floor_double(entitylivingbase.posY),
                MathHelper.floor_double(entitylivingbase.posZ));
    }

    public void startExecuting() {}

    public void resetTask() {
        EntityLivingBase entitylivingbase = (EntityLivingBase) this.attacker.getEntityToAttack();
        if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer) entitylivingbase).capabilities.isCreativeMode) {
            this.attacker.setAttackTarget((EntityLivingBase) null);
        }

    }

    public void updateTask() {
        EntityLivingBase entitylivingbase = (EntityLivingBase) this.attacker.getEntityToAttack();
        this.attacker.getNavigator()
            .setPath(
                this.attacker.getNavigator()
                    .getPathToEntityLiving(entitylivingbase),
                this.speedTowardsTarget);
        this.attacker.getLookHelper()
            .setLookPositionWithEntity(entitylivingbase, 360.0F, 360.0F);
        double d0 = this.attacker
            .getDistanceSq(entitylivingbase.posX, entitylivingbase.boundingBox.minY, entitylivingbase.posZ);
        if (this.attacker.getEntitySenses()
            .canSee(entitylivingbase)
            && (this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0
                || entitylivingbase.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0
                || this.attacker.getRNG()
                    .nextFloat() < 0.05F)) {
            this.targetX = entitylivingbase.posX;
            this.targetY = entitylivingbase.boundingBox.minY;
            this.targetZ = entitylivingbase.posZ;
        }

        this.attackTick = Math.max(this.attackTick - 1, 0);
        this.checkAndPerformAttack(entitylivingbase, d0);
    }

    protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_) {
        double d0 = this.getAttackReachSqr(p_190102_1_);
        if (p_190102_2_ <= d0 && this.attackTick <= 0) {
            this.attackTick = 20;
            this.attacker.swingItem();
            this.attacker.attackEntityAsMob(p_190102_1_);
        }

    }

    protected double getAttackReachSqr(EntityLivingBase attackTarget) {
        return (double) (this.attacker.width * 2.0F * this.attacker.width * 2.0F + attackTarget.width);
    }
}
