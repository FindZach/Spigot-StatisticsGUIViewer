package org.findzach.gamestats.service;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class PermissionChecker {

    public boolean hasPermission(CommandSender sender, String permission) {
        return sender.hasPermission(permission);
    }

    public boolean hasPermission(CommandSender sender, Permission permission) {
        return sender.hasPermission(permission);
    }
}