package me.bristermitten.fluency.button;

import me.bristermitten.fluency.FluentBuilder;
import me.bristermitten.fluency.button.click.HandlerBuilder;
import me.bristermitten.fluency.menu.MenuBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.ClickType;

import java.util.List;

public interface ButtonBuilder extends FluentBuilder<MenuButton, MenuBuilder> {
    ButtonBuilder amount(int amount);

    ButtonBuilder type(Material type);

    ButtonBuilder data(short data);

    ButtonBuilder name(String name);

    ButtonBuilder lore(String... lore);

    ButtonBuilder lore(List<String> lore);

    ButtonBuilder addLore(String lore);

    ButtonBuilder unbreakable();

    ButtonBuilder enchant(Enchantment e, int level);

    HandlerBuilder onClick();

    HandlerBuilder onClick(ClickType type);

    MenuButton build();
}
