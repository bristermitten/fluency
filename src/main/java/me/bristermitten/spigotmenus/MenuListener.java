package me.bristermitten.spigotmenus;

import me.bristermitten.spigotmenus.menu.Menu;
import me.bristermitten.spigotmenus.menu.MenuClickEvent;
import me.bristermitten.spigotmenus.menu.MenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class MenuListener implements Listener {
    private static boolean registered;

    private final JavaPlugin plugin;

    public MenuListener(JavaPlugin plugin) {
        this.plugin = plugin;
        if (!registered) {
            Bukkit.getPluginManager().registerEvents(this, plugin);
            registered = true;
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e instanceof MenuClickEvent) {
            return;
        }
        Inventory clickedInventory = e.getClickedInventory();
        if (clickedInventory != null && clickedInventory.getHolder() instanceof MenuHolder) {
            MenuClickEvent event = convertEvent(e);
            Bukkit.getPluginManager().callEvent(event);
            if (event.getClickedButton() == null) {
                return;
            }
            event.getClickedButton().getOnClick().accept(event);
            if (event.isCancelled()) {
                e.setCancelled(true);
            }
        }
    }

    private MenuClickEvent convertEvent(InventoryClickEvent e) {
        MenuHolder holder = (MenuHolder) e.getInventory().getHolder();
        Menu menu = holder.getMenu();
        return new MenuClickEvent(e, menu, menu.getButton(e.getSlot()));
    }
}
