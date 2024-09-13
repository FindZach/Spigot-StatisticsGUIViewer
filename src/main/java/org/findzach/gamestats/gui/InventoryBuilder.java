package org.findzach.gamestats.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InventoryBuilder {

    private final Inventory inventory;

    public InventoryBuilder(Inventory inventory) {
        this.inventory = inventory;
    }

    public void addItem(Material material, String name, int value, List<String> loreConfig) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            // Set the colored display name
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

            // Add lore with coloring
            List<String> lore = new ArrayList<>();
            if (loreConfig != null) {
                for (String line : loreConfig) {
                    lore.add(ChatColor.translateAlternateColorCodes('&', line));
                }
            }
            lore.add(ChatColor.translateAlternateColorCodes('&', "&eValue: " + value));
            meta.setLore(lore);

            item.setItemMeta(meta);
        }

        inventory.addItem(item);
    }

}
