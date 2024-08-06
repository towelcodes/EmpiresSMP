package sh.towel.empiresSMP.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import sh.towel.empiresSMP.EmpiresSMP;

import java.util.Collection;
import java.util.List;

public class EffectGiver implements Listener, CommandExecutor {
    private final EmpiresSMP plugin;
    public EffectGiver(EmpiresSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (PotionEffect effect : p.getActivePotionEffects())
                p.removePotionEffect(effect.getType());
            giveEffects(p);
        }
        sender.sendMessage(ChatColor.GREEN + "Refreshed all player effects.");
        return true;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (PotionEffect effect : p.getActivePotionEffects())
                p.removePotionEffect(effect.getType());
            giveEffects(p);
        }
        giveEffects(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawn(PlayerRespawnEvent e) {
        System.out.println("respawned");
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
            giveEffects(e.getPlayer());
        }, 8L);
    }

    @EventHandler
    public void onMilk(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() == Material.MILK_BUCKET) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
                giveEffects(e.getPlayer());
            }, 8L);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player p)) {
            return;
        }

        if (p.hasPermission("empires.free")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
                p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, -1, 1));
            }, 120L);
        }
    }

    private void giveEffects(Player p) {
        if (p.hasPermission("empires.phoenix")) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, -1, 0));
        } else if (p.hasPermission("empires.dark")) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, -1, 0));
        } else if (p.hasPermission("empires.sult")) {
            Collection<PotionEffect> eff = List.of(
                    new PotionEffect(PotionEffectType.SATURATION, -1, 0),
                    new PotionEffect(PotionEffectType.SPEED, -1, 1)
            );
            p.addPotionEffects(eff);
        } else if (p.hasPermission("empires.free")) {
            Collection<PotionEffect> eff = List.of(
                    new PotionEffect(PotionEffectType.RESISTANCE, -1, 0),
                    new PotionEffect(PotionEffectType.ABSORPTION, -1, 1)
            );
            p.addPotionEffects(eff);
        }
    }


}
