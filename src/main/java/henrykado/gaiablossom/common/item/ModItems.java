package henrykado.gaiablossom.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHangingEntity;

import cpw.mods.fml.common.registry.GameRegistry;
import henrykado.gaiablossom.common.entity.EntityLivingWoodItemFrame;

public class ModItems {

    public static Item living_wood_item_frame = new ItemHangingEntity(EntityLivingWoodItemFrame.class)
        .setUnlocalizedName("living_frame")
        .setTextureName("living_frame");

    public static Item recall_potion;
    public static LivingJetpack living_jetpack;

    public static Item invisibilityCloak;
    public static Item cloudPendant;
    public static Item dodgeRing;

    public static void init() {
        recall_potion = new RecallPotion();
        living_jetpack = new LivingJetpack();

        invisibilityCloak = new ItemInvisibilityCloak();
        cloudPendant = new ItemCloudPendant();
        dodgeRing = new ItemDodgeRing();

        GameRegistry.registerItem(living_wood_item_frame, "gaiablossom:living_frame");
    }
}
