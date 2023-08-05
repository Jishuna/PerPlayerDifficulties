package me.jishuna.perplayerdifficulty.listener;

import java.util.Set;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import me.jishuna.jishlib.util.EventUtils;
import me.jishuna.perplayerdifficulty.Utils;
import me.jishuna.perplayerdifficulty.difficulty.Difficulty;
import me.jishuna.perplayerdifficulty.difficulty.DifficultyRegistry;
import me.jishuna.perplayerdifficulty.difficulty.EntityBehaviour;

public class MobListener implements Listener {
    private static final Set<TargetReason> NEUTRAL_REASONS = Set.of(TargetReason.TARGET_ATTACKED_ENTITY, TargetReason.TARGET_ATTACKED_NEARBY_ENTITY, TargetReason.TARGET_ATTACKED_OWNER);
    private final DifficultyRegistry registry;

    public MobListener(DifficultyRegistry registry) {
        this.registry = registry;
    }

    @EventHandler
    public void onEntityTarget(EntityTargetLivingEntityEvent event) {
        if (!(event.getTarget() instanceof Player player)) {
            return;
        }

        Difficulty diffculty = Utils.getSelectedDifficulty(player, this.registry);
        EntityBehaviour behaviour = Utils.getBehaviourOrDefault(event.getEntityType(), diffculty);

        if (behaviour == EntityBehaviour.PASSIVE || (behaviour == EntityBehaviour.NEUTRAL && !NEUTRAL_REASONS.contains(event.getReason()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (event instanceof EntityDamageByEntityEvent entityEvent) {
            handleDamageByEntity(entityEvent, player);
        }
    }

    private void handleDamageByEntity(EntityDamageByEntityEvent event, Player player) {
        Entity entity = EventUtils.getAttackingEntity(event);
        if (entity == null) {
            return;
        }

        Difficulty diffculty = Utils.getSelectedDifficulty(player, this.registry);
        double damageMultiplier = diffculty.getEntitySettings(entity.getType()).getDamageMultiplier();
        if (damageMultiplier == 1.0) {
            return;
        }

        if (damageMultiplier <= 0) {
            event.setCancelled(true);
            return;
        }

        event.setDamage(event.getDamage() * damageMultiplier);
    }
}
