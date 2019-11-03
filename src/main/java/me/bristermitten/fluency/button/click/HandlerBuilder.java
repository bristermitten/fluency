package me.bristermitten.fluency.button.click;

import me.bristermitten.fluency.FluentBuilder;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.menu.Menu;
import org.bukkit.event.inventory.ClickType;

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

    HandlerBuilder whenClickType(ClickType type);

    HandlerBuilder when(boolean condition);

    HandlerBuilder when(Supplier<Boolean> condition);

    HandlerBuilder when(Predicate<MenuClickEvent> condition);

    /**
     * Set the working condition to the inverse of the previous condition
     * This inverses the directly previous condition, and that condition only!
     * @return
     */
    HandlerBuilder otherwise();

//    <T> HandlerBuilder handlePlaceholder(String key, Consumer<T> consumer);
}
