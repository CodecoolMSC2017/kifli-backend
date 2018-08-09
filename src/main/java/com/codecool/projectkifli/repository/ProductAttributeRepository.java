package com.codecool.projectkifli.repository;

import com.codecool.projectkifli.model.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {

    void deleteAllByProductId(Integer productId);

}
