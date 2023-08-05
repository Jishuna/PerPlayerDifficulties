package me.jishuna.perplayerdifficulty;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.jishlib.inventory.CustomInventory;

public class DifficultyInfoInventory extends CustomInventory {
    private final PerPlayerDifficulty plugin;
    private final Difficulty difficulty;

    public DifficultyInfoInventory(PerPlayerDifficulty plugin, Difficulty difficulty) {
        super(Bukkit.createInventory(null, 54, "test"));
       this.plugin = plugin;
       this.difficulty = difficulty;
       
       addClickConsumer(event -> event.setCancelled(true));
       populate();
    }
    
    private void populate() {
        addButton(10, new ItemStack(Material.CREEPER_SPAWN_EGG), this::showEntitySettings);
    }
    
    private void showEntitySettings(InventoryClickEvent event) {
        EntitySettingsInventory inventory = new EntitySettingsInventory(this.difficulty);
        Bukkit.getScheduler().runTask(this.plugin, () -> this.plugin.getInventoryManager().openInventory(event.getWhoClicked(), inventory));
    }
}
