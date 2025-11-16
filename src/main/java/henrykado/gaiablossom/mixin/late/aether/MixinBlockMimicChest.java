package henrykado.gaiablossom.mixin.late.aether;

import java.util.Random;

import net.daveyx0.primitivemobs.client.PrimitiveMobsAchievementPage;
import net.daveyx0.primitivemobs.entity.monster.EntityDMimic;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.gildedgames.the_aether.blocks.dungeon.BlockMimicChest;

@Mixin(BlockMimicChest.class)
public abstract class MixinBlockMimicChest extends BlockChest {

    @Unique
    private final Random blossom_of_Gaia$rand = new Random();

    protected MixinBlockMimicChest(int type) {
        super(type);
    }

    @Inject(method = "spawnMimic", at = @At("HEAD"), cancellable = true, remap = false)
    private void spawnMimicInject(World world, EntityPlayer player, int x, int y, int z, CallbackInfo ci) {
        if (!world.isRemote) {
            EntityDMimic mimic = new EntityDMimic(world);
            if (!player.capabilities.isCreativeMode) {
                mimic.setAttackTarget(player);
            }

            if (!world.isRemote && mimic.getMimic() == 0) {
                world.playSoundAtEntity(mimic, "random.chestopen", 1.0F, 1.0F);
                player.addStat(PrimitiveMobsAchievementPage.mimicFake, 1);
                if (this.blossom_of_Gaia$rand.nextInt(2) == 0) {
                    mimic.setMimic(2 + this.blossom_of_Gaia$rand.nextInt(5));
                } else {
                    mimic.setMimic(1);
                }
            }

            mimic.setPosition((double) x + 0.5, (double) y, (double) z + 0.5);
            world.spawnEntityInWorld(mimic);
        }

        world.setBlockToAir(x, y, z);
        ci.cancel();
    }
}
