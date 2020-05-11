package me.bristermitten.fluency.button.template;

import me.bristermitten.fluency.FluentBuilder;
import me.bristermitten.fluency.button.click.HandlerBuilder;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public interface TemplateBuilder<T> extends FluentBuilder<ButtonTemplate<T>, MenuBuilder>
{

    TemplateBuilder<T> nameFrom(Function<T, String> nameFunction);

    TemplateBuilder<T> loreFrom(Function<T, List<String>> loreFunction);

    TemplateBuilder<T> typeFrom(Function<T, Material> typeFunction);

    TemplateBuilder<T> type(Material type);

    TemplateBuilder<T> amount(int amount);

    TemplateBuilder<T> amountFrom(ToIntFunction<T> amountFunction);

    TemplateBuilder<T> withSource(Supplier<T> sourceSupplier);

    TemplateBuilder<T> withSource(Function<Player, T> sourceFunction);

    TemplateBuilder<T> withSource(T source);

    HandlerBuilder<TemplateBuilder<T>> onClick();

}
