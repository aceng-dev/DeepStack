package com.example.deepstack;

import com.google.gson.annotations.SerializedName;

public class Logbook {
    @SerializedName("id")
    private int id;

    @SerializedName("spot_name")
    private String spotName;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("gear_id")
    private int gearId;

    @SerializedName("notes")
    private String notes;

    @SerializedName("created_at")
    private String createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getGearId() {
        return gearId;
    }

    public void setGearId(int gearId) {
        this.gearId = gearId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // Constructor kosong untuk Gson/Retrofit
    public Logbook() {
    }

    // Constructor untuk insert/POST data baru
    public Logbook(String spotName, String latitude, String longitude, int gearId, String notes) {
        this.spotName = spotName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.gearId = gearId;
        this.notes = notes;
    }
}
