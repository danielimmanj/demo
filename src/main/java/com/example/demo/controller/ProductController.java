package com.example.demo.controller;

import com.example.demo.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @GetMapping
    public List<Product> getAllProducts() {
        return Arrays.asList(
                new Product(1L, "Laptop", 1200.0),
                new Product(2L, "Smartphone", 800.0)
        );
    }
}
