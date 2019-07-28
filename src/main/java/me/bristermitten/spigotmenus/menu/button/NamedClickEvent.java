package me.bristermitten.spigotmenus.menu.button;

import me.bristermitten.spigotmenus.menu.MenuClickEvent;

import java.util.UUID;
import java.util.function.Consumer;

public class NamedClickEvent implements Consumer<MenuClickEvent> {
    private final String alias;
    private Consumer<MenuClickEvent> onClick;

    public NamedClickEvent(String alias, Consumer<MenuClickEvent> onClick) {
        this.alias = alias;
        this.onClick = onClick;
    }

    public NamedClickEvent(Consumer<MenuClickEvent> onClick) {
        this.alias = UUID.randomUUID().toString();
        this.onClick = onClick;
    }

    public String getAlias() {
        return alias;
    }

    public Consumer<MenuClickEvent> getOnClick() {
        return onClick;
    }

    public void setOnClick(Consumer<MenuClickEvent> onClick) {
        if (onClick == null) {
            onClick = ClickEvents.DO_NOTHING.onClick;
        }
        this.onClick = onClick;
    }

    @Override
    public void accept(MenuClickEvent menuClickEvent) {
        onClick.accept(menuClickEvent);
    }
}
