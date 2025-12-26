package henrykado.gaiablossom.mixin.late.aether;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.items.armor.ItemAetherArmor;

@Mixin(ItemsAether.class)
public class MixinItemsAether {

    @Redirect(
        method = "initialization",
        at = @At(
            value = "INVOKE",
            target = "Lcom/gildedgames/the_aether/items/ItemsAether;register(Ljava/lang/String;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;"),
        remap = false)
    private static Item registerRedirect(String name, Item item) {
        if (name.equals("sentry_boots")) {
            return ItemsAether.register(
                name,
                (new ItemAetherArmor(3, ItemArmor.ArmorMaterial.IRON, "sentry", (Item) null))
                    .setTextureName(Aether.find("armor/sentry_boots")));
        } else if (name.equals("ambrosium_shard")) {
            return ItemsAether.register(name, item.setMaxStackSize(8));
        }
        return ItemsAether.register(name, item);
    }
}
