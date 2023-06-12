package com.syer.syermines;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;

public class ZonaManager {
    private static final String FILE_NAME = "zones.json";
    private File dataFolder;
    private Gson gson;

    public ZonaManager(File dataFolder) {
        this.dataFolder = dataFolder;
        // Verifica se la cartella del plugin esiste, altrimenti creala
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        this.gson = new Gson();
    }

    public boolean isNomeZonaUtilizzato(String nomeZona) {
        List<Zona> zoneList = leggiZone();
        for (Zona zona : zoneList) {
            if (zona.getNomeZona().equals(nomeZona)) {
                return true;
            }
        }
        return false;
    }

    public void salvaZona(Zona zona) {
        List<Zona> zoneList = leggiZone();
        zoneList.add(zona);
        scriviZone(zoneList);
    }

    public List<Zona> leggiZone() {
        List<Zona> zoneList = new ArrayList<>();

        File file = new File(dataFolder, FILE_NAME);
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                Type listType = new TypeToken<ArrayList<Zona>>(){}.getType();
                zoneList = gson.fromJson(reader, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return zoneList;
    }

    private void scriviZone(List<Zona> zoneList) {
        File file = new File(dataFolder, FILE_NAME);
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(zoneList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Aggiungi altri metodi per leggere e aggiornare le informazioni delle zone
}
