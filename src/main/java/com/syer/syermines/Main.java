package com.syer.syermines;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        // Inizializzazione del plugin
        getLogger().info("Il plugin è stato abilitato.");

        ZonaManager zonaManager = new ZonaManager(getDataFolder());
        getServer().getPluginManager().registerEvents(new MainGUI(this, zonaManager), this);
        getServer().getPluginManager().registerEvents(new ZonesListGUI(this, zonaManager), this);
        getServer().getPluginManager().registerEvents(new BlocksListGUI(this), this);
        MineRegenSystem mineRegenSystem = new MineRegenSystem(this);
        getServer().getPluginManager().registerEvents(mineRegenSystem, this);
        // Registra i comandi
        Objects.requireNonNull(getCommand("create")).setExecutor(new CreateCommand(zonaManager));
        Objects.requireNonNull(getCommand("syermine")).setExecutor(new CommandHandler(this, zonaManager));
        Objects.requireNonNull(getCommand("main")).setExecutor(new CommandHandler(this, zonaManager));
    }


    @Override
    public void onDisable() {
        // Disabilitazione del plugin
        getLogger().info("Il plugin è stato disabilitato.");
    }
}
