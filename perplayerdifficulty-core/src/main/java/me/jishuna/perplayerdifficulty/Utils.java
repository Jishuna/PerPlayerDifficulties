package me.jishuna.perplayerdifficulty;

import java.util.Set;

import org.bukkit.entity.Enemy;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;

import me.jishuna.jishlib.pdc.PersistentTypes;
import me.jishuna.perplayerdifficulty.difficulty.Difficulty;
import me.jishuna.perplayerdifficulty.difficulty.DifficultyRegistry;
import me.jishuna.perplayerdifficulty.difficulty.EntityBehaviour;

public class Utils {
    private static final Set<EntityType> NEUTRAL_ENTITIES = Set.of(EntityType.ENDERMAN, EntityType.ZOMBIFIED_PIGLIN, EntityType.WOLF, EntityType.DOLPHIN, EntityType.BEE, EntityType.GOAT, EntityType.POLAR_BEAR, EntityType.IRON_GOLEM, EntityType.LLAMA, EntityType.TRADER_LLAMA, EntityType.PANDA);

    public static Difficulty getSelectedDifficulty(HumanEntity player, DifficultyRegistry registry) {
        String name = player.getPersistentDataContainer().get(Constants.DIFFICULTY_KEY, PersistentTypes.STRING);
        Difficulty difficulty = registry.getDifficulty(name);

        if (difficulty == null) {
            return Settings.defaultDifficulty;
        }
        return difficulty;
    }

    public static void setDifficulty(HumanEntity player, Difficulty difficulty) {
        player.getPersistentDataContainer().set(Constants.DIFFICULTY_KEY, PersistentTypes.STRING, difficulty.getName());
    }

    public static boolean isEnemyType(EntityType type) {
        if (type.getEntityClass() == null) {
            return false;
        }
        return Enemy.class.isAssignableFrom(type.getEntityClass()) || NEUTRAL_ENTITIES.contains(type);
    }

    public static EntityBehaviour getBehaviourOrDefault(EntityType type, Difficulty difficulty) {
        EntityBehaviour behaviour = difficulty.getEntitySettings(type).getBehaviour();

        if (behaviour == null) {
            behaviour = getDefaultBehaviour(type);
        }
        return behaviour;
    }

    public static EntityBehaviour getDefaultBehaviour(EntityType type) {
        if (NEUTRAL_ENTITIES.contains(type)) {
            return EntityBehaviour.NEUTRAL;
        }
        return EntityBehaviour.HOSTILE;
    }
}
