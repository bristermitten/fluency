package me.bristermitten.fluency.button;

import me.bristermitten.fluency.FluentBuilder;
import me.bristermitten.fluency.button.click.HandlerBuilder;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.Material;

public interface ButtonBuilder extends FluentBuilder<MenuButton, MenuBuilder> {
    ButtonBuilder amount(int amount);

    ButtonBuilder type(Material type);

    HandlerBuilder onClick();

    MenuButton build();
}
