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

    public String getSpotName() {
        return spotName;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public int getGearId() {
        return gearId;
    }

    public String getNotes() {
        return notes;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
