package me.bristermitten.spigotmenus.menu.button.builder;

import me.bristermitten.spigotmenus.menu.Menu;
import me.bristermitten.spigotmenus.menu.button.ItemBuilder;
import me.bristermitten.spigotmenus.menu.button.MenuButton;
import org.bukkit.inventory.ItemStack;

/**
 * Builder class for creating a {@link Menu}
 * As with {@link ItemBuilder}, this is different to a traditional builder pattern
 * in that it builds the menu on the fly, taking advantage of the mutability.
 */
public class MenuBuilder {
    private Menu menu;
    private String title;
    private Integer size;

    public MenuBuilder(String title, int size) {
        this.title = title;
        this.size = size;
        tryCreate();
    }


    public MenuBuilder addButton(MenuButton button) {
        menu.addButton(button);
        return this;
    }

    public MenuBuilder addButton(MenuButton button, int slot) {
        menu.addButton(button, slot);
        return this;
    }


    public MenuButtonBuilder buildButton() {
        return new MenuButtonBuilder(this);
    }

    public MenuButtonBuilder buildButton(ItemStack item) {
        return new MenuButtonBuilder(this, item);
    }

    public MenuButtonBuilder buildButton(ItemStack item, boolean clone) {
        return new MenuButtonBuilder(this, item, clone);
    }

    public MenuButtonBuilder buildButton(int slot) {
        return new MenuButtonBuilder(this, slot);
    }

    private void tryCreate() {
        if (menu == null) { //only if called from constructor
            if (title == null || size == null) {
                return;
            }
            menu = new Menu(title, size);
        }
        menu.setTitle(title);
        menu.setSize(size);
    }

    public MenuBuilder setTitle(String title) {
        menu.setTitle(title);
        return this;
    }

    public MenuBuilder setSize(int size) {
        menu.setSize(size);
        return this;
    }

    public Menu build() {
        return menu;
    }
}
