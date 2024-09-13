package org.findzach.gamestats.command;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.findzach.gamestats.gui.GUIHandler;
import org.findzach.gamestats.service.MessageService;
import org.findzach.gamestats.service.PermissionChecker;
import org.findzach.gamestats.service.PlayerLookupService;

public class StatsCommand extends CommandHandler {

    private final GUIHandler guiHandler;

    public StatsCommand(MessageService messageService, PermissionChecker permissionChecker, PlayerLookupService playerLookupService, GUIHandler guiHandler) {
        super(messageService, permissionChecker, playerLookupService);
        this.guiHandler = guiHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, "messages.player_only_command");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sendMessage(player, "messages.view_own_stats");
            guiHandler.openStatsGUI(player, player);
            return true;
        }

        if (args.length == 1) {
            if (!hasPermission(player, "stats.viewother")) {
                sendMessage(player, "messages.no_permission");
                return true;
            }

            OfflinePlayer targetPlayer = playerLookupService.getPlayer(args[0]);

            if (!playerLookupService.isPlayerValid(targetPlayer)) {
                sendMessage(player, "messages.player_not_found", "{player}", args[0]);
                return true;
            }

            sendMessage(player, "messages.view_other_stats", "{player}", targetPlayer.getName());
            guiHandler.openStatsGUI(player, targetPlayer);
            return true;
        }

        return false;
    }
}