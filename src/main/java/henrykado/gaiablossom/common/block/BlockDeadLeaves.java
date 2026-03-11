package henrykado.gaiablossom.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDeadLeaves extends ModBlock {

    protected BlockDeadLeaves() {
        super(Material.leaves, "dead_leaves");
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHardness(0.1F);
        this.setStepSound(soundTypeGrass);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(
            (double) x + this.minX,
            (double) y + this.minY,
            (double) z + this.minZ,
            (double) x + this.maxX,
            y,
            (double) z + this.maxZ);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, int x, int y, int z) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    public boolean canPlaceBlockAt(World worldIn, int x, int y, int z) {
        Block block = worldIn.getBlock(x, y - 1, z);
        return block != Blocks.ice && block != Blocks.packed_ice
            && (block.isLeaves(worldIn, x, y - 1, z) || block.isOpaqueCube() && block.getMaterial()
                .blocksMovement());
    }

    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        if (!this.canPlaceBlockAt(worldIn, x, y, z)) {
            worldIn.setBlockToAir(x, y, z);
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        return side == 1 || super.shouldSideBeRendered(worldIn, x, y, z, side);
    }
}
