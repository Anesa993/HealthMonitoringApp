package com.example.healthyme.Camscanner;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("product_name")
    private String productName;

    @SerializedName("nutriments")
    private Nutriments nutriments;

    public String getProductName() {
        return productName;
    }

    public Nutriments getNutriments() {
        return nutriments;
    }
}
