package com.codecool.projectkifli.repository;

import com.codecool.projectkifli.model.ProductPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ProductPicture, Integer> {

    List<ProductPicture> findAllByProductId(Integer productId);

}
