package me.jishuna.perplayerdifficulty.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jishuna.jishlib.commands.SimpleCommandHandler;
import me.jishuna.perplayerdifficulty.PerPlayerDifficulty;
import me.jishuna.perplayerdifficulty.inventory.DifficultyInventory;

public class DifficultyCommand extends SimpleCommandHandler {
    private final PerPlayerDifficulty plugin;

    public DifficultyCommand(PerPlayerDifficulty plugin) {
        super("perplayerdifficulty.difficulty");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        DifficultyInventory inventory = new DifficultyInventory(this.plugin, player);
        this.plugin.getInventoryManager().openInventory(player, inventory);
        return true;
    }

}
