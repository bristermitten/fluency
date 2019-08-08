package me.bristermitten.fluency;

import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.button.MenuButtonBuilder;
import me.bristermitten.fluency.button.distribution.ButtonDistribution;

import java.util.HashMap;
import java.util.Map;

public class MenuBuilderImpl implements MenuBuilder {
    private final Fluency fluency;
    private final Map<Integer, MenuButton> buttons = new HashMap<>();
    private ButtonDistribution distribution;
    private int size = 6 * 9;
    private String title = "Title";

    public MenuBuilderImpl(Fluency fluency) {
        this.fluency = fluency;
    }

    @Override
    public MenuBuilder distributed(ButtonDistribution distribution) {
        ButtonDistribution copy = distribution.copy();
        copy.init(size);
        this.distribution = copy;
        return this;
    }

    @Override
    public MenuBuilder title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public MenuBuilder size(int size) {
        this.size = size;
        return this;
    }

    @Override
    public MenuButtonBuilder buildButton() {
        return null;
    }
}
