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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.ChatColor;
import java.util.ArrayList;


import java.util.List;

public class ZonesListGUI implements Listener {
    private JavaPlugin plugin;
    private ZonaManager zonaManager;

    public ZonesListGUI(JavaPlugin plugin, ZonaManager zonaManager) {
        this.plugin = plugin;
        this.zonaManager = zonaManager;
    }

    public void openZonesListGUI(Player player, List<Zona> zoneList) {
        Inventory gui = Bukkit.createInventory(null, 54, ChatColor.GOLD + "" + ChatColor.BOLD + "Zone");

        int slot = 0;
        for (Zona zona : zoneList) {
            String zoneName = zona.getNomeZona();
            List<Zona.BlockData> blockDataList = zona.getBlocks();
            List<String> blockList = new ArrayList<>();

            for (Zona.BlockData blockData : blockDataList) {
                String defaultData = blockData.getDefaultData();
                blockList.add(defaultData);
            }

            ItemStack zoneItem = new ItemStack(Material.STONE);
            ItemMeta zoneMeta = zoneItem.getItemMeta();
            String title = ChatColor.BOLD + "" + ChatColor.GOLD + "Zone: " + zoneName;
            zoneMeta.setDisplayName(ChatColor.BOLD + title);

            List<String> lore = new ArrayList<>();
            for (String block : blockList) {
                String[] parts = block.split("_");
                if (parts.length > 1) {
                    String resourceName = parts[0].toLowerCase();
                    String materialName = parts[1].toLowerCase();
                    lore.add(ChatColor.WHITE + " ・" + resourceName + ": " + materialName);
                }
            }

            zoneMeta.setLore(lore);
            zoneItem.setItemMeta(zoneMeta);

            gui.setItem(slot, zoneItem);
            slot++;
        }
        gui.setItem(49, createBackButton());

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Zones List")) {
            return;
        }

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem != null && clickedItem.getType() == Material.STONE) {
            String zoneName = clickedItem.getItemMeta().getDisplayName();
            // Esegui le operazioni desiderate con il nome della zona
            player.sendMessage("Clicked on zone: " + zoneName);
        }

        if (event.getRawSlot() == 49) { // Slot del pulsante di ritorno
            event.getWhoClicked().closeInventory();
            MainGUI mainGUI = new MainGUI(plugin, zonaManager);
            mainGUI.openMainGUI(player);
        }
    }

    private ItemStack createBackButton() {
        ItemStack backButton = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta backButtonMeta = (SkullMeta) backButton.getItemMeta();
        backButtonMeta.setOwner("MHF_ArrowLeft"); // Testa del player freccia
        backButtonMeta.setDisplayName("Back");
        backButton.setItemMeta(backButtonMeta);
        return backButton;
    }
}