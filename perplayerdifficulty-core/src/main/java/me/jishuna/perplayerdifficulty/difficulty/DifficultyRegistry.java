package me.jishuna.perplayerdifficulty.difficulty;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import me.jishuna.jishlib.config.ConfigurationManager;

public class DifficultyRegistry {
    private final Map<String, Difficulty> registryMap = new LinkedHashMap<>();

    public void registerFromFile(ConfigurationManager manager, File file) {
        Difficulty difficulty = new Difficulty();
        manager.createReloadable(file, difficulty).saveDefaults().load();
        this.registryMap.put(difficulty.getName(), difficulty);
    }

    public Difficulty getDifficulty(String name) {
        if (name == null) {
            return null;
        }
        return this.registryMap.get(name);
    }

    public Difficulty getFirstDifficulty() {
        return this.registryMap.values().stream().findFirst().orElse(null);
    }

    public int getDifficultyCount() {
        return this.registryMap.size();
    }

    public Collection<Difficulty> getAllDifficulties() {
        return Collections.unmodifiableCollection(this.registryMap.values());
    }
}
