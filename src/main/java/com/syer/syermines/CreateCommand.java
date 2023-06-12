package com.syer.syermines;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CreateCommand implements CommandExecutor {
    private final ZonaManager zonaManager;

    public CreateCommand(ZonaManager zonaManager) {
        this.zonaManager = zonaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Utilizzo: /syermine:create <nome>");
            return true;
        }

        String nomeZona = args[0];

        // Verifica se il nome della zona è già utilizzato
        if (zonaManager.isNomeZonaUtilizzato(nomeZona)) {
            sender.sendMessage("Il nome della zona è già utilizzato.");
            return true;
        }

        Zona nuovaZona = new Zona(nomeZona);

        // Salva la zona utilizzando il ZonaManager
        zonaManager.salvaZona(nuovaZona);

        sender.sendMessage("La zona è stata creata e salvata con successo.");
        return true;
    }
}
