package me.bristermitten.fluency.button.click;

import me.bristermitten.fluency.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuClickEvent extends InventoryClickEvent {
    private final Player whoClicked;
    private final Menu clicked;

    public MenuClickEvent(InventoryClickEvent event, Player whoClicked, Menu clicked) {
        super(event.getView(), event.getSlotType(), event.getSlot(), event.getClick(), event.getAction());
        this.whoClicked = whoClicked;
        this.clicked = clicked;
    }

    @Override
    @Deprecated
    public Player getWhoClicked() {
        return clicker();
    }

    public Player clicker() {
        return whoClicked;
    }

    public Menu menu() {
        return clicked;
    }

}
