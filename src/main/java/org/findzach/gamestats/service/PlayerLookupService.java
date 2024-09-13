package org.findzach.gamestats.service;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PlayerLookupService {

    public OfflinePlayer getPlayer(String name) {
        return Bukkit.getOfflinePlayer(name);
    }

    public boolean isPlayerValid(OfflinePlayer player) {
        return player.hasPlayedBefore() || player.isOnline();
    }
}
