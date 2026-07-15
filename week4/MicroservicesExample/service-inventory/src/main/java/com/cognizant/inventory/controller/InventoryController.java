package com.cognizant.inventory.controller;

import com.cognizant.inventory.model.StockLevel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Deliberately simple, in-memory stock lookup. The point of this service
 * is to be a second independently-deployable process that service-order
 * calls over REST -- not to model real inventory logic.
 */
@RestController
public class InventoryController {

    private static final Map<String, Integer> STOCK = Map.of(
            "SKU-1", 12,
            "SKU-2", 0,
            "SKU-3", 5
    );

    @GetMapping("/api/inventory/{sku}")
    public StockLevel getStock(@PathVariable String sku) {
        int quantity = STOCK.getOrDefault(sku, 0);
        return new StockLevel(sku, quantity);
    }
}
