package me.bristermitten.spigotmenus.menu.button;

import me.bristermitten.spigotmenus.menu.Menu;
import me.bristermitten.spigotmenus.menu.MenuClickEvent;

import java.util.LinkedList;

public class ClickEvents {

    public static final NamedClickEvent DO_NOTHING = new NamedClickEvent("DO_NOTHING", e -> {

    });

    public static final NamedClickEvent CANCEL = new NamedClickEvent("CANCEL", e -> {
        e.setCancelled(true);
    });

    public static final NamedClickEvent NEXT_PAGE = new NamedClickEvent("NEXT_PAGE", e -> moveToPage(e, 1));

    public static final NamedClickEvent PREVIOUS_PAGE = new NamedClickEvent("PREVIOUS_PAGE", e -> moveToPage(e, -1));


    private static void moveToPage(MenuClickEvent e, int pageChange) {
        CANCEL.accept(e);
        Menu menu = e.getMenu();
        LinkedList<Menu> pages = menu.getPages(); //if it's a page this will return the main menu's pages list
        int index = pages.indexOf(menu);
        Menu changed;
        try {
            changed = pages.get(index + pageChange);
        } catch (IndexOutOfBoundsException e1) { //in case the change goes out of the list
            return;
        }
        changed.open(e.getWhoClicked());
    }
}
