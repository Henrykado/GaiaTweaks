package henrykado.gaiablossom.mixin.late.botania;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.common.block.BlockHourglass;
import vazkii.botania.common.block.BlockModContainer;
import vazkii.botania.common.block.tile.TileHourglass;

@Mixin(TileHourglass.class)
public abstract class MixinTileHourglass extends TileEntity {
    @Shadow public boolean lock;

    @Inject(method = "updateEntity", at = @At("HEAD"))
    public void updateEntityInject(CallbackInfo ci) {
        boolean redstone = false;
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            int redstoneSide = this.worldObj.getIndirectPowerLevelTo(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, dir.ordinal());
            if (redstoneSide > 0) {
                redstone = true;
                break;
            }
        }

        if (redstone) {
            lock = !lock;
        }
    }
}
