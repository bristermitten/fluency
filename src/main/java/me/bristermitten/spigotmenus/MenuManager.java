package me.bristermitten.spigotmenus;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MenuManager {

    private final JavaPlugin plugin;

    public MenuManager(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new MenuListener(plugin), plugin);
    }
}
