package ua.omen.mobmodifier;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import org.bukkit.plugin.java.JavaPlugin;

public class MobModifier extends JavaPlugin implements Listener {
    private MobModifierCommand mobModifierCommand;
    private FileConfiguration config;

    public void onEnable() {
        getLogger().info("Plugin MobModifier is loaded");

        // Загрузка или создание конфигурационного файла
        config = getConfig();
        config.options().copyDefaults(true);
        saveDefaultConfig();

        // Инициализация mobModifierCommand
        mobModifierCommand = new MobModifierCommand(this, config);

        // Регистрация команды /mobmodifier reload и настройка ее обработчика
        getCommand("mobmodifier").setExecutor(new AllCommands(this));
        // Регистрация команды /mobmodifier help и настройка ее обработчика

        // Регистрация обработчика событий
        getServer().getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        EntityType entityType = event.getEntityType();
        mobModifierCommand.processEntityDeath(entityType, event);
    }
}