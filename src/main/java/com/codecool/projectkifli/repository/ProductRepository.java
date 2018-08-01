package com.codecool.projectkifli.repository;

import com.codecool.projectkifli.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * FROM product_ads WHERE lower(title) LIKE %?1%", nativeQuery = true)
    List<Product> findBySearchTitleString(String searchString);

    List<Product> getProductByUserId(int id);
}
