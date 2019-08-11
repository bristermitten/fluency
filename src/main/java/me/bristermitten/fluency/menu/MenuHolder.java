package me.bristermitten.fluency.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuHolder implements InventoryHolder {
    private final Menu menu;

    public MenuHolder(Menu menu) {
        this.menu = menu;
    }

    @Override
    public Inventory getInventory() {
        return menu.inventory;
    }

    public Menu menu() {
        return menu;
    }
}
