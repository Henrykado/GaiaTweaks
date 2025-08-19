package henrykado.gaiablossom.common.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.common.registry.GameRegistry;
import henrykado.gaiablossom.CustomCreativeTab;
import henrykado.gaiablossom.GaiaBlossom;

public class LivingJetpack extends ItemArmor implements IBauble {

    public LivingJetpack() {
        super(ArmorMaterial.CHAIN, 2, 1);
        String uniqueName = GaiaBlossom.MODID + ":living_jetpack";
        this.setCreativeTab(CustomCreativeTab.INSTANCE);
        this.setUnlocalizedName(uniqueName);
        this.setTextureName(uniqueName);
        GameRegistry.registerItem(this, uniqueName);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        onWornTick(stack, player);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.BELT;
    }

    private static boolean jumped = false;
    private static boolean canFly = false;

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        if (player instanceof EntityPlayerSP clientPlayer && player == Minecraft.getMinecraft().thePlayer) {
            boolean inputJump = clientPlayer.movementInput.jump;

            if (clientPlayer.onGround) {
                canFly = false;
                jumped = false;
            } else {
                if (inputJump && !jumped && !canFly) {
                    jumped = true;
                }
                if (!inputJump && jumped && !canFly) {
                    canFly = true;
                }

                if (inputJump && canFly) {
                    // player.fallDistance = 0;
                    if (player.motionY < -0.2) player.motionY = -0.2;
                    player.motionY = Math.min(player.motionY + 0.085D, 0.2);
                    if (player.isSneaking()) {
                        player.motionY = 0;
                    }
                }
            }
        }
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }
}
