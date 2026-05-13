package com.example.deepstack;

import com.google.gson.annotations.SerializedName;

public class Fish {

    @SerializedName("name")
    private String name;

    @SerializedName("taxonomy")
    private Taxonomy taxonomy;

    // Field tambahan, bukan dari API — diisi manual setelah fetch gambar
    private String imageUrl;

    public String getSpeciesName() { return name; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getScientificName() {
        return taxonomy != null ? taxonomy.getScientificName() : "-";
    }

    public static class Taxonomy {
        @SerializedName("scientific_name")
        private String scientificName;
        public String getScientificName() { return scientificName; }
    }
}