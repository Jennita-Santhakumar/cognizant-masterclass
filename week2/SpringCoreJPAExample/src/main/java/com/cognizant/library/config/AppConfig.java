package com.cognizant.library.config;

import com.cognizant.library.repository.AuthorRepository;
import com.cognizant.library.service.AuthorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Week 2 deliverable: 2+ beans wired with CONSTRUCTOR injection.
 *
 * Why constructor injection instead of field injection (@Autowired on a field):
 *  1. Dependencies become explicit and final -> the class cannot exist in a
 *     half-constructed state with a null dependency.
 *  2. It makes the class trivially testable with plain `new AuthorService(mockRepo)`
 *     in a unit test, no Spring context or reflection tricks needed.
 *  3. It surfaces circular dependencies at wiring time instead of at runtime,
 *     because the container can't build a bean whose constructor needs a
 *     bean that isn't ready yet.
 */
@Configuration
public class AppConfig {

    @Bean
    public AuthorService authorService(AuthorRepository authorRepository) {
        return new AuthorService(authorRepository);
    }
}
