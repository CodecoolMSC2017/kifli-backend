package com.codecool.projectkifli.repository;

import com.codecool.projectkifli.model.ProductAds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAdsRepository extends JpaRepository<ProductAds, Integer> {

    ProductAds findByProductAdsId (Integer id);
}
