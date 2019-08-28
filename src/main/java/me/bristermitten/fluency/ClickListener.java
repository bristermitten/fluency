package me.bristermitten.fluency;

import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.button.click.MenuClickEvent;
import me.bristermitten.fluency.menu.MenuHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e instanceof MenuClickEvent) return;
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (e.getClickedInventory() == null) return;
        if (!(e.getWhoClicked().getOpenInventory().getTopInventory() instanceof MenuHolder)) return;
        if (e.getClickedInventory().equals(e.getWhoClicked().getInventory())) {
            e.setCancelled(true);
            return;
        }

        MenuHolder holder = (MenuHolder) e.getClickedInventory().getHolder();
        if (holder == null) return;
        MenuClickEvent event = new MenuClickEvent(e, (Player) e.getWhoClicked(), holder.menu());
        MenuButton button = holder.menu().button(e.getSlot());
        if (button == null) return;
        button.handler().accept(event);
        e.setCancelled(event.isCancelled());
    }
}
