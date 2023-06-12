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

public class BlocksListGUI implements Listener {
    private JavaPlugin plugin;

    public BlocksListGUI(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void openBlocksListGUI(Player player, List<Zona.BlockData> blockList) {
        Inventory gui = Bukkit.createInventory(null, 54, "Blocks List");

        for (int i = 0; i < blockList.size(); i++) {
            Zona.BlockData blockData = blockList.get(i);
            ItemStack blockItem = new ItemStack(Material.STONE);
            ItemMeta blockItemMeta = blockItem.getItemMeta();
            blockItemMeta.setDisplayName("Block " + (i + 1));
            blockItem.setItemMeta(blockItemMeta);
            gui.setItem(i, blockItem);
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Blocks List")) {
            return;
        }

        event.setCancelled(true);
        // Gestisci l'evento di clic su un blocco della lista, se necessario
    }
}