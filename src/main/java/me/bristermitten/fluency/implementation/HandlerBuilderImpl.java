package me.bristermitten.fluency.implementation;

import me.bristermitten.fluency.FluentBuilder;
import me.bristermitten.fluency.Util;
import me.bristermitten.fluency.button.click.*;
import me.bristermitten.fluency.menu.Menu;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class HandlerBuilderImpl<P extends FluentBuilder<?, ?>> implements HandlerBuilder<P> {
	private final P parent;
	private final ActionList actions;
	private ClickAction current;

	public HandlerBuilderImpl(@Nullable P parent) {
		this.parent = parent;
		actions = new ActionList();
	}

	@Override
	public HandlerBuilder<P> cancel() {
		addIfNecessary();
		current.addRun(Handlers.CANCEL);
		return this;
	}

	private void addIfNecessary() {
		if (current == null) {
			current = new ClickAction();
			actions.add(current);
		}
	}

	@Override
	public HandlerBuilder<P> closeMenu() {
		addIfNecessary();
		current.addRun(e -> e.clicker().closeInventory());
		return this;
	}

	@Override
	public HandlerBuilder<P> openMenu(Menu m) {
		addIfNecessary();
		current.addRun(e -> m.open(e.clicker()));
		return this;
	}

	@Override
	public HandlerBuilder<P> openMenu(Supplier<Menu> m) {
		addIfNecessary();
		current.addRun(e -> m.get().open(e.clicker()));
		return this;
	}

	@Override
	public HandlerBuilder<P> sendMessage(String message) {
		addIfNecessary();
		current.addRun(e -> e.clicker().sendMessage(Util.color(message)));
		return this;
	}

	@Override
	public HandlerBuilder<P> sendMessage(Function<MenuClickEvent, String> message) {
		addIfNecessary();
		current.addRun(e -> e.clicker().sendMessage(Util.color(message.apply(e))));
		return this;
	}

	@Override
	public HandlerBuilder<P> action(ClickHandler event) {
		addIfNecessary();
		current.addRun(event);
		return this;
	}

	@Override
	public HandlerBuilder<P> nextPage() {
		addIfNecessary();
		current.addRun(Handlers.NEXT_PAGE);
		return this;
	}

	@Override
	public HandlerBuilder<P> previousPage() {
		addIfNecessary();
		current.addRun(Handlers.PREVIOUS_PAGE);
		return this;
	}

	@Override
	public HandlerBuilder<P> whenClickType(ClickType type) {
		return when(e -> e.getClick() == type);
	}

	@Override
	public HandlerBuilder<P> when(boolean condition) {
		return when(() -> condition);
	}


	@Override
	public HandlerBuilder<P> when(Supplier<Boolean> condition) {
		return when(e -> condition.get());
	}

	@Override
	public HandlerBuilder<P> when(Predicate<MenuClickEvent> condition) {
		current = new ClickAction();
		current.addCondition(condition);
		actions.add(current);
		return this;
	}

	@Override
	public HandlerBuilder<P> otherwise() {
		current = current.copySwapConditions();
		actions.add(current);
		return this;
	}

//    @Override
//    public void invalidate() {
//        actions.clear();
//        current = null;
//    }

	@NotNull
	@Override
	public ActionList build() {
		return actions;
	}

	@Override
	public P done() {
		return parent;
	}
}
