package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.model.ProductAd;
import com.codecool.projectkifli.repository.ProductAdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductAdController {

    @Autowired
    private ProductAdsRepository productAdsRepository;

    @GetMapping(name = "/products",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductAd> getAllAds() {
        ProductAd ad = productAdsRepository.findById(1).orElse(null);
        System.out.println(ad.getUploadDate());
        return productAdsRepository.findAll();
    }

}
