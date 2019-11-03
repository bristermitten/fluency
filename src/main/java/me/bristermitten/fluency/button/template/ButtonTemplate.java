package me.bristermitten.fluency.button.template;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.data.ButtonHolder;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ButtonTemplate<T> extends ButtonHolder {

    private final Fluency fluency;
    private Function<T, String> nameFunction;
    private Function<T, Material> typeFunction;
    private Function<T, List<String>> loreFunction;
    private Supplier<T> supplier;

    public ButtonTemplate(Fluency fluency) {
        this.fluency = fluency;
        nameFunction = any -> null;
        loreFunction = any -> Collections.emptyList();
        typeFunction = any -> Material.STONE;
    }

    public ButtonTemplate(Fluency fluency, Function<T, String> nameFunction,
                          Function<T, Material> typeFunction,
                          Function<T, List<String>> loreFunction) {
        this(fluency);

        this.nameFunction = nameFunction;
        this.typeFunction = typeFunction;
        this.loreFunction = loreFunction;
    }

    public Supplier<T> supplier() {
        return supplier;
    }

    public void supplier(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public MenuButton create(Fluency f, T t) {
        return create(f.buildButton(), t);
    }

    public MenuButton create(ButtonBuilder builder, T t) {
        return builder
                .name(nameFunction.apply(t))
                .lore(loreFunction.apply(t))
                .type(typeFunction.apply(t))
                .build();
    }


    public Function<T, List<String>> loreFunction() {
        return loreFunction;
    }

    public void loreFunction(Function<T, List<String>> loreFunction) {
        this.loreFunction = loreFunction;
    }

    public Function<T, String> nameFunction() {
        return nameFunction;
    }

    public void nameFunction(Function<T, String> nameFunction) {
        this.nameFunction = nameFunction;
    }

    public Function<T, Material> typeFunction() {
        return typeFunction;
    }

    public void typeFunction(Function<T, Material> typeFunction) {
        this.typeFunction = typeFunction;
    }


    @Override
    public MenuButton get() {
        if (!super.has())
            set(create(fluency, supplier.get()));
        return super.get();
    }
}
