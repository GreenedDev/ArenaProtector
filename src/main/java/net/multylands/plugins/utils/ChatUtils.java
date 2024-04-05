package net.multylands.plugins.utils;

import org.bukkit.ChatColor;

public class ChatUtils {
    public static String Color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
