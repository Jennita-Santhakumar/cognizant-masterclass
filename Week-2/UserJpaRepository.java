package week2.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository - CRUD operations and custom queries
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {
    
    // Custom finder methods
    Optional<UserEntity> findByEmail(String email);
    
    List<UserEntity> findByNameContaining(String name);
    
    List<UserEntity> findByIsActive(boolean isActive);
    
    // Custom JPQL Query
    @Query("SELECT u FROM UserEntity u WHERE u.email = :email AND u.isActive = true")
    Optional<UserEntity> findActiveUserByEmail(@Param("email") String email);
    
    // Custom native SQL Query
    @Query(value = "SELECT * FROM users WHERE name LIKE %:name% ORDER BY id DESC", nativeQuery = true)
    List<UserEntity> searchUsersByName(@Param("name") String name);
    
    // Count active users
    long countByIsActive(boolean isActive);
}
