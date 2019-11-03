package me.bristermitten.fluency.menu;

import me.bristermitten.fluency.FluentBuilder;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.button.distribution.ButtonDistribution;
import me.bristermitten.fluency.button.template.TemplateBuilder;
import me.bristermitten.fluency.data.ButtonHolder;

import java.util.function.Supplier;

public interface MenuBuilder extends FluentBuilder<Menu, MenuBuilder> {
    MenuBuilder distributed(ButtonDistribution distribution);

    MenuBuilder distributed(Supplier<ButtonDistribution> distribution);

    MenuBuilder title(String title);

    MenuBuilder size(int size);

    MenuBuilder maxStackSize(int maxStackSize);

    ButtonBuilder buildButton();

    <T> TemplateBuilder<T> buildTemplate();

    <T> TemplateBuilder<T> buildTemplatesForEach(Iterable<T> iterable);

    MenuBuilder addButton(MenuButton button);

    MenuBuilder addButton(ButtonHolder button);

    MenuBuilder background(MenuButton button);

    MenuBuilder skip();

    ButtonBuilder buildBackground();

    @Override
    default MenuBuilder done() {
        return this;
    }
}
