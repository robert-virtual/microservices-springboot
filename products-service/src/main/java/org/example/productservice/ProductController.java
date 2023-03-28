package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    @GetMapping("all")
    private List<Product> all() {
        return productRepository.findAll();
    }

    @PostMapping("create")
    private Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }
}
