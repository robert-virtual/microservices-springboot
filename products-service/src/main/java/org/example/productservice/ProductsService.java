package org.example.productservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class ProductsService {
    public static void main(String[] args) {
        SpringApplication.run(ProductsService.class, args);
    }

    @Bean
    public CommandLineRunner seedData(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() <= 0) {
                productRepository.saveAll(List.of(
                        Product
                                .builder()
                                .name("Iphone x")
                                .description("Iphone x")
                                .price("30,000")
                                .createdAt(LocalDateTime.now())
                                .build(),
                        Product
                                .builder()
                                .name("Samsung Galaxy A21s")
                                .description("Samsung Galaxy A21s")
                                .price("6,000")
                                .createdAt(LocalDateTime.now())
                                .build()
                ));
            }
        };
    }
}