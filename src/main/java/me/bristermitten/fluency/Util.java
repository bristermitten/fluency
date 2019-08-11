package me.bristermitten.fluency;

import org.bukkit.ChatColor;

public class Util {
    public static String color(String s) {
        if (s == null) return null;
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
