package me.bristermitten.fluency.impl;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.Util;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.click.*;
import me.bristermitten.fluency.menu.Menu;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class HandlerBuilderImpl implements HandlerBuilder {
    private final Fluency fluency;
    private final ButtonBuilder parent;
    private final ActionList actions;
    private ClickAction current;

    public HandlerBuilderImpl(Fluency fluency, ButtonBuilder parent) {
        this.fluency = fluency;
        this.parent = parent;
        actions = new ActionList();
        current = new ClickAction();
        actions.add(current);
    }

    @Override
    public HandlerBuilder cancel() {
        current.addRun(Handlers.CANCEL);
        return this;
    }

    @Override
    public HandlerBuilder closeMenu() {
        current.addRun(e -> e.getWhoClicked().closeInventory());
        return this;
    }

    @Override
    public HandlerBuilder openMenu(Menu m) {
        current.addRun(e -> m.open(e.getWhoClicked()));
        return this;
    }

    @Override
    public HandlerBuilder openMenu(Supplier<Menu> m) {
        current.addRun(e -> m.get().open(e.getWhoClicked()));
        return this;
    }

    @Override
    public HandlerBuilder sendMessage(String message) {
        current.addRun(e -> e.getWhoClicked().sendMessage(Util.color(message)));
        return this;
    }

    @Override
    public HandlerBuilder sendMessage(Function<MenuClickEvent, String> message) {
        current.addRun(e -> e.getWhoClicked().sendMessage(Util.color(message.apply(e))));
        return this;
    }

    @Override
    public HandlerBuilder action(ClickHandler event) {
        current.addRun(event);
        return this;
    }

    @Override
    public HandlerBuilder nextPage() {
        current.addRun(Handlers.NEXT_PAGE);
        return this;
    }

    @Override
    public HandlerBuilder previousPage() {
        current.addRun(Handlers.PREVIOUS_PAGE);
        return this;
    }

    @Override
    public HandlerBuilder when(boolean condition) {
        current = new ClickAction();
        actions.add(current);
        current.addCondition(e -> condition);
        return this;
    }

    @Override
    public HandlerBuilder when(Supplier<Boolean> condition) {
        current = new ClickAction();
        actions.add(current);
        current.addCondition(e -> condition.get());
        return this;
    }

    @Override
    public HandlerBuilder when(Predicate<MenuClickEvent> condition) {
        current = new ClickAction();
        actions.add(current);
        current.addCondition(condition);
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
    public ClickHandler build() {
        return actions;
    }

    @Override
    public ButtonBuilder done() {
        return parent;
    }
}
