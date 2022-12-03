package io.github.rysefoxx.example;

import io.github.rysefoxx.example.command.InventoryCommand;
import io.github.rysefoxx.example.listener.InventoryOpenListener;
import io.github.rysefoxx.inventory.plugin.pagination.InventoryManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Rysefoxx | Rysefoxx#7880
 * @since 12/3/2022
 */
public class ExamplePlugin extends JavaPlugin {

    @Getter
    private final InventoryManager manager = new InventoryManager(this);

    @Override
    public void onEnable() {
        getLogger().info("ExamplePlugin has been enabled!");
        manager.invoke();

        init();
    }

    @Override
    public void onDisable() {
        getLogger().info("ExamplePlugin has been disabled!");
    }

    private void init() {
        registerCommands();
        registerEvents();
    }

    private void registerCommands() {
        getCommand("inventory").setExecutor(new InventoryCommand(this));
    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryOpenListener(this), this);
    }
}
