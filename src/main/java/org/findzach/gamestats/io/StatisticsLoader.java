package org.findzach.gamestats.io;

import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.findzach.gamestats.gui.InventoryBuilder;

import java.util.List;
import java.util.Set;

public class StatisticsLoader {

    private final JavaPlugin plugin;

    public StatisticsLoader(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // Load general statistics from the config and add to the inventory
    public void loadGeneralStats(OfflinePlayer target, InventoryBuilder builder) {
        FileConfiguration config = plugin.getConfig();
        Set<String> generalStats = config.getConfigurationSection("stats.general").getKeys(false);
        for (String statKey : generalStats) {
            if (config.getBoolean("stats.general." + statKey + ".enabled")) {
                Statistic stat = Statistic.valueOf(statKey);
                Material material = Material.valueOf(config.getString("stats.general." + statKey + ".material"));
                String displayName = config.getString("stats.general." + statKey + ".display_name");
                List<String> lore = config.getStringList("stats.general." + statKey + ".lore");

                int statValue = target.isOnline()
                        ? ((Player) target).getStatistic(stat) // For online players
                        : plugin.getServer().getOfflinePlayer(target.getUniqueId()).getStatistic(stat); // For offline players

                builder.addItem(material, displayName, statValue, lore);
            }
        }
    }

    // Load entity-specific statistics from the config and add to the inventory
    public void loadEntityStats(OfflinePlayer target, InventoryBuilder builder) {
        FileConfiguration config = plugin.getConfig();
        Set<String> entityStats = config.getConfigurationSection("stats.entity").getKeys(false);
        for (String statKey : entityStats) {
            if (config.getBoolean("stats.entity." + statKey + ".enabled")) {
                Statistic stat = Statistic.KILL_ENTITY;
                EntityType entity = EntityType.valueOf(config.getString("stats.entity." + statKey + ".entity"));
                Material material = Material.valueOf(config.getString("stats.entity." + statKey + ".material"));
                String displayName = config.getString("stats.entity." + statKey + ".display_name");
                List<String> lore = config.getStringList("stats.entity." + statKey + ".lore");

                int statValue = target.isOnline()
                        ? ((Player) target).getStatistic(stat, entity)
                        : 0; // Offline players cannot track entity-specific stats directly

                builder.addItem(material, displayName, statValue, lore);
            }
        }
    }

    // Load block-specific statistics from the config and add to the inventory
    public void loadBlockStats(OfflinePlayer target, InventoryBuilder builder) {
        FileConfiguration config = plugin.getConfig();
        Set<String> blockStats = config.getConfigurationSection("stats.block").getKeys(false);
        for (String statKey : blockStats) {
            if (config.getBoolean("stats.block." + statKey + ".enabled")) {
                Statistic stat = Statistic.MINE_BLOCK;
                Material block = Material.valueOf(config.getString("stats.block." + statKey + ".block"));
                Material material = Material.valueOf(config.getString("stats.block." + statKey + ".material"));
                String displayName = config.getString("stats.block." + statKey + ".display_name");
                List<String> lore = config.getStringList("stats.block." + statKey + ".lore");

                int statValue = target.isOnline()
                        ? ((Player) target).getStatistic(stat, block)
                        : 0; // Offline players cannot track block-specific stats directly

                builder.addItem(material, displayName, statValue, lore);
            }
        }
    }

    // Load item-specific statistics from the config and add to the inventory
    public void loadItemStats(OfflinePlayer target, InventoryBuilder builder) {
        FileConfiguration config = plugin.getConfig();
        Set<String> itemStats = config.getConfigurationSection("stats.item").getKeys(false);
        for (String statKey : itemStats) {
            if (config.getBoolean("stats.item." + statKey + ".enabled")) {
                Statistic stat = statKey.contains("USE_ITEM") ? Statistic.USE_ITEM : Statistic.BREAK_ITEM;
                Material item = Material.valueOf(config.getString("stats.item." + statKey + ".item"));
                Material material = Material.valueOf(config.getString("stats.item." + statKey + ".material"));
                String displayName = config.getString("stats.item." + statKey + ".display_name");
                List<String> lore = config.getStringList("stats.item." + statKey + ".lore");

                int statValue = target.isOnline()
                        ? ((Player) target).getStatistic(stat, item)
                        : 0; // Offline players cannot track item-specific stats directly

                builder.addItem(material, displayName, statValue, lore);
            }
        }
    }
}
