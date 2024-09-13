package org.findzach.gamestats;

import org.bukkit.plugin.java.JavaPlugin;
import org.findzach.gamestats.command.StatsCommand;
import org.findzach.gamestats.gui.GUIHandler;
import org.findzach.gamestats.io.StatisticsLoader;
import org.findzach.gamestats.service.MessageService;
import org.findzach.gamestats.service.PermissionChecker;
import org.findzach.gamestats.service.PlayerLookupService;
import org.findzach.gamestats.tabcomplete.StatsTabCompleter;

public class InGameStatistics extends JavaPlugin {

    private StatisticsLoader statisticsLoader;
    private GUIHandler guiHandler;

    // Add the new services
    private MessageService messageService;
    private PermissionChecker permissionChecker;
    private PlayerLookupService playerLookupService;

    @Override
    public void onEnable() {
        // Initialize services
        this.messageService = new MessageService(this); // MessageService needs the plugin instance
        this.permissionChecker = new PermissionChecker(); // Simple permission checker
        this.playerLookupService = new PlayerLookupService(); // Player lookup for online/offline players

        // Initialize other components
        this.statisticsLoader = new StatisticsLoader(this);
        this.guiHandler = new GUIHandler(this, statisticsLoader); // Pass MessageService to GUIHandler

        // Register the command executor and tab completer
        getCommand("stats").setExecutor(new StatsCommand(
                messageService,
                permissionChecker,
                playerLookupService,
                guiHandler
        ));

        getCommand("stats").setTabCompleter(new StatsTabCompleter()); // Tab completer for player names

        // Register the event listener for GUI interactions
        getServer().getPluginManager().registerEvents(guiHandler, this);

        // Save default config if it doesn't exist
        saveDefaultConfig();
    }

    public StatisticsLoader getStatisticsLoader() {
        return statisticsLoader;
    }
}