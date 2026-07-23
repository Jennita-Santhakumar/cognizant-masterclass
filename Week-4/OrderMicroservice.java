package week4.microservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Order Microservice - Communicates with User Microservice
 */
@RestController
@RequestMapping("/api/orders")
public class OrderMicroserviceController {
    
    @Autowired
    private OrderMicroserviceImpl orderMicroservice;
    
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderMicroservice.getAllOrders());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        Order order = orderMicroservice.getOrderById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }
    
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order savedOrder = orderMicroservice.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable int userId) {
        List<Order> orders = orderMicroservice.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
}

@Service
class OrderMicroserviceImpl {
    
    private List<Order> orders = new ArrayList<>();
    private int nextId = 1;
    
    @Autowired
    private RestTemplate restTemplate;
    
    public OrderMicroserviceImpl() {
        orders.add(new Order(nextId++, 1, 1000, new Date(), "Pending"));
        orders.add(new Order(nextId++, 2, 2000, new Date(), "Completed"));
    }
    
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }
    
    public Order getOrderById(int id) {
        return orders.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public Order createOrder(Order order) {
        order.setId(nextId++);
        order.setOrderDate(new Date());
        orders.add(order);
        return order;
    }
    
    public List<Order> getOrdersByUserId(int userId) {
        return orders.stream()
                .filter(o -> o.getUserId() == userId)
                .toList();
    }
    
    public boolean deleteOrder(int id) {
        return orders.removeIf(o -> o.getId() == id);
    }
}

class Order {
    private int id;
    private int userId;
    private double amount;
    private Date orderDate;
    private String status;
    
    public Order() {}
    
    public Order(int id, int userId, double amount, Date orderDate, String status) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.orderDate = orderDate;
        this.status = status;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", amount=" + amount +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                '}';
    }
}
