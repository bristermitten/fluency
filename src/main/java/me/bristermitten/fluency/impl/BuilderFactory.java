package me.bristermitten.fluency.impl;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.click.HandlerBuilder;
import me.bristermitten.fluency.button.template.TemplateBuilder;
import me.bristermitten.fluency.menu.MenuBuilder;

public class BuilderFactory {
    private final Fluency fluency;

    public BuilderFactory(Fluency fluency) {
        this.fluency = fluency;
    }

    public MenuBuilder buildMenu() {
        return new MenuBuilderImpl(fluency);
    }

    public ButtonBuilder buildButton() {
        return buildButton(null);
    }

    public ButtonBuilder buildButton(MenuBuilder parent) {
        return new ButtonBuilderImpl(fluency, parent);
    }

    public HandlerBuilder buildHandler(ButtonBuilder parent) {
        return new HandlerBuilderImpl(fluency, parent);
    }

    public <T> TemplateBuilder<T> buildTemplate(MenuBuilder parent) {
        return new TemplateBuilderImpl<>(fluency, parent);
    }
}
