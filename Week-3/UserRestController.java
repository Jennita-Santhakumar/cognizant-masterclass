package week3.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * User REST API Controller - REST endpoints for CRUD operations
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserRestController {
    
    @Autowired
    private UserService userService;
    
    // GET all users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers()
                .stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok(users);
    }
    
    // GET user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        UserEntity user = userService.getUserById(id);
        return ResponseEntity.ok(convertToDTO(user));
    }
    
    // POST create new user
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserEntity user = convertToEntity(userDTO);
        UserEntity savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedUser));
    }
    
    // PUT update user
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        UserEntity user = convertToEntity(userDTO);
        UserEntity updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(convertToDTO(updatedUser));
    }
    
    // DELETE user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    // GET users by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserEntity user = userService.getUserByEmail(email);
        return ResponseEntity.ok(convertToDTO(user));
    }
    
    // GET search users by name
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String name) {
        List<UserDTO> users = userService.searchUsersByName(name)
                .stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok(users);
    }
    
    // Helper methods
    private UserDTO convertToDTO(UserEntity user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getAddress());
    }
    
    private UserEntity convertToEntity(UserDTO dto) {
        return new UserEntity(dto.getName(), dto.getEmail(), dto.getPhone());
    }
}

class UserDTO {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    
    public UserDTO() {}
    
    public UserDTO(int id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}

class UserService {
    public List<UserEntity> getAllUsers() {
        // Implementation
        return null;
    }
    
    public UserEntity getUserById(int id) {
        // Implementation
        return null;
    }
    
    public UserEntity createUser(UserEntity user) {
        // Implementation
        return null;
    }
    
    public UserEntity updateUser(int id, UserEntity user) {
        // Implementation
        return null;
    }
    
    public void deleteUser(int id) {
        // Implementation
    }
    
    public UserEntity getUserByEmail(String email) {
        // Implementation
        return null;
    }
    
    public List<UserEntity> searchUsersByName(String name) {
        // Implementation
        return null;
    }
}

class UserEntity {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    
    public UserEntity(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
}
