package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.model.Product;
import com.codecool.projectkifli.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(name = "/products",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public List<Product> getAllProducts() {
        Product product = productRepository.findById(1).orElse(null);
        System.out.println(product.getUploadDate());
        return productRepository.findAll();
        }
}