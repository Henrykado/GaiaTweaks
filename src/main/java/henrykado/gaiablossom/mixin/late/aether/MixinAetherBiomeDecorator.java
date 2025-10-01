package henrykado.gaiablossom.mixin.late.aether;

import net.minecraft.block.Block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.world.biome.AetherBiomeDecorator;

@Mixin(AetherBiomeDecorator.class)
public abstract class MixinAetherBiomeDecorator {

    @Shadow
    public abstract void spawnOre(Block block, int size, int chance, int y);

    @Redirect(
        method = "genDecorations",
        at = @At(
            value = "INVOKE",
            target = "Lcom/gildedgames/the_aether/world/biome/AetherBiomeDecorator;spawnOre(Lnet/minecraft/block/Block;III)V"))
    public void spawnOreRedirect(AetherBiomeDecorator instance, Block block, int size, int chance, int y) {
        if (block == BlocksAether.gravitite_ore) {
            spawnOre(block, 5, 2, 32);
            return;
        } else if (block == BlocksAether.icestone) {
            return;
        }
        spawnOre(block, size, chance, y);
    }
}
