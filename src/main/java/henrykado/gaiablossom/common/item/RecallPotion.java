package henrykado.gaiablossom.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RecallPotion extends ModItem {

    public RecallPotion() {
        super("recall_potion");
        this.setContainerItem(Items.glass_bottle);
        this.setMaxStackSize(16);
    }

    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote && player.dimension == 0) {
            for (int i = 62; i < 128; i++) {
                EntityPlayerMP playerMp = (EntityPlayerMP) player;

                Block block = world.getBlock((int) playerMp.posX, i, (int) playerMp.posZ);

                if (!(block instanceof BlockAir)
                    && world.getBlock((int) playerMp.posX, i + 2, (int) playerMp.posZ) instanceof BlockAir) {
                    if (playerMp.isRiding()) playerMp.mountEntity(null);
                    playerMp.fallDistance = 0.0f;
                    playerMp.setPositionAndUpdate(playerMp.posX, i + 1, playerMp.posZ);
                }
            }
        }

        if (!player.capabilities.isCreativeMode) {
            for (int i = 0; i < 32; ++i) {
                world.spawnParticle(
                    "portal",
                    player.posX,
                    player.posY + world.rand.nextDouble() * 2.0D,
                    player.posZ,
                    world.rand.nextGaussian(),
                    0.0D,
                    world.rand.nextGaussian());
            }

            --stack.stackSize;
            if (stack.stackSize <= 0) {
                return new ItemStack(Items.glass_bottle);
            }

            player.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
        }

        return stack;
    }

    public int getMaxItemUseDuration(ItemStack p_77626_1_) {
        return 64;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.drink;
    }

    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player) {
        player.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
        return itemStackIn;
    }
}
