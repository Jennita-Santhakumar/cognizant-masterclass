package com.cognizant.order.client;

import com.cognizant.order.model.StockLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * The one place service-order knows how to reach service-inventory over
 * REST. Everything else in this service talks to InventoryClient, never
 * directly to a URL -- so swapping REST for a message queue later touches
 * one file.
 */
@Component
public class InventoryClient {

    private final RestTemplate restTemplate;
    private final String inventoryBaseUrl;

    public InventoryClient(RestTemplate restTemplate,
                            @Value("${inventory.service.base-url}") String inventoryBaseUrl) {
        this.restTemplate = restTemplate;
        this.inventoryBaseUrl = inventoryBaseUrl;
    }

    public StockLevel getStock(String sku) {
        return restTemplate.getForObject(inventoryBaseUrl + "/api/inventory/{sku}", StockLevel.class, sku);
    }
}
