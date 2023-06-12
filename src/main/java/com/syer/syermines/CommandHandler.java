package com.syer.syermines;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandHandler implements CommandExecutor {
    private JavaPlugin plugin;
    private ZonaManager zonaManager;

    public CommandHandler(JavaPlugin plugin, ZonaManager zonaManager) {
        this.plugin = plugin;
        this.zonaManager = zonaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0 || !args[0].equalsIgnoreCase("main")) {
            player.sendMessage("Usage: /syermine main");
            return true;
        }

        MainGUI mainGUI = new MainGUI(plugin, zonaManager);
        mainGUI.openMainGUI(player);

        return true;
    }
}