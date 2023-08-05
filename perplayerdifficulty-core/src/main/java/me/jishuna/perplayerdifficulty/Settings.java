package me.jishuna.perplayerdifficulty;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.config.annotation.PostLoad;

public class Settings {
    @ConfigEntry("default-difficulty")
    public static String defaultDifficultyName = "normal";

    public static Difficulty defaultDifficulty;

    @PostLoad
    public static void postLoad() {
        PerPlayerDifficulty plugin = JavaPlugin.getPlugin(PerPlayerDifficulty.class);
        defaultDifficulty = plugin.getDifficultyRegistry().getDifficulty(defaultDifficultyName);

        // Default is invalid
        if (defaultDifficulty == null) {
            defaultDifficulty = plugin.getDifficultyRegistry().getFirstDifficulty();

            // No backup found
            if (defaultDifficulty == null) {
                plugin.getLogger().log(Level.SEVERE, "The specified default difficulty {0} could not be found!", defaultDifficultyName);
            } else {
                plugin.getLogger().log(Level.WARNING, "The specified default difficulty {0} could not be found, falling back to {1}", new Object[] { defaultDifficultyName, defaultDifficulty.getName() });
            }
        }
    }
}
