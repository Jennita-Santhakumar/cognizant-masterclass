package week2.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Core Configuration - Bean definition and dependency injection
 */
@Configuration
public class AppConfig {
    
    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository());
    }
    
    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }
    
    @Bean
    public EmailService emailService() {
        return new EmailService();
    }
}

interface UserService {
    void registerUser(User user);
    User getUserById(int id);
}

class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public void registerUser(User user) {
        userRepository.save(user);
        System.out.println("User registered: " + user.getName());
    }
    
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id);
    }
}

interface UserRepository {
    void save(User user);
    User findById(int id);
}

class UserRepositoryImpl implements UserRepository {
    @Override
    public void save(User user) {
        System.out.println("Saving user to database: " + user.getName());
    }
    
    @Override
    public User findById(int id) {
        return new User(id, "John Doe", "john@example.com");
    }
}

class EmailService {
    public void sendEmail(String to, String subject, String body) {
        System.out.println("Email sent to " + to + " with subject: " + subject);
    }
}

class User {
    private int id;
    private String name;
    private String email;
    
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
