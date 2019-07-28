package me.bristermitten.spigotmenus.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuHolder implements InventoryHolder {
    private final Menu menu;

    public MenuHolder(Menu menu) {
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }

    @Override
    public Inventory getInventory() {
        return menu.inventory;
    }
}
