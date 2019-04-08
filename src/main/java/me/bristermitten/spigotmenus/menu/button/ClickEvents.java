package me.bristermitten.spigotmenus.menu.button;

public class ClickEvents {

    public static final NamedClickEvent DO_NOTHING = new NamedClickEvent("DO_NOTHING", e -> {

    });
    public static final NamedClickEvent CANCEL = new NamedClickEvent("CANCEL", e -> {
        e.setCancelled(true);
    });
    public static final NamedClickEvent NEXT_PAGE = new NamedClickEvent("NEXT_PAGE", e -> {
    });
    public static final NamedClickEvent PREVIOUS_PAGE = new NamedClickEvent("PREVIOUS_PAGE", e -> {
    });
}
