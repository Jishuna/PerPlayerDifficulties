package me.jishuna.perplayerdifficulty;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.jishlib.MessageHandler;
import me.jishuna.jishlib.config.ConfigurationManager;
import me.jishuna.jishlib.inventory.CustomInventoryListener;
import me.jishuna.jishlib.inventory.CustomInventoryManager;
import me.jishuna.jishlib.util.FileUtils;

public class PerPlayerDifficulty extends JavaPlugin {
    private ConfigurationManager configurationManager;
    private DifficultyRegistry difficultyRegistry;
    private CustomInventoryManager inventoryManager;

    @Override
    public void onEnable() {
        this.configurationManager = new ConfigurationManager(this);
        this.inventoryManager = new CustomInventoryManager();
        MessageHandler.initalize(this.configurationManager, new File(getDataFolder(), "message.yml"), getResource("messages.yml"));

        reload();

        Bukkit.getPluginManager().registerEvents(new CustomInventoryListener(this.inventoryManager), this);
        Bukkit.getPluginManager().registerEvents(new MobListener(this.difficultyRegistry), this);

        getCommand("difficulty").setExecutor(new DifficultyCommand(this));
    }

    public void reload() {
        this.difficultyRegistry = new DifficultyRegistry();

        loadDifficulties();
        loadSettings();
    }

    private void loadDifficulties() {
        File difficultyFolder = new File(getDataFolder(), "difficulties");

        if (!difficultyFolder.exists()) {
            difficultyFolder.mkdirs();
            // TODO defaults
        }
        FileUtils.visitFiles(difficultyFolder, FileUtils.YML_FILE_FILTER, file -> this.difficultyRegistry.registerFromFile(this.configurationManager, file));
    }

    private void loadSettings() {
        this.configurationManager.createStaticReloadable(new File(getDataFolder(), "config.yml"), Settings.class).saveDefaults().load();
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    public DifficultyRegistry getDifficultyRegistry() {
        return difficultyRegistry;
    }

    public CustomInventoryManager getInventoryManager() {
        return inventoryManager;
    }
}
