package sh.towel.empiresSMP.Listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RareEffects implements Listener {
    private final NamespacedKey idKey;

    public RareEffects(NamespacedKey idKey) {
        this.idKey = idKey;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player d
                && e.getEntity() instanceof Player p)) {
            return;
        }

        // fire empire has this buff
        if (d.hasPermission("empires.phoenix")) {
//            int rand = (int)(Math.random() * 100 + 1);
//            if (rand <= 20) {
//                p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 5*20, 1));
//            }
            if (!p.hasPermission("empires.phoenix")) {
                d.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 5*20, 0));
            }
        }

        if (d.getInventory().getItemInMainHand().getItemMeta() == null) {
            return;
        }

        if (d.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(idKey, PersistentDataType.STRING) == "best_sword") {
            // all the effects lol
            // we will need to reroll each time
            int rand = (int)(Math.random() * 100 + 1);
            if (rand <= 25) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 5*20, 0));
            }

            rand = (int)(Math.random() * 100 + 1);
            if (rand <= 10) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5*20, 0));
            }

            rand = (int)(Math.random() * 100 + 1);
            if (rand <= 25) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 10*20, 0));
            }

            rand = (int)(Math.random() * 100 + 1);
            if (rand <= 10) {
                d.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5*20, 1));
            }
            return;
        }

        // now we check items
        int rand = (int)(Math.random() * 100 + 1);
        switch (d.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(idKey, PersistentDataType.STRING)) {
            case "phoenix_sword" -> {
                if (rand <= 25) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 5*20, 0));
                }
            }
            case "dark_sword" -> {
                if (rand <= 10) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5*20, 0));
                }
            }
            case "sult_sword" -> {
                if (rand <= 25) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 10*20, 0));
                }
            }
            case "free_sword" -> {
                if (rand <= 10) {
                    d.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5*20, 1));
                }
            }
            case null, default -> {}
        }
    }
}
