package sh.towel.empiresSMP;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This is a temporary solution to store custom items
 * This should be replaced in the near future with user configurable items
 */
public class CustomItems {
    private final EmpiresSMP plugin;
    public final NamespacedKey idKey;
    public final NamespacedKey announceKey;

    public CustomItems(EmpiresSMP plugin) {
        this.plugin = plugin;
        this.idKey = new NamespacedKey(this.plugin, "item_id");
        this.announceKey = new NamespacedKey(this.plugin, "announce_type");
    }

    public static ItemStack toMaxDurability(Damageable i) {
        i.setDamage(0);
        return (ItemStack) i;
    }

    public final ItemStack phoenixSword() {
       ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
       ItemMeta m = i.getItemMeta();
       PersistentDataContainer d = m.getPersistentDataContainer();
       d.set(idKey, PersistentDataType.STRING, "phoenix_sword");
       d.set(announceKey, PersistentDataType.BYTE, (byte) 0x01);
       m.setItemName(""+ChatColor.RED+ChatColor.BOLD+"Hellsabre");
       m.setLore(List.of(ChatColor.BLUE+"25% chance to inflict Wither (5s)"));
       i.setItemMeta(m);
       i.addUnsafeEnchantments(Map.of(
               Enchantment.SHARPNESS, 5,
               Enchantment.UNBREAKING, 3,
               Enchantment.MENDING, 1,
               Enchantment.FIRE_ASPECT, 3,
               Enchantment.SWEEPING_EDGE, 3
       ));
       return i;
   }

   public final ItemStack darkSword() {
       ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
       ItemMeta m = i.getItemMeta();
       PersistentDataContainer d = m.getPersistentDataContainer();
       d.set(idKey, PersistentDataType.STRING, "dark_sword");
       d.set(announceKey, PersistentDataType.BYTE, (byte) 0x01);
       m.setItemName(""+ChatColor.DARK_GRAY+ChatColor.BOLD+"Shadowsabre");
       m.setLore(List.of(ChatColor.BLUE+"10% chance to inflict Blindness (5s)"));
       i.setItemMeta(m);
       i.addUnsafeEnchantments(Map.of(
               Enchantment.SHARPNESS, 6,
               Enchantment.UNBREAKING, 3,
               Enchantment.MENDING, 1,
               Enchantment.SWEEPING_EDGE, 3
       ));
       return i;
    }

    public final ItemStack sultSword() {
        ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta m = i.getItemMeta();
        PersistentDataContainer d = m.getPersistentDataContainer();
        d.set(idKey, PersistentDataType.STRING, "sult_sword");
        d.set(announceKey, PersistentDataType.BYTE, (byte) 0x01);
        m.setItemName(""+ChatColor.GOLD+ChatColor.BOLD+"Sandsabre");
        m.setLore(List.of(ChatColor.BLUE+"25% chance to inflict Slowness I (10s)"));
        i.setItemMeta(m);
        i.addUnsafeEnchantments(Map.of(
                Enchantment.SHARPNESS, 5,
                Enchantment.UNBREAKING, 3,
                Enchantment.MENDING, 1,
                Enchantment.SWEEPING_EDGE, 3
        ));
        return i;
    }

    public final ItemStack freeSword() {
        ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta m = i.getItemMeta();
        PersistentDataContainer d = m.getPersistentDataContainer();
        d.set(idKey, PersistentDataType.STRING, "free_sword");
        d.set(announceKey, PersistentDataType.BYTE, (byte) 0x01);
        m.setItemName(""+ChatColor.GREEN+ChatColor.BOLD+"Sabre of Peace");
        m.setLore(List.of(ChatColor.BLUE+"10% chance to give Regeneration II (5s)"));
        i.setItemMeta(m);
        i.addUnsafeEnchantments(Map.of(
                Enchantment.SHARPNESS, 6,
                Enchantment.UNBREAKING, 3,
                Enchantment.MENDING, 1,
                Enchantment.SWEEPING_EDGE, 3
        ));
        return i;
    }

   public final ItemStack bestSword() {
       ItemStack i = new ItemStack(Material.NETHERITE_SWORD);
       ItemMeta m = i.getItemMeta();
       PersistentDataContainer d = m.getPersistentDataContainer();
       d.set(idKey, PersistentDataType.STRING, "best_sword");
       d.set(announceKey, PersistentDataType.BYTE, (byte) 0x02);
       m.setItemName(ChatColor.translateAlternateColorCodes('&', "&0&k| &d&lFinal Sabre &0&k|"));
//            meta.getAttributeModifiers(Attribute.GENERIC_ATTACK_DAMAGE).add(new AttributeModifier(
//                    new NamespacedKey(this.plugin, "generic.attack_damage"),
//                    3.0d,
//                    AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND
//            ));
       m.setLore(List.of(
               ChatColor.BLUE+"25% chance to inflict Wither (5s)",
               ChatColor.BLUE+"10% chance to inflict Blindness (5s)",
               ChatColor.BLUE+"25% chance to inflict Slowness I (10s)",
               ChatColor.BLUE+"10% chance to give Regeneration II (5s)"
       ));
       m.addAttributeModifier(
               Attribute.GENERIC_ATTACK_DAMAGE,
               new AttributeModifier(
                       UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"),
                       "generic.attack_damage",
                       10.0d,
                       AttributeModifier.Operation.ADD_NUMBER,
                       EquipmentSlotGroup.MAINHAND
               ));
       m.addAttributeModifier(
               Attribute.GENERIC_ATTACK_SPEED,
               new AttributeModifier(
                       UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"),
                       "generic.attack_speed",
                       1.6d,
                       AttributeModifier.Operation.ADD_NUMBER,
                       EquipmentSlotGroup.MAINHAND
               )
       );
       i.setItemMeta(m);
       i.addEnchantments(Map.of(
               Enchantment.SHARPNESS, 5,
               Enchantment.SWEEPING_EDGE, 3,
               Enchantment.FIRE_ASPECT, 2,
               Enchantment.MENDING, 1,
               Enchantment.LOOTING, 3,
               Enchantment.UNBREAKING, 3
       ));
       return i;
   }
}
