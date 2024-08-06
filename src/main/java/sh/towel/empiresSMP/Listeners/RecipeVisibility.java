package sh.towel.empiresSMP.Listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.persistence.PersistentDataType;
import sh.towel.empiresSMP.EmpiresSMP;

public class RecipeVisibility implements Listener, CommandExecutor {
    private final EmpiresSMP plugin;
    private final NamespacedKey idKey;
    private final NamespacedKey announceKey;
    public RecipeVisibility(EmpiresSMP plugin, NamespacedKey idKey, NamespacedKey announceKey) {
        this.plugin = plugin;
        this.idKey = idKey;
        this.announceKey = announceKey;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            refreshPlayerAdvancements(p);
        }
        sender.sendMessage(ChatColor.GREEN+"Refreshed all player recipe advancements.");
        return true;
    }

//    @EventHandler
//    public void onJoin(PlayerJoinEvent e) {
//        refreshPlayerAdvancements(e.getPlayer());
//    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent e) {
        CraftingInventory inventory = e.getInventory();
        Recipe recipe = e.getRecipe();
        ItemStack result = recipe != null ? recipe.getResult() : null;

        if (result == null) {
            return;
        }

        String perm = "";
        if  (result.getItemMeta() == null) {
            return;
        }
        switch (result.getItemMeta().getPersistentDataContainer().get(idKey, PersistentDataType.STRING)) {
            case "phoenix_sword" -> {
                perm = "empires.phoenix";
            }
            case "dark_sword" -> {
                perm = "empires.dark";
            }
            case "sult_sword" -> {
                perm = "empires.sult";
            }
            case "free_sword" -> {
                perm = "free_sword";
            }
            case null, default -> {
                return;
            }
        }
        System.out.println("perm is "+perm);
        Player player = (Player) e.getView().getPlayer();
        if (!player.hasPermission(perm)) {
            inventory.setResult(new ItemStack(Material.AIR)); // Deny crafting
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (e.getCurrentItem().getItemMeta() == null) {
            return;
        }

        if (e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(announceKey, PersistentDataType.BYTE) == null) {
            return;
        }

        byte data = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(announceKey, PersistentDataType.BYTE);
        // this doesn't need to be a switch for now but it will if i add more crafting effects
        switch (data) {
            case 0x01, 0x02 -> {
                Bukkit.getOnlinePlayers().forEach((Player p) -> p.playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1f, 1f));
                Player p = (Player) e.getWhoClicked();
                String message = "%vault_prefix%" + p.getDisplayName() + " &ahas crafted " + e.getCurrentItem().getItemMeta().getItemName() + "&a!";
                message = PlaceholderAPI.setPlaceholders(p, message) ;
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
            default -> {}
        }
    }

    public void refreshPlayerAdvancements(Player p) {
        String[] groups = {"phoenix", "dark", "sult", "free"};
        for (String g : groups) {
            NamespacedKey key = new NamespacedKey(plugin, String.format("recipes/tools/%s_sword", g));
            Advancement adv = plugin.getServer().getAdvancement(key);
            AdvancementProgress prog = p.getAdvancementProgress(adv);
            if (p.hasPermission(String.format("empires.%s", g))) {
                if (!prog.isDone()) {
                    for (String criteria : prog.getRemainingCriteria()) {
                        prog.awardCriteria(criteria);
                    }
                }
            } else {
                if (prog.isDone()) {
                    for (String criteria : prog.getAwardedCriteria()) {
                        prog.revokeCriteria(criteria);
                    }
                }
            }
        }
    }

}
