package me.bristermitten.fluency.menu;

import me.bristermitten.fluency.FluentBuilder;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.button.distribution.ButtonDistribution;
import me.bristermitten.fluency.button.template.TemplateBuilder;
import me.bristermitten.fluency.data.ButtonHolder;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public interface MenuBuilder extends FluentBuilder<Menu, MenuBuilder> {
	@Nonnull
	MenuBuilder distributed(ButtonDistribution distribution);

	@Nonnull
	MenuBuilder distributed(Supplier<ButtonDistribution> distribution);

	ButtonDistribution distribution();

	@Nonnull
	MenuBuilder title(String title);

	@Nonnull
	String title();

	@Nonnull
	MenuBuilder size(int size);

	int size();

	@Nonnull
	ButtonBuilder buildButton();

	@Nonnull
	<T> TemplateBuilder<T> buildTemplate();

	@Nonnull
	<T> TemplateBuilder<T> buildTemplatesForEach(Iterable<T> iterable);

	@Nonnull
	MenuBuilder addButton(MenuButton button);

	@Nonnull
	MenuBuilder addButton(ButtonHolder button);

	@Nonnull
	MenuBuilder background(MenuButton button);

	@Nonnull
	MenuBuilder skip();

	@Nonnull
	ButtonBuilder buildBackground();

	@Override
	default MenuBuilder done() {
		return this;
	}
}
