package henrykado.gaiablossom.asm.replacements.valkyrie;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.api.player.util.IAetherBoss;
import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.blocks.dungeon.BlockDungeonBase;
import com.gildedgames.the_aether.entities.projectile.crystals.EntityCrystal;
import com.gildedgames.the_aether.entities.util.AetherNameGen;
import com.gildedgames.the_aether.entities.util.EntityAetherItem;
import com.gildedgames.the_aether.entities.util.EntityBossMob;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.registry.achievements.AchievementsAether;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class NewEntityValkyrieQueen extends EntityBossMob implements IAetherBoss {

    // private EntityAIAttackContinuously enhancedCombat;
    public int angerLevel;
    public int timeLeft;
    public int timeUntilTeleport;
    public int chatTime;
    public int timeUntilTeleportToPlayer;
    public int dungeonX;
    public int dungeonY;
    public int dungeonZ;
    public int dungeonEntranceZ;
    public double safeX;
    public double safeY;
    public double safeZ;
    public float sinage;
    public double lastMotionY;

    public NewEntityValkyrieQueen(World world) {
        super(world);
        // this.enhancedCombat = new EntityAIAttackContinuously(this, 0.65);
        this.timeUntilTeleport = this.rand.nextInt(250);
        this.registerEntityAI();
        this.dataWatcher.updateObject(19, AetherNameGen.valkGen());
        this.safeX = this.posX;
        this.safeY = this.posY;
        this.safeZ = this.posZ;
    }

    public NewEntityValkyrieQueen(World world, double x, double y, double z) {
        this(world);
        this.safeX = this.posX = x;
        this.safeY = this.posY = y;
        this.safeZ = this.posZ = z;
    }

    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, (byte) 0);
        this.dataWatcher.addObject(19, AetherNameGen.valkGen());
    }

    public void registerEntityAI() {
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false));
        this.tasks.addTask(1, new ValkyrieQueenAIAttack(this, 0.65));
        this.tasks.addTask(2, new EntityAISwimming(this));
        this.tasks.addTask(7, new ValkyrieQueenAIWander(this, 0.5));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F, 200.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange)
            .setBaseValue(28.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(0.85);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(13.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(500.0);
    }

    protected boolean isMovementBlocked() {
        return !this.isBossReady();
    }

    public void addVelocity(double x, double y, double z) {
        if (this.isBossReady()) {
            super.addVelocity(x, y, z);
        }

    }

    public void swingArm() {
        if (!this.isSwingInProgress) {
            this.isSwingInProgress = true;
        }

    }

    private void becomeAngryAt(EntityLivingBase entity) {
        this.setTarget(entity);
        this.angerLevel = 200 + this.rand.nextInt(200);
    }

    public void setDungeon(int i, int j, int k) {
        this.dungeonX = i;
        this.dungeonY = j;
        this.dungeonZ = k - 19;
    }

    private void unlockDoor() {
        this.worldObj.setBlock(this.dungeonX - 1, this.dungeonY, this.dungeonEntranceZ, Blocks.air);
        this.worldObj.setBlock(this.dungeonX - 1, this.dungeonY, this.dungeonEntranceZ + 1, Blocks.air);
        this.worldObj.setBlock(this.dungeonX - 1, this.dungeonY + 1, this.dungeonEntranceZ + 1, Blocks.air);
        this.worldObj.setBlock(this.dungeonX - 1, this.dungeonY + 1, this.dungeonEntranceZ, Blocks.air);
    }

    private void unlockTreasure() {
        this.worldObj.setBlock(this.dungeonX + 16, this.dungeonY + 1, this.dungeonZ + 9, Blocks.trapdoor, 3, 2);
        this.worldObj.setBlock(this.dungeonX + 17, this.dungeonY + 1, this.dungeonZ + 9, Blocks.trapdoor, 2, 2);
        this.worldObj.setBlock(this.dungeonX + 16, this.dungeonY + 1, this.dungeonZ + 10, Blocks.trapdoor, 3, 2);
        this.worldObj.setBlock(this.dungeonX + 17, this.dungeonY + 1, this.dungeonZ + 10, Blocks.trapdoor, 2, 2);

        for (int x = this.dungeonX - 27; x < this.dungeonX + 30; ++x) {
            for (int y = this.dungeonY - 1; y < this.dungeonY + 22; ++y) {
                for (int z = this.dungeonZ - 6; z < this.dungeonZ + 26; ++z) {
                    Block block = this.worldObj.getBlock(x, y, z);
                    if (block == BlocksAether.locked_angelic_stone
                        || block == BlocksAether.locked_light_angelic_stone) {
                        this.worldObj.setBlock(x, y, z, ((BlockDungeonBase) block).getUnlockedBlock());
                    }
                }
            }
        }

    }

    private void chatItUp(EntityPlayer player, String s) {
        Side side = FMLCommonHandler.instance()
            .getEffectiveSide();
        if (this.chatTime <= 0) {
            if (side.isClient()) {
                Aether.proxy.sendMessage(player, s);
            }

            this.chatTime = 60;
        }

    }

    public void makeHomeShot(int shots, EntityPlayer player) {
        for (int i = 0; i < shots; ++i) {
            EntityCrystal crystal = new EntityCrystal(
                this.worldObj,
                this.posX - this.motionX / 2.0,
                this.posY,
                this.posZ - this.motionZ / 2.0,
                player);
            if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(crystal);
            }
        }

    }

    public boolean interact(EntityPlayer entityplayer) {
        this.faceEntity(entityplayer, 180.0F, 180.0F);
        if (!this.isBossReady()) {
            boolean flag = false;
            for (int slotId = 0; slotId < entityplayer.inventory.mainInventory.length; ++slotId) {
                ItemStack stack = entityplayer.inventory.mainInventory[slotId];
                if (stack != null && stack.getItem() == ItemsAether.victory_medal) {
                    flag = true;
                    if (stack.stackSize >= 10) {
                        entityplayer.inventory.mainInventory[slotId] = null;
                        this.setBossReady(true);
                        return true;
                    }
                }
            }
            if (flag) {
                chatItUp(entityplayer, StatCollector.translateToLocal("gui.valkyrie.dialog.player.lackmedals"));
            } else {
                chatItUp(entityplayer, StatCollector.translateToLocal("gui.queen.nomedals"));
            }
        }

        return super.interact(entityplayer);
    }

    /*
     * public void updateEntityActionState() {
     * super.updateEntityActionState();
     * if (!this.isBossReady()) {
     * this.motionY *= 0.5;
     * this.moveStrafing = this.moveForward = 0.0F;
     * } else {
     * if (this.getEntityToAttack() != null && this.getEntityToAttack() instanceof EntityPlayer) {
     * EntityPlayer target = (EntityPlayer) this.getEntityToAttack();
     * if (target != null) {
     * if (target.posY > this.posY) {
     * ++this.timeUntilTeleportToPlayer;
     * if (this.timeUntilTeleportToPlayer >= 75 && !this.worldObj.isRemote) {
     * this.teleportToPlayer();
     * }
     * } else {
     * this.timeUntilTeleportToPlayer = 0;
     * }
     * if (this.timeUntilTeleport++ >= 450) {
     * if (this.onGround && this.rand.nextInt(5) == 0) {
     * this.makeHomeShot(1, target);
     * } else {
     * this.teleport(target.posX, target.posY, target.posZ, 4);
     * }
     * } else
     * if (this.timeUntilTeleport >= 446 || !(this.posY <= 0.0) && !(this.posY <= this.safeY - 16.0)) {
     * if (this.timeUntilTeleport % 5 == 0 && !this.canEntityBeSeen(target)) {
     * this.timeUntilTeleport += 100;
     * }
     * } else {
     * this.timeUntilTeleport = 446;
     * }
     * }
     * }
     * if (!this.worldObj.isRemote) {
     * for (int k = 2; k < 23; k += 7) {
     * Block state = this.worldObj.getBlock(this.dungeonX - 1, this.dungeonY, this.dungeonZ + k);
     * if (state != BlocksAether.locked_angelic_stone
     * || state != BlocksAether.locked_light_angelic_stone) {
     * this.worldObj.setBlock(
     * this.dungeonX - 1,
     * this.dungeonY,
     * this.dungeonZ + k,
     * BlocksAether.locked_angelic_stone);
     * this.worldObj.setBlock(
     * this.dungeonX - 1,
     * this.dungeonY,
     * this.dungeonZ + k + 1,
     * BlocksAether.locked_angelic_stone);
     * this.worldObj.setBlock(
     * this.dungeonX - 1,
     * this.dungeonY + 1,
     * this.dungeonZ + k + 1,
     * BlocksAether.locked_angelic_stone);
     * this.worldObj.setBlock(
     * this.dungeonX - 1,
     * this.dungeonY + 1,
     * this.dungeonZ + k,
     * BlocksAether.locked_angelic_stone);
     * this.dungeonEntranceZ = this.dungeonZ + k;
     * }
     * }
     * }
     * }
     * if (this.getEntityToAttack() != null && this.getEntityToAttack().isDead) {
     * this.setTarget((Entity) null);
     * this.unlockDoor();
     * this.angerLevel = 0;
     * this.setHealth(this.getMaxBossHealth());
     * }
     * if (this.chatTime > 0) {
     * --this.chatTime;
     * }
     * }
     */

    public void onUpdate() {
        this.lastMotionY = this.motionY;
        super.onUpdate();
        if (!this.onGround && this.getEntityToAttack() != null
            && this.lastMotionY >= 0.0
            && this.motionY < 0.0
            && this.getDistanceToEntity(this.getEntityToAttack()) <= 16.0F
            && this.canEntityBeSeen(this.getEntityToAttack())) {
            double a = this.getEntityToAttack().posX - this.posX;
            double b = this.getEntityToAttack().posZ - this.posZ;
            double angle = Math.atan2(a, b);
            this.motionX = Math.sin(angle) * 0.25;
            this.motionZ = Math.cos(angle) * 0.25;
        }

        if (!this.onGround && !this.isOnLadder()
            && Math.abs(this.motionY - this.lastMotionY) > 0.07
            && Math.abs(this.motionY - this.lastMotionY) < 0.09) {
            this.motionY += 0.054999999701976776;
            if (this.motionY < -0.2750000059604645) {
                this.motionY = -0.2750000059604645;
            }
        }

        if (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL
            && (this.getEntityToAttack() != null || this.angerLevel > 0)) {
            this.angerLevel = 0;
            this.setTarget((Entity) null);
        }

        if (!this.onGround) {
            this.sinage += 0.75F;
        } else {
            this.sinage += 0.15F;
        }

        if (this.sinage > 6.283186F) {
            this.sinage -= 6.283186F;
        }

        if (this.getHealth() <= 0.0F || this.isDead) {
            if (!this.worldObj.isRemote) {
                this.unlockDoor();
                this.unlockTreasure();
            }

            if (this.getEntityToAttack() instanceof EntityPlayer) {
                ((EntityPlayer) this.getEntityToAttack()).triggerAchievement(AchievementsAether.defeat_silver);
                PlayerAether.get((EntityPlayer) this.getEntityToAttack())
                    .setFocusedBoss((IAetherBoss) null);
            }

            this.spawnExplosionParticle();
            this.setDead();
        }

        if (!this.otherDimension()) {
            --this.timeLeft;
            if (this.timeLeft <= 0) {
                this.spawnExplosionParticle();
                this.setDead();
            }
        }

    }

    protected Entity findPlayerToAttack() {
        return null;
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setShort("Anger", (short) this.angerLevel);
        nbttagcompound.setShort("TimeLeft", (short) this.timeLeft);
        nbttagcompound.setBoolean("Duel", this.isBossReady());
        nbttagcompound.setInteger("DungeonX", this.dungeonX);
        nbttagcompound.setInteger("DungeonY", this.dungeonY);
        nbttagcompound.setInteger("DungeonZ", this.dungeonZ);
        nbttagcompound.setInteger("DungeonEntranceZ", this.dungeonEntranceZ);
        nbttagcompound.setTag("SafePos", this.newDoubleNBTList(new double[] { this.safeX, this.safeY, this.safeZ }));
        nbttagcompound.setString("BossName", this.getName());
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        this.angerLevel = nbttagcompound.getShort("Anger");
        this.timeLeft = nbttagcompound.getShort("TimeLeft");
        this.setBossReady(nbttagcompound.getBoolean("Duel"));
        this.dungeonX = nbttagcompound.getInteger("DungeonX");
        this.dungeonY = nbttagcompound.getInteger("DungeonY");
        this.dungeonZ = nbttagcompound.getInteger("DungeonZ");
        this.dungeonEntranceZ = nbttagcompound.getInteger("DungeonEntranceZ");
        NBTTagList nbttaglist = nbttagcompound.getTagList("SafePos", 10);
        this.setBossName(nbttagcompound.getString("BossName"));
        this.safeX = nbttaglist.func_150309_d(0);
        this.safeY = nbttaglist.func_150309_d(1);
        this.safeZ = nbttaglist.func_150309_d(2);
    }

    public boolean attackEntityFrom(DamageSource ds, float i) {
        if (!(ds.getEntity() instanceof EntityPlayer)) {
            this.extinguish();
            return false;
        } else {
            EntityPlayer player = (EntityPlayer) ds.getEntity();
            if (this.isBossReady()) {
                PlayerAether playerAether = PlayerAether.get(player);
                if (playerAether != null) {
                    boolean flag = true;
                    if (!player.isDead && flag) {
                        playerAether.setFocusedBoss(this);
                    }

                    if (this.isDead || this.getHealth() <= 0.0F) {
                        playerAether.setFocusedBoss((IAetherBoss) null);
                    }
                }

                if (this.getEntityToAttack() == null) {
                    if (ds.getEntity() instanceof EntityLivingBase) {
                        this.becomeAngryAt((EntityLivingBase) ds.getEntity());
                    }
                } else {
                    this.timeUntilTeleport += 60;
                }

                return super.attackEntityFrom(ds, i);
            }
        }
        return false;
    }

    public boolean attackEntityAsMob(Entity entity) {
        boolean flag = false;
        this.swingArm();
        flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0F);
        if (entity != null && this.getEntityToAttack() != null
            && entity == this.getEntityToAttack()
            && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.getHealth() <= 0.0F || player.isDead) {
                this.setTarget((Entity) null);
                this.angerLevel = 0;
                this.unlockDoor();
            }
        }

        return flag;
    }

    protected void dropFewItems(boolean var1, int var2) {
        this.entityDropItem(new ItemStack(ItemsAether.dungeon_key, 1, 1), 0.5F);
        this.dropItem(Items.golden_sword, 1);
    }

    public EntityItem entityDropItem(ItemStack stack, float offsetY) {
        if (stack.stackSize != 0 && stack.getItem() != null) {
            EntityAetherItem entityitem = new EntityAetherItem(
                this.worldObj,
                this.posX,
                this.posY + (double) offsetY,
                this.posZ,
                stack);
            if (this.captureDrops) {
                this.capturedDrops.add(entityitem);
            } else {
                this.worldObj.spawnEntityInWorld(entityitem);
            }

            return entityitem;
        } else {
            return null;
        }
    }

    public void fall(float distance) {}

    public void teleport(double x, double y, double z, int rad) {
        int a = this.rand.nextInt(rad + 1);
        int b = this.rand.nextInt(rad / 2);
        int c = rad - a;
        a *= this.rand.nextInt(2) * 2 - 1;
        b *= this.rand.nextInt(2) * 2 - 1;
        c *= this.rand.nextInt(2) * 2 - 1;
        x += (double) a;
        y += (double) b;
        z += (double) c;
        int newX = (int) Math.floor(x - 0.5);
        int newY = (int) Math.floor(y - 0.5);
        int newZ = (int) Math.floor(z - 0.5);
        boolean flag = false;

        for (int q = 0; q < 32 && !flag; ++q) {
            int i = newX + (this.rand.nextInt(rad / 2) - this.rand.nextInt(rad / 2));
            int j = newY + (this.rand.nextInt(rad / 2) - this.rand.nextInt(rad / 2));
            int k = newZ + (this.rand.nextInt(rad / 2) - this.rand.nextInt(rad / 2));
            if (this.isAirySpace(i, j, k) && this.isAirySpace(i, j + 1, k)
                && !this.isAirySpace(i, j - 1, k)
                && i > this.dungeonX
                && i < this.dungeonX + 20
                && j > this.dungeonY
                && j < this.dungeonY + 12
                && k > this.dungeonZ
                && k < this.dungeonZ + 20) {
                newX = i;
                newY = j;
                newZ = k;
                flag = true;
            }
        }

        if (!flag) {
            this.timeUntilTeleport -= this.rand.nextInt(40) + 40;
            if (this.posY <= 0.0) {
                this.timeUntilTeleport = 446;
            }
        } else {
            this.spawnExplosionParticle();
            // this.enhancedCombat.resetTask();
            this.setPosition((double) newX + 0.5, (double) newY + 0.5, (double) newZ + 0.5);
            this.isJumping = false;
            this.renderYawOffset = this.rand.nextFloat() * 360.0F;
            this.timeUntilTeleport = this.rand.nextInt(40);
            this.motionX = this.motionY = this.motionZ = (double) (this.moveForward = this.moveStrafing = this.rotationPitch = this.rotationYaw = 0.0F);
        }

    }

    public void teleportToPlayer() {
        if (this.getEntityToAttack() instanceof EntityPlayer) {
            this.spawnExplosionParticle();
            // this.enhancedCombat.resetTask();
            this.setPosition(
                this.getEntityToAttack().posX + 0.5,
                this.getEntityToAttack().posY + 0.5,
                this.getEntityToAttack().posZ + 0.5);
            this.isJumping = false;
            this.renderYawOffset = this.rand.nextFloat() * 360.0F;
            this.timeUntilTeleportToPlayer = 0;
            this.motionX = this.motionY = this.motionZ = (double) (this.moveForward = this.moveStrafing = this.rotationPitch = this.rotationYaw = 0.0F);
        }

    }

    public boolean isAirySpace(int x, int y, int z) {
        Block block = this.worldObj.getBlock(x, y, z);
        return block == Blocks.air || block.getCollisionBoundingBoxFromPool(this.worldObj, x, y, z) == null;
    }

    public boolean otherDimension() {
        return true;
    }

    public boolean canDespawn() {
        return false;
    }

    public boolean getCanSpawnHere() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        return this.worldObj.checkBlockCollision(this.boundingBox)
            && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox)
                .size() == 0;
    }

    public int getMedals(EntityPlayer entityplayer) {
        int medals = 0;
        ItemStack[] var3 = entityplayer.inventory.mainInventory;
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            ItemStack item = var3[var5];
            if (item != null && item.getItem() == ItemsAether.victory_medal) {
                medals += item.stackSize;
            }
        }

        return medals;
    }

    public List<?> getPlayersInDungeon() {
        return this.worldObj.getEntitiesWithinAABBExcludingEntity(
            this.getEntityToAttack(),
            AxisAlignedBB
                .getBoundingBox(
                    (double) this.dungeonX,
                    (double) this.dungeonY,
                    (double) this.dungeonZ,
                    (double) this.dungeonX,
                    (double) this.dungeonY,
                    (double) this.dungeonZ)
                .expand(20.0, 20.0, 20.0));
    }

    protected String getHurtSound() {
        return "game.player.hurt";
    }

    protected String getDeathSound() {
        return "game.player.die";
    }

    public String getName() {
        return this.dataWatcher.getWatchableObjectString(19);
    }

    public String getBossName() {
        return AetherConfig.config.get("Misc", "Enables randomly generated boss names", true)
            .getBoolean()
                ? this.dataWatcher.getWatchableObjectString(19) + ", "
                    + StatCollector.translateToLocal("title.aether_legacy.valkyrie_queen.name")
                : StatCollector.translateToLocal("title.aether_legacy.valkyrie_queen.name");
    }

    public void setBossName(String name) {
        this.dataWatcher.updateObject(19, name);
    }

    public float getBossHealth() {
        return this.getHealth();
    }

    public float getMaxBossHealth() {
        return this.getMaxHealth();
    }

    public void setBossReady(boolean isReady) {
        this.dataWatcher.updateObject(18, (byte) (isReady ? 1 : 0));
    }

    public boolean isBossReady() {
        return this.dataWatcher.getWatchableObjectByte(18) == 1;
    }
}
