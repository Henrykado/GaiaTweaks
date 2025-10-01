package henrykado.gaiablossom.mixin.late.thaumcraft;

import net.minecraft.block.BlockStone;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.tiles.TileNode;

@Mixin(TileNode.class)
public class MixinTileNode {

    @Inject(method = "func_145845_h", at = @At("TAIL"))
    private void updateEntityInject(CallbackInfo ci) {
        TileNode node = (TileNode) (Object) this;
        World world = node.getWorldObj();

        if (world.getTotalWorldTime() % 3600 == 0) {
            blossomOfGaia$infuseStone(node, world, node.xCoord, node.yCoord, node.zCoord);
        }
    }

    @Unique
    private static void blossomOfGaia$infuseStone(TileNode node, World world, int nx, int ny, int nz) {
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    if (world.rand.nextInt(75) == 0 && world.getBlock(x + nx, y + ny, z + nz)
                        .getClass()
                        .equals(BlockStone.class)) {
                        Aspect aspect = node.getAspects()
                            .getAspects()[world.rand.nextInt(
                                node.getAspects()
                                    .size())];

                        byte md = 4; // EARTH, default
                        if (aspect == Aspect.AIR) {
                            md = 1;
                        } else if (aspect == Aspect.FIRE) {
                            md = 2;
                        } else if (aspect == Aspect.WATER) {
                            md = 3;
                        } else if (aspect == Aspect.ORDER) {
                            md = 5;
                        } else {
                            md = 6; // ENTROPY
                        }

                        if (node.getAspects()
                            .getAmount(aspect) > 15) {
                            node.getAspects()
                                .reduce(aspect, 1);
                            world.setBlock(x + nx, y + ny, z + nz, ConfigBlocks.blockCustomOre, md, 2);
                        }
                    }
                }
            }
        }
    }
}
