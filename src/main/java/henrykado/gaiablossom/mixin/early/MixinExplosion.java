package henrykado.gaiablossom.mixin.early;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Explosion.class)
public class MixinExplosion {

    @Shadow
    public Entity exploder;

    @Redirect(
        method = "doExplosionB",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/Block;dropBlockAsItemWithChance(Lnet/minecraft/world/World;IIIIFI)V"))
    public void dropBlockWithChanceRedirect(Block block, World world, int x, int y, int z, int meta, float chance,
        int fortune) {
        if (exploder instanceof EntityCreeper) block.dropBlockAsItem(world, x, y, z, meta, fortune);
        else block.dropBlockAsItemWithChance(world, x, y, z, meta, chance, fortune);
    }
}
