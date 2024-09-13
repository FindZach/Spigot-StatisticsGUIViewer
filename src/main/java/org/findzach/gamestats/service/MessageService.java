package org.findzach.gamestats.service;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.findzach.gamestats.config.ConfigurableTextProvider;

public class MessageService implements ConfigurableTextProvider {

    private final JavaPlugin plugin;

    public MessageService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getMessage(String path) {
        String message = plugin.getConfig().getString(path);
        return ChatColor.translateAlternateColorCodes('&', message != null ? message : "");
    }

    @Override
    public String getMessage(String path, String placeholder, String replacement) {
        String message = plugin.getConfig().getString(path);
        if (message != null) {
            message = message.replace(placeholder, replacement);
        }
        return ChatColor.translateAlternateColorCodes('&', message != null ? message : "");
    }
}
