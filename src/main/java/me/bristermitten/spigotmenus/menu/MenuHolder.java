package me.bristermitten.spigotmenus.menu;

import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@Getter
public class MenuHolder implements InventoryHolder {

    private final Menu menu;
    public MenuHolder(Menu menu) {
        this.menu = menu;
    }

    @Override
    public Inventory getInventory() {
        return menu.inventory;
    }
}
