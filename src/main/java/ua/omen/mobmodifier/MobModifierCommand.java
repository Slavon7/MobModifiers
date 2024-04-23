package ua.omen.mobmodifier;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobModifierCommand {
    private MobModifier plugin;
    private Map<EntityType, List<ItemChancePair>> customDrops;

    public MobModifierCommand(MobModifier plugin, FileConfiguration config) {
        this.plugin = plugin;
        customDrops = new HashMap<>();

        // Загрузка настроек для каждого моба из конфигурации
        ConfigurationSection mobsSection = config.getConfigurationSection("Mobs");
        if (mobsSection != null) {
            for (String mobName : mobsSection.getKeys(false)) {
                EntityType entityType = EntityType.fromName(mobName.toUpperCase());
                if (entityType != null) {
                    List<ItemChancePair> itemChances = loadItemChancesFromConfig(mobsSection.getConfigurationSection(mobName));
                    customDrops.put(entityType, itemChances);
                }
            }
        }
    }

    public void processEntityDeath(EntityType entityType, EntityDeathEvent event) {
        if (customDrops.containsKey(entityType)) {
            List<ItemChancePair> itemChances = customDrops.get(entityType);
            event.getDrops().clear();
            for (ItemChancePair itemChancePair : itemChances) {
                ItemStack drop = itemChancePair.getItemStack();
                int dropChance = itemChancePair.getChance();
                if (Math.random() * 100.0 < dropChance) {
                    event.getDrops().add(drop.clone());
                }
            }
        }
    }

    private List<ItemChancePair> loadItemChancesFromConfig(ConfigurationSection configSection) {
        List<ItemChancePair> itemChances = new ArrayList<>();

        for (String itemString : configSection.getStringList("Items")) {
            String[] parts = itemString.split(":");
            if (parts.length == 2) {
                String itemName = parts[0].trim().toUpperCase();
                int dropChance = Integer.parseInt(parts[1].trim());
                ItemStack itemStack = new ItemStack(Material.valueOf(itemName));
                itemChances.add(new ItemChancePair(itemStack, dropChance));
            }
        }

        return itemChances;
    }
}