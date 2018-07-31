package com.codecool.projectkifli.repository;

import com.codecool.projectkifli.model.SimpleProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleProductRepository extends JpaRepository<SimpleProduct, Integer> {
}
