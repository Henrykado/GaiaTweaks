package henrykado.gaiablossom.event.eep;

import java.util.UUID;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import cpw.mods.fml.common.Loader;
import henrykado.gaiablossom.CommonProxy;
import henrykado.gaiablossom.GaiaBlossom;

public class GaiaPlayer implements IExtendedEntityProperties {

    public static final String PROP_NAME = GaiaBlossom.MODID + "_PlayerEntityData";

    public static GaiaPlayer get(Entity p) {
        return (GaiaPlayer) p.getExtendedProperties(PROP_NAME);
    }

    public int staminaTimer = 400;
    public int bonusHealth = 0;

    @Override
    public void saveNBTData(NBTTagCompound compound) {}

    @Override
    public void loadNBTData(NBTTagCompound compound) {}

    @Override
    public void init(Entity entity, World world) {}

    private static final UUID globalID = UUID.fromString("B243BE32-DC1B-4C53-8D13-8752D5C69D5B");

    public static void recalculateHealth(EntityPlayer player, GaiaPlayer stats, ItemStack armorStack, int armorSlot) {
        if (Loader.isModLoaded("TConstruct")) return;

        ItemStack[] stacks = player.inventory.armorInventory;

        int bonusHP = 0;
        for (int i = 0; i < 4; i++) {
            ItemStack stack = stacks[i];
            if (i == armorSlot - 1) stack = armorStack;

            if (stack != null && EnchantmentHelper.getEnchantmentLevel(CommonProxy.growth.effectId, stack) > 0) {
                bonusHP += 2;
                if (i == 2) bonusHP += 2;
            }
        }
        int prevHealth = stats.bonusHealth;
        stats.bonusHealth = bonusHP;

        int healthChange = bonusHP - prevHealth;
        if (healthChange != 0) {
            IAttributeInstance attributeinstance = player.getAttributeMap()
                .getAttributeInstance(SharedMonsterAttributes.maxHealth);
            try {
                attributeinstance.removeModifier(attributeinstance.getModifier(globalID));
            } catch (Exception ignored) {}
            attributeinstance.applyModifier(new AttributeModifier(globalID, "gaiablossom.bonusHealth", bonusHP, 0));
        }
    }

    public static void recalculateHealth(EntityPlayer player, GaiaPlayer stats) {
        recalculateHealth(player, stats, null, -1);
    }
}
