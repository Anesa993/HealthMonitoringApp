package com.example.healthyme.Camscanner;

import com.google.gson.annotations.SerializedName;

public class ProductResponse {
    @SerializedName("product")
    private Product product;

    public Product getProduct() {
        return product;
    }
}