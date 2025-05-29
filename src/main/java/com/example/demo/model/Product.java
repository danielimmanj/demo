package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product {
    // Getters & Setters
    private Long id;
    private String name;
    private double price;

    public Product() {}

    public Product(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

}
