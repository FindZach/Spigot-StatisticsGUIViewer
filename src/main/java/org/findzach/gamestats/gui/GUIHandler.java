package org.findzach.gamestats.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.findzach.gamestats.io.StatisticsLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GUIHandler implements Listener {

    private final StatisticsLoader statisticsLoader;
    private final JavaPlugin plugin;

    public GUIHandler(JavaPlugin plugin, StatisticsLoader statisticsLoader) {
        this.statisticsLoader = statisticsLoader;
        this.plugin = plugin;
    }

    public void openStatsGUI(Player viewer, OfflinePlayer target) {
        int guiSize = Bukkit.getPluginManager().getPlugin("InGameStatistics").getConfig().getInt("gui.size");
        String guiTitle = ChatColor.translateAlternateColorCodes('&', Bukkit.getPluginManager().getPlugin("InGameStatistics").getConfig().getString("gui.title"));

        // Create inventory (GUI)
        Inventory gui = Bukkit.createInventory(null, guiSize, guiTitle);

        // InventoryBuilder to add items dynamically
        InventoryBuilder builder = new InventoryBuilder(gui);

        // Load and populate statistics
        statisticsLoader.loadGeneralStats(target, builder);
        statisticsLoader.loadEntityStats(target, builder);
        statisticsLoader.loadBlockStats(target, builder);
        statisticsLoader.loadItemStats(target, builder);

        // Open the inventory for the viewer
        viewer.openInventory(gui);
    }

    // Handle clicks in the GUI
    // Handle GUI click events
    @org.bukkit.event.EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(plugin.getConfig().getString("gui.title"))) {
            event.setCancelled(true); // Prevent players from taking the items
        }
    }

    /**
     * In API versions 1.20.6 and earlier, InventoryView is a class.
     * In versions 1.21 and later, it is an interface.
     * This method uses reflection to get the top Inventory object from the
     * InventoryView associated with an InventoryEvent, to avoid runtime errors.
     * @param event The generic InventoryEvent with an InventoryView to inspect.
     * @return The top Inventory object from the event's InventoryView.
     */
    public static Inventory getTopInventory(InventoryEvent event) {
        try {
            Object view = event.getView();
            Method getTopInventory = view.getClass().getMethod("getTopInventory");
            getTopInventory.setAccessible(true);
            return (Inventory) getTopInventory.invoke(view);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
