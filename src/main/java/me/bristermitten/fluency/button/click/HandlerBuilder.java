package me.bristermitten.fluency.button.click;

import me.bristermitten.fluency.FluentBuilder;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.menu.Menu;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface HandlerBuilder extends FluentBuilder<ClickHandler, ButtonBuilder> {
    HandlerBuilder cancel();

    HandlerBuilder closeMenu();

    HandlerBuilder openMenu(Menu m);

    HandlerBuilder openMenu(Supplier<Menu> m);

    HandlerBuilder sendMessage(String message);

    HandlerBuilder sendMessage(Function<MenuClickEvent, String> message);

    HandlerBuilder action(ClickHandler event);

    HandlerBuilder nextPage();

    HandlerBuilder previousPage();

    HandlerBuilder when(boolean condition);

    HandlerBuilder when(Supplier<Boolean> condition);

    HandlerBuilder when(Predicate<MenuClickEvent> condition);

    HandlerBuilder otherwise();

//    <T> HandlerBuilder handlePlaceholder(String key, Consumer<T> consumer);
}