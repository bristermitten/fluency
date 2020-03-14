package me.bristermitten.fluency.button.click;

import me.bristermitten.fluency.FluentBuilder;
import me.bristermitten.fluency.menu.Menu;
import org.bukkit.event.inventory.ClickType;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface HandlerBuilder<R extends FluentBuilder<?, ?>> extends FluentBuilder<ClickHandler, R> {
	HandlerBuilder<R> cancel();

	HandlerBuilder<R> closeMenu();

	HandlerBuilder<R> openMenu(Menu m);

	HandlerBuilder<R> openMenu(Supplier<Menu> m);

	HandlerBuilder<R> sendMessage(String message);

	HandlerBuilder<R> sendMessage(Function<MenuClickEvent, String> message);

	HandlerBuilder<R> action(ClickHandler event);

	HandlerBuilder<R> nextPage();

	HandlerBuilder<R> previousPage();

	HandlerBuilder<R> whenClickType(ClickType type);

	HandlerBuilder<R> when(boolean condition);

	HandlerBuilder<R> when(Supplier<Boolean> condition);

	HandlerBuilder<R> when(Predicate<MenuClickEvent> condition);

	HandlerBuilder<R> otherwise();

//    <T> HandlerBuilder handlePlaceholder(String key, Consumer<T> consumer);
}
