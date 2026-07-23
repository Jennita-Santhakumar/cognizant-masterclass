package week1.patterns;

/**
 * Builder Pattern - Constructs complex objects step by step
 */
public class Builder {
    
    public static void main(String[] args) {
        User user = new User.UserBuilder()
                .setId(1)
                .setName("John Doe")
                .setEmail("john@example.com")
                .setPhone("1234567890")
                .setAddress("123 Main St")
                .build();
        
        System.out.println(user);
    }
}

class User {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    
    private User(UserBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
        this.address = builder.address;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
    
    public static class UserBuilder {
        private int id;
        private String name;
        private String email;
        private String phone;
        private String address;
        
        public UserBuilder setId(int id) {
            this.id = id;
            return this;
        }
        
        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }
        
        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }
        
        public UserBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }
        
        public UserBuilder setAddress(String address) {
            this.address = address;
            return this;
        }
        
        public User build() {
            return new User(this);
        }
    }
}
