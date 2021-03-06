package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.dto.ProductDetailsDto;
import com.codecool.projectkifli.dto.ProductListDto;
import com.codecool.projectkifli.exception.ForbiddenException;
import com.codecool.projectkifli.exception.InvalidInputException;
import com.codecool.projectkifli.model.Product;
import com.codecool.projectkifli.model.ProductPostData;
import com.codecool.projectkifli.service.ProductService;
import javassist.NotFoundException;
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
        try {
            return productService.findById(id);
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.setHeader("errorMessage", e.getMessage());
            logger.warn("Product {} not found", id);
            return null;
        }
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Integer id, Principal principal, HttpServletResponse resp) {
        if (principal == null) {
            logger.error("Anonymous user trying to delete product {}", id);
            throw new AccessDeniedException("Can't delete product without login");
        }
        logger.trace("Delete product {} by user {}", id, principal.getName());
        try {
            productService.delete(id, principal.getName());
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.setHeader("errorMessage", e.getMessage());
            logger.warn("Can't delete product {}: {}", id, e.getMessage());
        }
    }

    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ProductDetailsDto postAd(@RequestBody ProductPostData data, Principal principal, HttpServletResponse resp) throws ParseException {
        if (principal == null) {
            logger.error("Anonymous user trying to add new product");
            throw new AccessDeniedException("Can't add new product without login");
        }
        logger.trace("Post by user {}", principal.getName());
        String errorMessage = null;
        Integer errorStatus;
        try {
            return productService.add(data, principal.getName());
        } catch (NotFoundException e) {
            errorMessage = e.getMessage();
            errorStatus = HttpServletResponse.SC_NOT_FOUND;
        } catch (InvalidInputException e) {
            errorMessage = e.getMessage();
            errorStatus = HttpServletResponse.SC_BAD_REQUEST;
        } catch (ParseException e) {
            logger.error("Error parsing date", e);
            errorStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        if (errorMessage != null) {
            logger.warn("Error adding product: {}", errorMessage);
            resp.setHeader("errorMessage", errorMessage);
        }
        resp.setStatus(errorStatus);
        return null;
    }

    @GetMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ProductListDto getProducts(
            @Nullable @RequestParam("search") String searchString,
            @Nullable @RequestParam("categoryId") Integer categoryId,
            @Nullable @RequestParam("minimumPrice") Float minimumPrice,
            @Nullable @RequestParam("maximumPrice") Float maximumPrice,
            @Nullable @RequestParam("page") Integer page
    ) {
        logger.trace("Get products");
        if (searchString == null && categoryId == null && minimumPrice == null && maximumPrice == null) {
            return productService.findAll(page);
        }
        return productService.getFilteredProducts(searchString, categoryId, minimumPrice, maximumPrice, page);
    }

    @GetMapping(
            value = "user/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ProductListDto getUserProducts(@PathVariable("id") Integer userId, @Nullable @RequestParam("page") Integer page) {
        logger.trace("Get products of user {}", userId);
        return productService.getUserProducts(userId, page);
    }

    @PutMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void updateProduct(@RequestBody ProductDetailsDto dto, Principal principal, HttpServletResponse resp) {
        if (principal == null) {
            logger.warn("User trying to update product {} is not logged in", dto.getId());
            return;
        }
        logger.trace("Put product {} by user {}", dto.getId(), principal.getName());
        String errorMessage = null;
        Integer errorStatus;
        try {
            productService.update(dto, principal.getName());
            return;
        } catch (NotFoundException e) {
            errorMessage = e.getMessage();
            errorStatus = HttpServletResponse.SC_NOT_FOUND;
        } catch (ForbiddenException e) {
            errorStatus = HttpServletResponse.SC_UNAUTHORIZED;
        } catch (InvalidInputException e) {
            errorMessage = e.getMessage();
            errorStatus = HttpServletResponse.SC_BAD_REQUEST;
        }
        if (errorMessage != null) {
            logger.warn("Error updating product: {}", errorMessage);
            resp.setHeader("errorMessage", errorMessage);
        }
        resp.setStatus(errorStatus);
    }

    @GetMapping(
            value = "/admin",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ProductListDto getInactiveProducts(@Nullable @RequestParam("page") Integer page) {
        logger.trace("Get inactive products");
        return productService.getInactiveProducts(page);
    }

    @PutMapping(
            value = "/{productId}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void setActivation(@RequestBody ProductDetailsDto product, @RequestHeader("productId") Integer productId, HttpServletResponse resp) {
        logger.trace("Put for product {}", productId);
        String errorMessage = null;
        Integer errorStatus;
        try {
            productService.setActivation(product, productId);
            return;
        } catch (NotFoundException e) {
            errorMessage = e.getMessage();
            errorStatus = HttpServletResponse.SC_NOT_FOUND;
        }
        if (errorMessage != null) {
            logger.warn("Error updating product: {}", errorMessage);
            resp.setHeader("errorMessage", errorMessage);
        }
        resp.setStatus(errorStatus);
    }
}
