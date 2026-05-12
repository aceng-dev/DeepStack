package com.example.deepstack;

import com.google.gson.annotations.SerializedName;

public class Fish {
    @SerializedName("id")
    private int id;

    @SerializedName("Species Name")
    private String speciesName;

    @SerializedName("Scientific Name")
    private String scientificName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }
}
