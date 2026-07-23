package week2.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * User Service - Business logic layer
 */
@Service
public class UserService {
    
    @Autowired
    private UserJpaRepository userRepository;
    
    // Create
    public UserEntity createUser(UserEntity user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }
    
    // Read
    public UserEntity getUserById(int id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
    
    public List<UserEntity> getActiveUsers() {
        return userRepository.findByIsActive(true);
    }
    
    // Update
    public UserEntity updateUser(int id, UserEntity updatedUser) {
        UserEntity user = getUserById(id);
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        user.setAddress(updatedUser.getAddress());
        return userRepository.save(user);
    }
    
    // Delete
    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
    
    // Search
    public List<UserEntity> searchUsersByName(String name) {
        return userRepository.searchUsersByName(name);
    }
    
    // Count
    public long getActiveUserCount() {
        return userRepository.countByIsActive(true);
    }
}
