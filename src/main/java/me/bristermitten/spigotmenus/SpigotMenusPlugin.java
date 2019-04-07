package me.bristermitten.spigotmenus;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpigotMenusPlugin extends JavaPlugin {

    @Getter
    private static MenuManager menuManager;


    @Override
    public void onEnable() {
        getLogger().info("SpigotMenus plugin loaded - using this for MenuManager!");
        menuManager = new MenuManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
