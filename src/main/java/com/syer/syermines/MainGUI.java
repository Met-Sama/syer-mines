package com.syer.syermines;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class MainGUI implements Listener {
    private JavaPlugin plugin;
    private ZonaManager zonaManager;

    public MainGUI(JavaPlugin plugin, ZonaManager zonaManager) {
        this.plugin = plugin;
        this.zonaManager = zonaManager;
    }

    public void openMainGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 54, "Main GUI");

        ItemStack viewZonesItem = new ItemStack(Material.MINECART);
        ItemMeta viewZonesMeta = viewZonesItem.getItemMeta();
        viewZonesMeta.setDisplayName("View Zones");
        viewZonesItem.setItemMeta(viewZonesMeta);

        gui.setItem(22, viewZonesItem);

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Main GUI")) {
            return;
        }

        event.setCancelled(true);

        if (event.getRawSlot() == 22) {
            Player player = (Player) event.getWhoClicked();
            List<Zona> zoneList = zonaManager.leggiZone();

            if (zoneList.isEmpty()) {
                player.sendMessage("There are no zones created.");
                return;
            }

            ZonesListGUI zonesListGUI = new ZonesListGUI(plugin, zonaManager);
            zonesListGUI.openZonesListGUI(player, zoneList);
        }

    }


}
