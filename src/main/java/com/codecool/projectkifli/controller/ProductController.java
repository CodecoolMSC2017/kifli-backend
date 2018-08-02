package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.dto.ProductDetailsDto;
import com.codecool.projectkifli.model.Product;
import com.codecool.projectkifli.model.ProductListItem;
import com.codecool.projectkifli.model.ProductPostData;
import com.codecool.projectkifli.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<ProductListItem> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ProductDetailsDto findById(@PathVariable("id") Integer id) throws ChangeSetPersister.NotFoundException {
        Product product = productService.findById(id);
        return new ProductDetailsDto(product);
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
            value = "search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<ProductListItem> findBySearchTitle(
            @RequestParam("search") String searchString,
            @RequestParam("categoryId") int categoryId
    ) {
        return productService.search(searchString, categoryId);
    }

    @GetMapping(
            value = "user/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<ProductListItem> getUserProducts(@PathVariable("id") Integer userId) {
        return productService.getUserProducts(userId);
    }

    @GetMapping(
            value = "category/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<ProductListItem> getByCategory(@PathVariable("id") Integer id) {
        return productService.getProductsByCategory(id);
    }

}
