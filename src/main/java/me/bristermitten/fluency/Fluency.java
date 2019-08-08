package me.bristermitten.fluency;

import org.bukkit.plugin.Plugin;

public class Fluency {
    private final Plugin plugin;

    public Fluency(Plugin plugin) {
        this.plugin = plugin;
    }

    public static Fluency create(Plugin plugin) {
        return new Fluency(plugin);
    }

    public MenuBuilder buildMenu() {
        return new MenuBuilderImpl(this);
    }
}
