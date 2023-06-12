package com.syer.syermines;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Zona {
    @SerializedName("name")
    private String nomeZona;

    @SerializedName("blocks")
    private List<BlockData> blocks;

    @SerializedName("coordinates")
    private List<Integer> coordinates;

    public Zona(String nomeZona) {
        this.nomeZona = nomeZona;
        this.blocks = new ArrayList<>();
        this.coordinates = new ArrayList<>();
    }

    public String getNomeZona() {
        return nomeZona;
    }

    public List<BlockData> getBlocks() {
        return blocks;
    }

    public void addBlock(BlockData blockData) {
        blocks.add(blockData);
    }

    public List<Integer> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Integer> coordinates) {
        this.coordinates = coordinates;
    }

    // Altri metodi e attributi della classe Zona

    public static class BlockData {
        @SerializedName("default")
        private String defaultData;

        @SerializedName("drop")
        private String dropData;

        public BlockData(String defaultData, String dropData) {
            this.defaultData = defaultData;
            this.dropData = dropData;
        }

        public String getDefaultData() {
            return defaultData;
        }

        public String getDropData() {
            return dropData;
        }
    }
}