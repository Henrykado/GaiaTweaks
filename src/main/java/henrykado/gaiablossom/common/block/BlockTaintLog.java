package henrykado.gaiablossom.common.block;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.CustomSoundType;

public class BlockTaintLog extends BlockRotatedPillar {

    public BlockTaintLog() {
        super(Material.wood);
        String name = "log_taintwood";

        setBlockName(name);
        setBlockTextureName("gaiablossom:" + name);
        GameRegistry.registerBlock(this, name);

        this.setHardness(1.5F);
        this.setStepSound(new CustomSoundType("gore", 0.5F, 0.8F));
        this.setCreativeTab(Thaumcraft.tabTC);
        this.setTickRandomly(true);
    }

    @SideOnly(Side.CLIENT)
    protected IIcon getSideIcon(int p_150163_1_) {
        return this.blockIcon;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.field_150164_N = reg.registerIcon(this.getTextureName() + "_top");
        this.blockIcon = reg.registerIcon(this.getTextureName());
    }

    public boolean onBlockEventReceived(World world, int x, int y, int z, int id, int cd) {
        if (id == 1) {
            if (world.isRemote) {
                world.playSound(
                    (double) x,
                    (double) y,
                    (double) z,
                    "thaumcraft:roots",
                    0.1F,
                    0.9F + world.rand.nextFloat() * 0.2F,
                    false);
            }

            return true;
        } else {
            return super.onBlockEventReceived(world, x, y, z, id, cd);
        }
    }
}
