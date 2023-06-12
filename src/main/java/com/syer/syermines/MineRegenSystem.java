package com.syer.syermines;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import org.bukkit.Particle;

import java.util.UUID;
import org.bukkit.block.BlockFace;

public class MineRegenSystem implements Listener {
    private JavaPlugin plugin;
    private Map<Location, Material> originalBlocks;
    private Map<Location, Long> regeneratingBlocks;


    public MineRegenSystem(JavaPlugin plugin) {
        this.plugin = plugin;
        this.originalBlocks = new HashMap<>();
        this.regeneratingBlocks = new HashMap<>();

    }

    public void regenerateBlock(Block block) {
        Location location = block.getLocation();
        Material originalMaterial = block.getType();

        Bukkit.getLogger().info("Regenerated block : " + block);

        // Memorizza il blocco originale
        originalBlocks.put(location, originalMaterial);

        Block randomBlock = findRandomAirBlock(location);
        if (randomBlock != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    // Sostituisci il blocco con un blocco a caso non di aria
                    randomBlock.setType(originalMaterial);
                }
            }.runTaskLater(plugin, 7 * 20);
        }

        // Programma la rigenerazione del blocco dopo il ritardo specificato
        new BukkitRunnable() {
            @Override
            public void run() {
                // Sostituisci il blocco con un blocco a caso non di aria


                regenerateBlockDelayed(location);
            }
        }.runTaskLater(plugin, 5 * 20); // 5 secondi di ritardo (moltiplicato per 20 per convertire in ticks)

        Bukkit.getLogger().info("Regenerating block at " + location.toString());
    }

    private Block findRandomAirBlock(Location location) {
        World world = location.getWorld();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        Random random = new Random();

        // Cerca un blocco a caso non di aria entro un raggio di 5 blocchi
        for (int i = 0; i < 10; i++) {
            int offsetX = random.nextInt(11) - 5; // Raggio di 5 blocchi (da -5 a +5)
            int offsetY = random.nextInt(11) - 5;
            int offsetZ = random.nextInt(11) - 5;

            int newX = x + offsetX;
            int newY = y + offsetY;
            int newZ = z + offsetZ;

            Location newLocation = new Location(world, newX, newY, newZ);
            Block block = newLocation.getBlock();

            if (block.getType() != Material.AIR && block.getType() != Material.BEDROCK && isBlockVisible(block) && !regeneratingBlocks.containsKey(newLocation)) {
                // Controlla se il blocco è un minerale
                if (isOreBlock(block)) {
                    // Verifica se ci sono altri minerali nelle vicinanze
                    boolean hasAdjacentOre = hasAdjacentOre(block);
                    if (!hasAdjacentOre) {
                        return block;
                    }
                } else {
                    return block;
                }
            }
        }

        return null; // Nessun blocco non di aria trovato
    }

    public void regenerateBlockDelayed(Location location) {
        Block block = location.getBlock();
        Material originalMaterial = originalBlocks.remove(location);

        if (originalMaterial != null) {
            // Controlla se il blocco è in rigenerazione
            if (regeneratingBlocks.containsKey(location)) {
                // Rimuovi il blocco dalla lista di rigenerazione
                regeneratingBlocks.remove(location);
                block.setType(originalMaterial);
                Bukkit.getLogger().info("Regenerated block at " + location.toString());

                    // Mostra l'effetto visivo
                    block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(0.5, 1.5, 0.5), 30);

            } else {
                // Il blocco non è in rigenerazione, quindi è la bedrock
                block.setType(Material.STONE);
                Bukkit.getLogger().info("Regenerating block at " + location.toString());
                // Aggiungi il blocco alla lista di rigenerazione
                regeneratingBlocks.put(location, System.currentTimeMillis());
            }
        }
    }

    private boolean isBlockVisible(Block block) {
        BlockFace[] faces = {
                BlockFace.NORTH,
                BlockFace.EAST,
                BlockFace.SOUTH,
                BlockFace.WEST,
                BlockFace.UP,
                BlockFace.DOWN
        };

        // Controlla se almeno una faccia del blocco è adiacente a un blocco d'aria
        for (BlockFace face : faces) {
            Block relativeBlock = block.getRelative(face);
            if (relativeBlock.getType() == Material.AIR) {
                return true;
            }
        }

        return false;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        // Controlla se il blocco è stato effettivamente rotto dal giocatore
        if (!event.isCancelled() && block.getType() != Material.AIR && !player.isSneaking()) {
            // Impedisci la rottura del blocco
            event.setCancelled(true);
            // Rigenera il blocco
            regenerateBlock(block);
            // Sostituisci il blocco con BEDROCK
            block.setType(Material.BEDROCK);
        }



        //if (block.getType() != Material.AIR && !player.isSneaking()) {
        //    regenerateBlock(block);
        //}
    }

    private boolean isOreBlock(Material material) {
        // Lista dei materiali di minerale
        List<Material> oreMaterials = Arrays.asList(
                Material.DIAMOND_ORE,
                Material.GOLD_ORE,
                Material.IRON_ORE
                // Aggiungi altri materiali di minerale se necessario
        );

        // Verifica se il materiale è presente nella lista dei materiali di minerale
        return oreMaterials.contains(material);
    }

    private boolean isOreBlock(Block block) {
        Material type = block.getType();
        return type == Material.DIAMOND_ORE ||
                type == Material.EMERALD_ORE ||
                type == Material.GOLD_ORE ||
                type == Material.IRON_ORE ||
                type == Material.LAPIS_ORE ||
                type == Material.REDSTONE_ORE ||
                type == Material.COAL_ORE;
    }

    private boolean hasAdjacentOre(Block block) {
        BlockFace[] faces = {
                BlockFace.NORTH,
                BlockFace.EAST,
                BlockFace.SOUTH,
                BlockFace.WEST,
                BlockFace.UP,
                BlockFace.DOWN
        };

        for (BlockFace face : faces) {
            Block relativeBlock = block.getRelative(face);
            if (isOreBlock(relativeBlock)) {
                return true;
            }
        }

        return false;
    }

}
