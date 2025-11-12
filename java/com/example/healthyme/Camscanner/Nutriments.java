package com.example.healthyme.Camscanner;

import com.google.gson.annotations.SerializedName;

public class Nutriments {
    @SerializedName("energy-kcal_100g")
    private String energyKcal;

    public String getEnergyKcal() {
        return energyKcal;
    }
}