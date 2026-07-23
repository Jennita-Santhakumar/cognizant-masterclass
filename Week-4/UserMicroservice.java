package week4.microservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * User Microservice
 */
@RestController
@RequestMapping("/api/users")
public class UserMicroserviceController {
    
    @Autowired
    private UserMicroserviceImpl userMicroservice;
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userMicroservice.getAllUsers());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userMicroservice.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userMicroservice.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        User updatedUser = userMicroservice.updateUser(id, user);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        boolean deleted = userMicroservice.deleteUser(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

@Service
class UserMicroserviceImpl {
    
    private List<User> users = new ArrayList<>();
    private int nextId = 1;
    
    public UserMicroserviceImpl() {
        users.add(new User(nextId++, "Alice", "alice@example.com"));
        users.add(new User(nextId++, "Bob", "bob@example.com"));
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
    
    public User getUserById(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public User createUser(User user) {
        user.setId(nextId++);
        users.add(user);
        return user;
    }
    
    public User updateUser(int id, User updatedUser) {
        User user = getUserById(id);
        if (user == null) {
            return null;
        }
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        return user;
    }
    
    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }
}

class User {
    private int id;
    private String name;
    private String email;
    
    public User() {}
    
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
