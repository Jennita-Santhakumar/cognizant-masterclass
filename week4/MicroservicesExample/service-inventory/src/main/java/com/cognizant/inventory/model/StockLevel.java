package com.cognizant.inventory.model;

public class StockLevel {
    private final String sku;
    private final int quantityAvailable;

    public StockLevel(String sku, int quantityAvailable) {
        this.sku = sku;
        this.quantityAvailable = quantityAvailable;
    }

    public String getSku() {
        return sku;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }
}
