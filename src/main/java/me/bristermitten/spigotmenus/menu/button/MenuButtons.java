package me.bristermitten.spigotmenus.menu.button;

import org.bukkit.Material;

public class MenuButtons {
    public static final MenuButton NEXT_PAGE = new MenuButton(
            new ItemBuilder().setType(Material.EMERALD).setName("&a&lNext").build(),
            ClickEvents.NEXT_PAGE
    );
}
