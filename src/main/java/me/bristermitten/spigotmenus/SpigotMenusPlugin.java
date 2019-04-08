package me.bristermitten.spigotmenus;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class SpigotMenusPlugin extends JavaPlugin {

    public SpigotMenusPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    public SpigotMenusPlugin() {
    }


    @Override
    public void onEnable() {
        getLogger().info("SpigotMenus plugin loaded - using this for MenuManager!");
        Bukkit.getPluginManager().registerEvents(new MenuListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
