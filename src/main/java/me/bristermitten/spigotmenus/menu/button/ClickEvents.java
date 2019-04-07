package me.bristermitten.spigotmenus.menu.button;

public class ClickEvents {

    public static final ClickEvent DO_NOTHING = new ClickEvent("DO_NOTHING", e -> {
    });
    public static final ClickEvent CANCEL = new ClickEvent("CANCEL", e -> {
        e.setCancelled(true);
    });
    public static final ClickEvent NEXT_PAGE = new ClickEvent("NEXT_PAGE", e -> {

    });
}
