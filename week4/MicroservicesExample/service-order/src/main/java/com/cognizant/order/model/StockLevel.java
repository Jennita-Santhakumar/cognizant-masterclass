package com.cognizant.order.model;

/**
 * Local copy of the shared contract with service-inventory. In a real
 * system this would live in a shared client library or be generated from
 * an OpenAPI spec published by service-inventory; documented here as the
 * Week 4 "shared contract" deliverable.
 */
public class StockLevel {
    private String sku;
    private int quantityAvailable;

    public StockLevel() {
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
}
