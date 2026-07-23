package week4.microservices;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Microservices Configuration - Service Discovery with Eureka
 */
@SpringBootApplication
@EnableEurekaClient
@Configuration
public class MicroserviceConfig {
    
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceConfig.class, args);
    }
}
