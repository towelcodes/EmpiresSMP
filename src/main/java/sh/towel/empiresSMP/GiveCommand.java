package sh.towel.empiresSMP;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;
import java.util.UUID;

public class GiveCommand implements CommandExecutor {
    private EmpiresSMP plugin;

    GiveCommand(EmpiresSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            NamespacedKey key = new NamespacedKey(this.plugin, "item_id");

            ItemStack overallOp = new ItemStack(Material.NETHERITE_SWORD);
            ItemMeta meta = overallOp.getItemMeta();
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "sceptre");
            meta.setItemName("" + ChatColor.GOLD + ChatColor.BOLD + "The Sceptre");
//            meta.getAttributeModifiers(Attribute.GENERIC_ATTACK_DAMAGE).add(new AttributeModifier(
//                    new NamespacedKey(this.plugin, "generic.attack_damage"),
//                    3.0d,
//                    AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND
//            ));
            meta.addAttributeModifier(
                    Attribute.GENERIC_ATTACK_DAMAGE,
                    new AttributeModifier(
                            UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"),
                            "generic.attack_damage",
                            10.0d,
                            AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlotGroup.MAINHAND
            ));
            meta.addAttributeModifier(
                    Attribute.GENERIC_ATTACK_SPEED,
                    new AttributeModifier(
                            UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"),
                            "generic.attack_damage",
                            1.6d,
                            AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlotGroup.MAINHAND
                    )
            );
            overallOp.setItemMeta(meta);
            overallOp.addEnchantments(Map.of(
                    Enchantment.SHARPNESS, 5,
                    Enchantment.SWEEPING_EDGE, 3,
                    Enchantment.FIRE_ASPECT, 2,
                    Enchantment.MENDING, 1,
                    Enchantment.LOOTING, 3,
                    Enchantment.UNBREAKING, 3
            ));

            p.getInventory().addItem(overallOp);
        }
        return true;
    }
}
