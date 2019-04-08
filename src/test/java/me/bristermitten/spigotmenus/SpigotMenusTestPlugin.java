package me.bristermitten.spigotmenus;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class SpigotMenusTestPlugin extends SpigotMenusPlugin {
    public SpigotMenusTestPlugin() {
    }

    public SpigotMenusTestPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }
}
