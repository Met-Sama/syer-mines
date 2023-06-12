package com.syer.syermines;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ZonaManager {
    private static final String FILE_PATH = "zones.txt";

    public static void salvaZona(Zona zona) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(zona.getNome());
            writer.newLine();
            // Scrivi altre informazioni della zona sul file

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Aggiungi altri metodi per leggere e aggiornare le informazioni delle zone
}
