package me.bristermitten.fluency;

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
        if (!(e.getClickedInventory().getHolder() instanceof MenuHolder)) {
            return;
        }
        MenuHolder holder = (MenuHolder) e.getClickedInventory().getHolder();
        MenuClickEvent event = new MenuClickEvent(e, (Player) e.getWhoClicked(), holder.menu());
        holder.menu().button(e.getSlot()).handler().accept(event);
    }
}
