package me.jishuna.perplayerdifficulty.difficulty;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.EntityType;

import com.google.common.base.Objects;

import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;

public class Difficulty {
    private static final EntitySettings DEFAULT_SETTINGS = new EntitySettings();

    @Comment("\n# Settings for each individual type of entity.")
    @ConfigEntry("entity-settings")
    private Map<EntityType, EntitySettings> entitySettings = new LinkedHashMap<>();

    @Comment("\n# Prevents all hunger loss.")
    @ConfigEntry("disable-hunger-loss")
    private boolean hungerLossDisabled = false;

    @Comment("\n# The description of this difficulty in the difficulty selector.")
    @ConfigEntry("description")
    private List<String> description = new ArrayList<>();

    @Comment("\n# The name of this difficulty in the difficulty selector.")
    @ConfigEntry("display-name")
    private String displayName = "";

    @Comment("The internal name of this difficulty.")
    @ConfigEntry("name")
    private String name = "";

    public String getName() {
        return this.name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getDescription() {
        return description;
    }

    public EntitySettings getEntitySettings(EntityType type) {
        return this.entitySettings.getOrDefault(type, DEFAULT_SETTINGS);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Difficulty other) {
            return Objects.equal(this.name, other.name);
        }
        return false;
    }
}