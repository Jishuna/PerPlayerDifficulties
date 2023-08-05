package me.jishuna.perplayerdifficulty;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.jishlib.MessageHandler;
import me.jishuna.jishlib.inventory.PagedCustomInventory;
import me.jishuna.jishlib.items.ItemBuilder;
import me.jishuna.jishlib.items.MobHead;
import me.jishuna.jishlib.util.StringUtils;

public class EntitySettingsInventory extends PagedCustomInventory<EntityType> {
    private static final List<EntityType> TYPES = Arrays.stream(EntityType.values()).filter(Utils::isEnemyType).sorted((a, b) -> a.getKey().getKey().compareTo(b.getKey().getKey())).toList();
    private static final ItemStack FILLER = ItemBuilder.create(Material.GRAY_STAINED_GLASS_PANE).name(" ").build();
    
    private final Difficulty difficulty;
    
    protected EntitySettingsInventory(Difficulty difficulty) {
        super(Bukkit.createInventory(null, 54, "test"), TYPES, 45);
        this.difficulty = difficulty;

        addClickConsumer(event -> event.setCancelled(true));
        populate();
    }

    private void populate() {
        addButton(45, new ItemStack(Material.ARROW), event -> super.changePage(-1));
        addButton(53, new ItemStack(Material.ARROW), event -> super.changePage(1));
        
        for (int i = 46; i <= 52; i++) {
            setItem(i, FILLER);
        }

        super.refreshOptions();
    }

    @Override
    protected ItemStack asItemStack(EntityType type) {
        String name = MessageHandler.get("entity-settings.name", StringUtils.capitalizeAll(type.getKey().getKey().replace("_", " ")));
        int multiplier = (int) (this.difficulty.getEntitySettings(type).getDamageMultiplier() * 100);
        String behaviour = MessageHandler.get("behaviour." + Utils.getBehaviourOrDefault(type, this.difficulty).name().toLowerCase());
        
        ItemStack item = MobHead.forType(type);

        if (item == null) {
            item = new ItemStack(Material.SPAWNER);
        }

        return ItemBuilder.modifyClone(item)
                .name(name)
                .lore(MessageHandler.getList("entity-settings.description", multiplier, behaviour))
                .build();
    }

    @Override
    protected void onItemClicked(InventoryClickEvent event, EntityType type) {
        // NO-OP
    }

}
