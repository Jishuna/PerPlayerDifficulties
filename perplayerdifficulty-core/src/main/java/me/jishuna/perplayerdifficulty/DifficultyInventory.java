package me.jishuna.perplayerdifficulty;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.jishlib.MessageHandler;
import me.jishuna.jishlib.inventory.CustomInventory;
import me.jishuna.jishlib.items.ItemBuilder;

public class DifficultyInventory extends CustomInventory {
    private final PerPlayerDifficulty plugin;
    private final Player player;

    public DifficultyInventory(PerPlayerDifficulty plugin, Player player) {
        super(Bukkit.createInventory(null, 54, MessageHandler.get("test")));
        this.plugin = plugin;
        this.player = player;

        populate();
    }

    private void populate() {
        DifficultyRegistry registry = this.plugin.getDifficultyRegistry();
        Difficulty selected = Utils.getSelectedDifficulty(this.player, registry);

        int index = 0;
        for (Difficulty difficulty : registry.getAllDifficulties()) {
            Material material = difficulty.equals(selected) ? Material.LIME_DYE : Material.GRAY_DYE;
            ItemStack difficultyItem = ItemBuilder.create(material).name(difficulty.getDisplayName()).lore(difficulty.getDescription()).build();

            addButton(index++, difficultyItem, event -> onClick(event, difficulty));
        }
    }

    private void onClick(InventoryClickEvent event, Difficulty difficulty) {
        if (event.getClick().isLeftClick()) {
            Utils.setDifficulty(event.getWhoClicked(), difficulty);
        } else if (event.getClick().isRightClick()) {
            CustomInventory inventory = new DifficultyInfoInventory(this.plugin, difficulty);
            Bukkit.getScheduler().runTask(this.plugin, () -> this.plugin.getInventoryManager().openInventory(event.getWhoClicked(), inventory));
        }
    }
}
