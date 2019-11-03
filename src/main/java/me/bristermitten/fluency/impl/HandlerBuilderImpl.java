package me.bristermitten.fluency.impl;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.Util;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.click.*;
import me.bristermitten.fluency.menu.Menu;
import org.bukkit.event.inventory.ClickType;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class HandlerBuilderImpl implements HandlerBuilder {
    private final Fluency fluency;
    private final ButtonBuilder parent;
    private final ActionList actions;
    private ClickAction current;

    private boolean hasHadAction;

    public HandlerBuilderImpl(Fluency fluency, ButtonBuilder parent) {
        this.fluency = fluency;
        this.parent = parent;
        actions = new ActionList();
    }

    private void addIfNecessary() {
        if (current == null) {
            current = new ClickAction();
            actions.add(current);
        }
    }

    @Override
    public HandlerBuilder cancel() {
        return action(Handlers.CANCEL);
    }

    @Override
    public HandlerBuilder closeMenu() {
        return action(e -> e.getWhoClicked().closeInventory());
    }

    @Override
    public HandlerBuilder openMenu(Menu m) {
        return action(e -> m.open(e.getWhoClicked()));
    }

    @Override
    public HandlerBuilder openMenu(Supplier<Menu> m) {
        return action(e -> m.get().open(e.getWhoClicked()));
    }

    @Override
    public HandlerBuilder sendMessage(String message) {
        return action(e -> e.getWhoClicked().sendMessage(Util.color(message)));
    }

    @Override
    public HandlerBuilder sendMessage(Function<MenuClickEvent, String> message) {
        return action(e -> e.getWhoClicked().sendMessage(Util.color(message.apply(e))));
    }

    @Override
    public HandlerBuilder action(ClickHandler event) {
        addIfNecessary();
        hasHadAction = true;
        current.addRun(event);
        return this;
    }

    @Override
    public HandlerBuilder nextPage() {
        addIfNecessary();
        current.addRun(Handlers.NEXT_PAGE);
        return this;
    }

    @Override
    public HandlerBuilder previousPage() {
        addIfNecessary();
        current.addRun(Handlers.PREVIOUS_PAGE);
        return this;
    }

    @Override
    public HandlerBuilder whenClickType(ClickType type) {
        return when(e -> e.getClick() == type);
    }

    @Override
    public HandlerBuilder when(boolean condition) {
        return when(() -> condition);
    }


    @Override
    public HandlerBuilder when(Supplier<Boolean> condition) {
        return when(e -> condition.get());
    }

    @Override
    public HandlerBuilder when(Predicate<MenuClickEvent> condition) {
        if (hasHadAction) {
            current = new ClickAction();
            current.addCondition(condition);
            actions.add(current);
            hasHadAction = false;
        } else {
            addIfNecessary();
            current.addCondition(condition);
        }
        return this;
    }

    @Override
    public HandlerBuilder otherwise() {
        current = current.copySwapConditions();
        actions.add(current);
        return this;
    }

    @Override
    public void invalidate() {
        actions.clear();
        current = null;
    }

    @Override
    public ActionList build() {
        return actions;
    }

    @Override
    public ButtonBuilder done() {
        return parent;
    }
}
