package org.findzach.gamestats.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.findzach.gamestats.service.MessageService;
import org.findzach.gamestats.service.PermissionChecker;
import org.findzach.gamestats.service.PlayerLookupService;

public abstract class CommandHandler implements CommandExecutor {

    protected final MessageService messageService;
    protected final PermissionChecker permissionChecker;
    protected final PlayerLookupService playerLookupService;

    public CommandHandler(MessageService messageService, PermissionChecker permissionChecker, PlayerLookupService playerLookupService) {
        this.messageService = messageService;
        this.permissionChecker = permissionChecker;
        this.playerLookupService = playerLookupService;
    }

    protected void sendMessage(CommandSender sender, String path) {
        sender.sendMessage(messageService.getMessage(path));
    }

    protected void sendMessage(CommandSender sender, String path, String placeholder, String replacement) {
        sender.sendMessage(messageService.getMessage(path, placeholder, replacement));
    }

    protected boolean hasPermission(CommandSender sender, String permission) {
        return permissionChecker.hasPermission(sender, permission);
    }
}