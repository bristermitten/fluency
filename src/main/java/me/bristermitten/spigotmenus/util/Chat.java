package me.bristermitten.spigotmenus.util;

import org.bukkit.ChatColor;

public class Chat {

    public static String color(String s) {
        if(s==null)return null;
        return ChatColor.translateAlternateColorCodes(ChatColor.COLOR_CHAR, s);
    }
}
