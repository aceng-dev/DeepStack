package com.example.deepstack;

import com.google.gson.annotations.SerializedName;

public class Logbook {
    @SerializedName("id")
    private Long id; // Gunakan Long (objek) agar bisa null

    @SerializedName("spot_name")
    private String spotName;

    @SerializedName("latitude")
    private Double latitude; // Sesuaikan dengan float8 (Double)

    @SerializedName("longitude")
    private Double longitude; // Sesuaikan dengan float8 (Double)

    @SerializedName("gear_id")
    private Long gearId; // Sesuaikan dengan bigint (Long)

    @SerializedName("notes")
    private String notes;

    @SerializedName("created_at")
    private String createdAt;

    // Konstruktor untuk membuat log baru (ID dan CreatedAt dibiarkan NULL)
    public Logbook(String spotName, Double latitude, Double longitude, Long gearId, String notes) {
        this.id = null;
        this.spotName = spotName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.gearId = gearId;
        this.notes = notes;
        this.createdAt = null;
    }

    // GSON secara default akan mengabaikan field yang bernilai NULL.
    // Ini penting agar Supabase yang menghandle ID dan created_at secara otomatis.

    public Long getId() { return id; }
    public String getSpotName() { return spotName; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public Long getGearId() { return gearId; }
    public String getNotes() { return notes; }

    // Tambahkan ini di Logbook.java
    public String getCreatedAt() {
        return createdAt;
    }
}