package com.cognizant.order.controller;

import com.cognizant.order.client.InventoryClient;
import com.cognizant.order.model.StockLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * service-order calls service-inventory over plain REST (via InventoryClient)
 * -- two independently deployable, independently runnable services, not a
 * monolith split into packages.
 */
@RestController
public class OrderController {

    private final InventoryClient inventoryClient;

    public OrderController(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

    @GetMapping("/api/orders/can-fulfil/{sku}")
    public ResponseEntity<String> canFulfil(@PathVariable String sku) {
        StockLevel stock = inventoryClient.getStock(sku);
        if (stock.getQuantityAvailable() > 0) {
            return ResponseEntity.ok(sku + " can be fulfilled (" + stock.getQuantityAvailable() + " in stock)");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(sku + " is out of stock");
    }
}
