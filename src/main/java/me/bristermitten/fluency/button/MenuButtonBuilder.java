package me.bristermitten.fluency.button;

import org.bukkit.Material;

public interface MenuButtonBuilder {
    MenuButtonBuilder amount(int amount);

    MenuButtonBuilder type(Material type);
}
