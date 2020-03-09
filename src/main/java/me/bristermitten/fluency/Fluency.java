package me.bristermitten.fluency;

import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.button.click.HandlerBuilder;
import me.bristermitten.fluency.button.template.TemplateBuilder;
import me.bristermitten.fluency.implementation.BuilderFactory;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

public class Fluency {
    public static MenuButton PAGE_NEXT;
    public static MenuButton PAGE_PREVIOUS;
    private final Plugin plugin;
    private final BuilderFactory factory;

    private Fluency(Plugin plugin) {
        this.plugin = plugin;
        this.factory = new BuilderFactory(this);

        Bukkit.getPluginManager().registerEvents(new ClickListener(), plugin);

        PAGE_PREVIOUS = buildButton().type(Material.REDSTONE)
                .name("&cPrevious Page")
                .onClick().previousPage().done().build();
        PAGE_NEXT = buildButton().type(Material.EMERALD)
                .name("&aNext Page")
                .onClick().nextPage().done().build();
    }

    public static Fluency create(Plugin plugin) {
        return new Fluency(plugin);
    }

    public MenuBuilder buildMenu() {
        return factory.buildMenu();
    }

    public ButtonBuilder buildButton(MenuBuilder parent) {
        return factory.buildButton(parent);
    }

    public ButtonBuilder buildButton() {
        return factory.buildButton();
    }

    public HandlerBuilder buildHandler(ButtonBuilder parent) {
        return factory.buildHandler(parent);
    }

    public <T>TemplateBuilder<T> buildTemplate(MenuBuilder parent){
        return factory.buildTemplate(parent);
    }
}
