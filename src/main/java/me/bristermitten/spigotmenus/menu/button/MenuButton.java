package me.bristermitten.spigotmenus.menu.button;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Getter
public class MenuButton {
    private final ItemStack item;
    private final ClickEvent action;
}
