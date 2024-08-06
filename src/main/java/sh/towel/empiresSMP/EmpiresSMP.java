package sh.towel.empiresSMP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import sh.towel.empiresSMP.Listeners.EffectGiver;
import sh.towel.empiresSMP.Listeners.RareEffects;
import sh.towel.empiresSMP.Listeners.RecipeVisibility;

public final class EmpiresSMP extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().severe("Could not find PlaceholderAPI, which is required!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        CustomItems items = new CustomItems(this);
        EffectGiver effectGiver = new EffectGiver(this);
        RecipeVisibility recipeVisibility = new RecipeVisibility(this, items.idKey, items.announceKey);

        getCommand("giveme").setExecutor(new GiveCommand(this));
        getCommand("emeffects").setExecutor(effectGiver);
        getCommand("emadvance").setExecutor(recipeVisibility);
        getServer().getPluginManager().registerEvents(effectGiver, this);
        getServer().getPluginManager().registerEvents(recipeVisibility, this);
        getServer().getPluginManager().registerEvents(new RareEffects(items.idKey), this);

        ShapedRecipe phoenixR = new ShapedRecipe(new NamespacedKey(this, "phoenix_sword"), items.phoenixSword());
        phoenixR.shape("aba", "bxb", "aba");
        phoenixR.setIngredient('a', Material.BLAZE_ROD);
        phoenixR.setIngredient('b', Material.WITHER_SKELETON_SKULL);
        phoenixR.setIngredient('x', Material.DIAMOND_SWORD);
        Bukkit.addRecipe(phoenixR);

        ShapedRecipe darkR = new ShapedRecipe(new NamespacedKey(this, "dark_sword"), items.darkSword());
        darkR.shape("aba", "bxb", "aba");
        darkR.setIngredient('a', Material.COAL_BLOCK);
        darkR.setIngredient('b', Material.GOLD_BLOCK);
        darkR.setIngredient('x', Material.DIAMOND_SWORD);
        Bukkit.addRecipe(darkR);

        ShapedRecipe sultR = new ShapedRecipe(new NamespacedKey(this, "sult_sword"), items.sultSword());
        sultR.shape("aba", "bxb", "aba");
        sultR.setIngredient('a', Material.ENCHANTED_GOLDEN_APPLE);
        sultR.setIngredient('b', Material.RABBIT_FOOT);
        sultR.setIngredient('x', Material.DIAMOND_SWORD);
        Bukkit.addRecipe(sultR);

        ShapedRecipe freeR = new ShapedRecipe(new NamespacedKey(this, "free_sword"), items.freeSword());
        freeR.shape("aba", "bxb", "aba");
        freeR.setIngredient('a', Material.EMERALD_BLOCK);
        freeR.setIngredient('b', Material.HEART_OF_THE_SEA);
        freeR.setIngredient('x', Material.DIAMOND_SWORD);
        Bukkit.addRecipe(freeR);

        ShapedRecipe overallR = new ShapedRecipe(new NamespacedKey(this, "best_sword"), items.bestSword());
        overallR.shape("aba", "cxd", "aea");
        overallR.setIngredient('a', Material.TOTEM_OF_UNDYING);
        overallR.setIngredient('x', Material.DRAGON_EGG);
        overallR.setIngredient('b', new RecipeChoice.ExactChoice(items.phoenixSword()));
        overallR.setIngredient('c', new RecipeChoice.ExactChoice(items.freeSword()));
        overallR.setIngredient('d', new RecipeChoice.ExactChoice(items.darkSword()));
        overallR.setIngredient('e', new RecipeChoice.ExactChoice(items.sultSword()));
        Bukkit.addRecipe(overallR);

        ItemStack kbStick = new ItemStack(Material.STICK);
        ItemMeta kbMeta = kbStick.getItemMeta();
        kbMeta.setItemName(ChatColor.BLACK + "Sigma Stick");
        kbStick.setItemMeta(kbMeta);
        kbStick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
        ShapedRecipe kbR = new ShapedRecipe(new NamespacedKey(this, "kb"), kbStick);
        kbR.shape("aba", "bcb", "aba");
        kbR.setIngredient('a', Material.DIRT);
        kbR.setIngredient('b', Material.COBBLESTONE);
        kbR.setIngredient('c', Material.STICK);
        Bukkit.addRecipe(kbR);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
