package me.bristermitten.fluency.implementation;

import me.bristermitten.fluency.Fluency;
import me.bristermitten.fluency.button.template.ButtonTemplate;
import me.bristermitten.fluency.button.template.TemplateBuilder;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class TemplateBuilderImpl<T> implements TemplateBuilder<T> {
	@Nullable
	private final MenuBuilder parent;
	protected ButtonTemplate<T> template;

	public TemplateBuilderImpl(Fluency fluency, MenuBuilder parent) {
		template = new ButtonTemplate<>(fluency);
		this.parent = parent;
	}

	@Override
	public TemplateBuilder<T> nameFrom(Function<T, String> nameFunction) {
		template.nameFunction(nameFunction);
		return this;
	}

	@Override
	public TemplateBuilder<T> loreFrom(Function<T, List<String>> loreFunction) {
		template.loreFunction(loreFunction);
		return this;
	}

	@Override
	public TemplateBuilder<T> typeFrom(Function<T, Material> typeFunction) {
		template.typeFunction(typeFunction);
		return this;
	}

	@Override
	public TemplateBuilder<T> type(Material type) {
		template.typeFunction(any -> type);
		return this;
	}

	@Override
	public TemplateBuilder<T> withSource(Supplier<T> sourceSupplier) {
		return withSource(p -> sourceSupplier.get());
	}

	@Override
	public TemplateBuilder<T> withSource(Function<Player, T> sourceFunction) {
		template.sourceFunction(sourceFunction);
		return this;
	}

	@Override
	public TemplateBuilder<T> withSource(T source) {
		return withSource(() -> source);
	}

	@NotNull
	@Override
	public ButtonTemplate<T> build() {
		return template;
	}

//    @Override
//    public void invalidate() {
//        template = null;
//    }

	@Override
	public MenuBuilder done() {
		if (template.sourceFunction() == null) {
			throw new IllegalStateException("No source object set!");
		}
		return parent;
	}


}
