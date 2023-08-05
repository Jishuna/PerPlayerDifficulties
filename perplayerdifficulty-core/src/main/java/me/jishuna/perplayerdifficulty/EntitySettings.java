package me.jishuna.perplayerdifficulty;

import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.config.annotation.ConfigMappable;

@ConfigMappable
public class EntitySettings {
    @ConfigEntry("damage-multiplier")
    private double damage = 1;

    @ConfigEntry("behaviour")
    private EntityBehaviour behaviour;

    public double getDamageMultiplier() {
        return damage;
    }

    public EntityBehaviour getBehaviour() {
        return behaviour;
    }

}
