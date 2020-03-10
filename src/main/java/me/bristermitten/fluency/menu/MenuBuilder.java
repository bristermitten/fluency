package me.bristermitten.fluency.menu;

import me.bristermitten.fluency.FluentBuilder;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.button.distribution.ButtonDistribution;
import me.bristermitten.fluency.button.template.TemplateBuilder;
import me.bristermitten.fluency.data.ButtonHolder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface MenuBuilder extends FluentBuilder<Menu, MenuBuilder> {
	@NotNull
	MenuBuilder distributed(ButtonDistribution distribution);

	@NotNull
	MenuBuilder distributed(Supplier<ButtonDistribution> distribution);

	ButtonDistribution distribution();

	@NotNull
	MenuBuilder title(String title);

	@NotNull
	String title();

	@NotNull
	MenuBuilder size(int size);

	int size();

	@NotNull
	ButtonBuilder buildButton();

	@NotNull <T> TemplateBuilder<T> buildTemplate();

	@NotNull <T> TemplateBuilder<T> buildTemplatesForEach(Iterable<T> iterable);

	@NotNull
	MenuBuilder addButton(MenuButton button);

	@NotNull
	MenuBuilder addButton(ButtonHolder button);

	@NotNull
	MenuBuilder background(MenuButton button);

	@NotNull
	MenuBuilder skip();

	@NotNull
	ButtonBuilder buildBackground();

	@Override
	default MenuBuilder done() {
		return this;
	}
}
