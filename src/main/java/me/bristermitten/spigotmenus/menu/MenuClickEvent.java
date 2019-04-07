package me.bristermitten.spigotmenus.menu;

import lombok.Getter;
import me.bristermitten.spigotmenus.menu.button.MenuButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

@Getter
public class MenuClickEvent extends InventoryClickEvent {

    private final Menu menu;
    private final MenuButton clickedButton;
    private final Player whoClicked;

    public MenuClickEvent(InventoryClickEvent parent, Menu menu, MenuButton clickedButton) {
        super(parent.getView(), parent.getSlotType(), parent.getSlot(), parent.getClick(), parent.getAction());
        this.menu = menu;
        this.clickedButton = clickedButton;
        this.whoClicked = (Player) parent.getWhoClicked();
    }
}
