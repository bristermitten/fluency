package me.bristermitten.fluency.impl;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.button.click.HandlerBuilder;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.Material;

class ButtonBuilderImpl implements ButtonBuilder {
    private final Fluency fluency;
    private final MenuBuilder parent;
    private MenuButton button;

    public ButtonBuilderImpl(Fluency fluency, MenuBuilder parent) {
        this.fluency = fluency;
        this.parent = parent;
        this.button = new MenuButton();
    }

    @Override
    public ButtonBuilder amount(int amount) {
        button.setAmount(amount);
        return this;
    }

    @Override
    public ButtonBuilder type(Material type) {
        button.setType(type);
        return this;
    }

    @Override
    public HandlerBuilder onClick() {
        HandlerBuilder handlerBuilder = fluency.buildHandler(this);
        button.handler(handlerBuilder.build());
        return handlerBuilder;
    }

    @Override
    public void invalidate() {
        button = null;
    }

    @Override
    public MenuButton build() {
        return button;
    }

    @Override
    public MenuBuilder done() {
        return parent;
    }
}
