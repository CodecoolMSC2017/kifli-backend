package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.dto.ProductDetailsDto;
import com.codecool.projectkifli.dto.ProductListDto;
import com.codecool.projectkifli.model.ProductPostData;
import com.codecool.projectkifli.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public ProductDetailsDto findById(@PathVariable("id") Integer id, HttpServletResponse resp) {
        logger.trace("Get product {}", id);
        ProductDetailsDto dto = productService.findById(id);
        if (dto == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return dto;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Integer id, Principal principal) {
        logger.trace("Delete product {}", id);
        if (principal == null) {
            logger.error("Anonymous user trying to delete product {}", id);
            throw new AccessDeniedException("Can't delete product without login");
        }
        logger.info("Delete product {} by user {}", id, principal.getName());
        productService.delete(id, principal);
    }

    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ProductDetailsDto postAd(@RequestBody ProductPostData data, Principal principal) throws ParseException {
        if (principal == null) {
            logger.error("Anonymous user trying to add new product");
            throw new AccessDeniedException("Can't add new product without login");
        }
        logger.trace("Post by user {}", principal.getName());
        return productService.add(data, principal);
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
        logger.trace("Get products");
        if (searchString == null && categoryId == null && minimumPrice == null && maximumPrice == null) {
            return productService.findAll();
        }
        return productService.getFilteredProducts(searchString, categoryId, minimumPrice, maximumPrice);
    }

    @GetMapping(
            value = "user/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ProductListDto getUserProducts(@PathVariable("id") Integer userId) {
        logger.trace("Get products of user {}", userId);
        return productService.getUserProducts(userId);
    }

    @PutMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void updateProduct(@RequestBody ProductDetailsDto dto, Principal principal) {
        if (principal == null) {
            logger.warn("User trying to update product {} is not logged in", dto.getId());
            return;
        }
        logger.trace("Put product {} by user {}", dto.getId(), principal.getName());
        productService.update(dto, principal.getName());
    }

}
