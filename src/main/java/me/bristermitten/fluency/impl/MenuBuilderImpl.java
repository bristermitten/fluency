package me.bristermitten.fluency.impl;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.button.distribution.ButtonDistribution;
import me.bristermitten.fluency.menu.Menu;
import me.bristermitten.fluency.menu.MenuBuilder;

class MenuBuilderImpl implements MenuBuilder {
    private final Fluency fluency;
    private Menu menu;

    public MenuBuilderImpl(Fluency fluency) {
        this.fluency = fluency;
        this.menu = new Menu();
    }

    @Override
    public MenuBuilder distributed(ButtonDistribution distribution) {
        ButtonDistribution copy = distribution.copy();
        menu.distribution(copy);
        return this;
    }

    @Override
    public MenuBuilder title(String title) {
        menu.title(title);
        return this;
    }

    @Override
    public MenuBuilder size(int size) {
        menu.size(size);
        return this;
    }

    @Override
    public ButtonBuilder buildButton() {
        ButtonBuilder builder = fluency.buildButton(this);
        menu.addButton(builder.build());
        return builder;
    }

    @Override
    public MenuBuilder addButton(MenuButton button) {
        menu.addButton(button);
        return this;
    }

    @Override
    public MenuBuilder background(MenuButton button) {
        return null;
    }

    @Override
    public ButtonBuilder buildBackground() {
        return null;
    }

    @Override
    public void invalidate() {
        menu = null;
    }

    @Override
    public Menu build() {
        return menu;
    }
}
