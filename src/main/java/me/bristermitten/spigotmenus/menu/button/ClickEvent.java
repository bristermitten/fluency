package me.bristermitten.spigotmenus.menu.button;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.bristermitten.spigotmenus.menu.MenuClickEvent;

import java.util.UUID;
import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ClickEvent {

    private final String alias;
    private Consumer<MenuClickEvent> onClick;

    public ClickEvent(Consumer<MenuClickEvent> onClick) {
        this.alias = UUID.randomUUID().toString();
        this.onClick = onClick;
    }

    public void setOnClick(Consumer<MenuClickEvent> onClick) {
        if (onClick == null) {
            onClick = ClickEvents.DO_NOTHING.onClick;
        }
        this.onClick = onClick;
    }
}
