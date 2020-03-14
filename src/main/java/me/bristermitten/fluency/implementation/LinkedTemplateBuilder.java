package me.bristermitten.fluency.implementation;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.button.click.HandlerBuilder;
import me.bristermitten.fluency.button.template.ButtonTemplate;
import me.bristermitten.fluency.button.template.TemplateBuilder;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class LinkedTemplateBuilder<T> implements TemplateBuilder<T> {
	private final MenuBuilder parent;
	private final List<TemplateBuilder<T>> children;

	private final TemplateBuilder<T> internal;

	public LinkedTemplateBuilder(Fluency fluency, MenuBuilder parent, List<TemplateBuilder<T>> children) {
		this.parent = parent;
		this.internal = fluency.buildTemplate(parent);

		children.add(internal);
		this.children = children;
	}

	@Override
	public TemplateBuilder<T> nameFrom(Function<T, String> nameFunction) {
		for (TemplateBuilder<T> sub : children) {
			sub.nameFrom(nameFunction);
		}
		return this;
	}

	@Override
	public TemplateBuilder<T> loreFrom(Function<T, List<String>> loreFunction) {
		for (TemplateBuilder<T> sub : children) {
			sub.loreFrom(loreFunction);
		}
		return this;
	}

	@Override
	public TemplateBuilder<T> typeFrom(Function<T, Material> typeFunction) {
		for (TemplateBuilder<T> sub : children) {
			sub.typeFrom(typeFunction);
		}
		return this;
	}

	@Override
	public TemplateBuilder<T> type(Material type) {
		for (TemplateBuilder<T> sub : children) {
			sub.type(type);
		}
		return this;
	}

	@Override
	public TemplateBuilder<T> withSource(Supplier<T> sourceSupplier) {
		return withSource(p -> sourceSupplier.get());
	}

	@Override
	public TemplateBuilder<T> withSource(Function<Player, T> sourceFunction) {
		for (TemplateBuilder<T> child : children) {
			child.withSource(sourceFunction);
		}
		return this;
	}

	@Override
	public TemplateBuilder<T> withSource(T object) {
		return withSource(() -> object);
	}

	@Override
	public HandlerBuilder<TemplateBuilder<T>> onClick() {
		return internal.onClick();
	}

//    @Override
//    public void invalidate() {
//        templateBuilders.clear();
//    }

	@NotNull
	@Override
	public ButtonTemplate<T> build() {
		return internal.build();
	}

	@Override
	public MenuBuilder done() {
		return parent;
	}
}
