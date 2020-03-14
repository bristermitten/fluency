package me.bristermitten.fluency.implementation;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.FluentBuilder;
import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.click.HandlerBuilder;
import me.bristermitten.fluency.button.template.TemplateBuilder;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.entity.Player;

public class BuilderFactory {
	private final Fluency fluency;

	public BuilderFactory(Fluency fluency) {
		this.fluency = fluency;
	}

	public MenuBuilder buildMenu() {
		return new MenuBuilderImpl(fluency);
	}

	public ButtonBuilder buildButton() {
		return buildButton(null);
	}

	public ButtonBuilder buildButton(MenuBuilder parent) {
		return new ButtonBuilderImpl(fluency, parent);
	}

	public <P extends FluentBuilder<?, ?>> HandlerBuilder<P> buildHandler(P parent) {
		return new HandlerBuilderImpl(parent);
	}

	public <T> TemplateBuilder<T> buildTemplate(MenuBuilder parent) {
		return new TemplateBuilderImpl<>(fluency, parent);
	}

	public TemplateBuilder<Player> buildViewerTemplate(MenuBuilder parent) {
		return new PlayerTemplateBuilder(fluency, parent);
	}
}
