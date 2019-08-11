package me.bristermitten.fluency.menu;

import me.bristermitten.fluency.FluentBuilder;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.button.distribution.ButtonDistribution;

public interface MenuBuilder extends FluentBuilder<Menu, MenuBuilder> {
    MenuBuilder distributed(ButtonDistribution distribution);

    MenuBuilder title(String title);

    MenuBuilder size(int size);

    ButtonBuilder buildButton();

    MenuBuilder addButton(MenuButton button);

    MenuBuilder background(MenuButton button);

    ButtonBuilder buildBackground();

    @Override
    default MenuBuilder done() {
        return this;
    }
}
