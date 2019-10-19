package me.bristermitten.fluency.impl;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.button.template.ButtonTemplate;
import me.bristermitten.fluency.button.template.TemplateBuilder;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.Material;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class LinkedTemplateBuilder<T> implements TemplateBuilder<T> {
    private final MenuBuilder parent;
    private final List<TemplateBuilder<T>> templateBuilders;

    private final TemplateBuilder<T> internal;

    public LinkedTemplateBuilder(Fluency fluency, MenuBuilder parent, List<TemplateBuilder<T>> templateBuilders) {
        this.parent = parent;
        this.internal = fluency.buildTemplate(parent);
        templateBuilders.add(internal);
        this.templateBuilders = templateBuilders;
    }

    @Override
    public TemplateBuilder<T> nameFrom(Function<T, String> nameFunction) {
        for (TemplateBuilder<T> sub : templateBuilders) {
            sub.nameFrom(nameFunction);
        }
        return this;
    }

    @Override
    public TemplateBuilder<T> loreFrom(Function<T, List<String>> loreFunction) {
        for (TemplateBuilder<T> sub : templateBuilders) {
            sub.loreFrom(loreFunction);
        }
        return this;
    }

    @Override
    public TemplateBuilder<T> typeFrom(Function<T, Material> typeFunction) {
        for (TemplateBuilder<T> sub : templateBuilders) {
            sub.typeFrom(typeFunction);
        }
        return this;
    }

    @Override
    public TemplateBuilder<T> type(Material type) {
        for (TemplateBuilder<T> sub : templateBuilders) {
            sub.type(type);
        }
        return this;
    }

    @Override
    public TemplateBuilder<T> withObject(Supplier<T> objectSupplier) {
        for (TemplateBuilder<T> sub : templateBuilders) {
            sub.withObject(objectSupplier);
        }
        return this;
    }

    @Override
    public TemplateBuilder<T> withObject(T object) {
        for (TemplateBuilder<T> sub : templateBuilders) {
            sub.withObject(object);
        }
        return this;
    }

    @Override
    public void invalidate() {
        templateBuilders.clear();
    }

    @Override
    public ButtonTemplate<T> build() {
        return internal.build();
    }

    @Override
    public MenuBuilder done() {
        return parent;
    }
}
