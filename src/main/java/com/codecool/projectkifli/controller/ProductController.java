package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.dto.ProductDetailsDto;
import com.codecool.projectkifli.dto.ProductListDto;
import com.codecool.projectkifli.model.ProductPostData;
import com.codecool.projectkifli.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ProductDetailsDto findById(@PathVariable("id") Integer id) throws ChangeSetPersister.NotFoundException {
        return productService.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Integer id, Principal principal) throws ChangeSetPersister.NotFoundException {
        productService.delete(id, principal);
    }

    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void postAd(@RequestBody ProductPostData data, Principal principal) throws ParseException {
        productService.add(data, principal);
    }

    @GetMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ProductListDto getProducts(
            @Nullable @RequestParam("search") String searchString,
            @Nullable @RequestParam("categoryId") Integer categoryId,
            @Nullable @RequestParam("minimumPrice") Float minimumPrice,
            @Nullable @RequestParam("maximumPrice") Float maximumPrice
    ) {
        return productService.getFilteredProducts(searchString, categoryId, minimumPrice, maximumPrice);
    }

    @GetMapping(
            value = "user/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ProductListDto getUserProducts(@PathVariable("id") Integer userId) {
        return productService.getUserProducts(userId);
    }

}
