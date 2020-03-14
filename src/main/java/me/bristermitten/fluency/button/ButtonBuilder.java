package me.bristermitten.fluency.button;

import me.bristermitten.fluency.FluentBuilder;
import me.bristermitten.fluency.button.click.HandlerBuilder;
import me.bristermitten.fluency.data.ButtonHolder;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public interface ButtonBuilder extends FluentBuilder<MenuButton, MenuBuilder> {
	@NotNull ButtonBuilder amount(int amount);

	int amount();

	@NotNull ButtonBuilder type(Material type);

	@NotNull
	Material type();

	@NotNull ButtonBuilder data(short data);

	short data();

	@NotNull ButtonBuilder name(@Nullable String name);

	@Nullable
	String name();

	@NotNull ButtonBuilder lore(String... lore);

	@NotNull ButtonBuilder lore(List<String> lore);

	@NotNull ButtonBuilder addLore(String lore);

	@NotNull List<String> lore();

	@NotNull ButtonBuilder unbreakable();

	@NotNull ButtonBuilder breakable();

	boolean isUnbreakable();

	@NotNull ButtonBuilder enchant(Enchantment e, int level);

	@NotNull Map<Enchantment, Integer> enchantments();

	@NotNull HandlerBuilder<ButtonBuilder> onClick();

	@NotNull HandlerBuilder<ButtonBuilder> onClick(ClickType type);

	@NotNull MenuButton build();

	@NotNull ButtonHolder getHolder();
}
