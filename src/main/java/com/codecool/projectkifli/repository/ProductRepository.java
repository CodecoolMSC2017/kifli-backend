package com.codecool.projectkifli.repository;

import com.codecool.projectkifli.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
