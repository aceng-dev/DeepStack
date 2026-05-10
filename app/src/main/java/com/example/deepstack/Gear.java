package com.example.deepstack;

import com.google.gson.annotations.SerializedName;

public class Gear {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("spec_detail")
    private String specDetail;

    @SerializedName("category")
    private String category;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecDetail() {
        return specDetail;
    }

    public String getCategory() {
        return category;
    }
}
