package me.bristermitten.fluency.impl;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.button.distribution.ButtonDistribution;
import me.bristermitten.fluency.button.template.TemplateBuilder;
import me.bristermitten.fluency.data.ButtonHolder;
import me.bristermitten.fluency.menu.Menu;
import me.bristermitten.fluency.menu.MenuBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

class MenuBuilderImpl implements MenuBuilder {
    private final Fluency fluency;
    private Menu menu;

    public MenuBuilderImpl(Fluency fluency) {
        this.fluency = fluency;
        this.menu = new Menu();
    }

    @Override
    public MenuBuilder distributed(Supplier<ButtonDistribution> distribution) {
        return distributed(distribution.get());
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
    public MenuBuilder maxStackSize(int maxStackSize) {
        menu.maxStackSize(maxStackSize);
        return this;
    }

    @Override
    public ButtonBuilder buildButton() {
        ButtonBuilder builder = fluency.buildButton(this);
        menu.addButton(builder.buildHolder());
        return builder;
    }

    @Override
    public <T> TemplateBuilder<T> buildTemplate() {
        TemplateBuilder<T> templateBuilder = fluency.buildTemplate(this);
        menu.addButton(templateBuilder.build());
        return templateBuilder;
    }

    @Override
    public <T> TemplateBuilder<T> buildTemplatesForEach(Iterable<T> iterable) {
        List<TemplateBuilder<T>> list = new ArrayList<>();
        for (T t : iterable) {
            TemplateBuilder<T> template = buildTemplate();
            template.withObject(t);
            list.add(template);
        }
        return new LinkedTemplateBuilder<>(fluency, this, list);
    }

    @Override
    public MenuBuilder addButton(MenuButton button) {
        menu.addButton(button);
        return this;
    }

    @Override
    public MenuBuilder addButton(ButtonHolder button) {
        menu.addButton(button);
        return this;
    }

    @Override
    public MenuBuilder background(MenuButton button) {
        menu.background(button);
        return this;
    }

    @Override
    public MenuBuilder skip() {
        menu.distribution().skip();
        return this;
    }

    @Override
    public ButtonBuilder buildBackground() {
        ButtonBuilder builder = fluency.buildButton(this);
        menu.background(builder.buildHolder());
        return builder;
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
