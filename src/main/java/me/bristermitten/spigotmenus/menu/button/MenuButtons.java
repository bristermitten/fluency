package me.bristermitten.spigotmenus.menu.button;

import org.bukkit.Material;

public class MenuButtons {
    public static final MenuButton NEXT_PAGE = new MenuButton(
            new ItemBuilder().setType(Material.EMERALD).setName("&a&lNext Page").build(),
            ClickEvents.NEXT_PAGE
    );
    public static final MenuButton PREVIOUS_PAGE = new MenuButton(
            new ItemBuilder().setType(Material.REDSTONE).setName("&c&lPrevious Page").build(),
            ClickEvents.PREVIOUS_PAGE
    );
}
