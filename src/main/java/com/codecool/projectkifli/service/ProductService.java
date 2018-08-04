package com.codecool.projectkifli.service;

import com.codecool.projectkifli.dto.ProductDetailsDto;
import com.codecool.projectkifli.dto.ProductListDto;
import com.codecool.projectkifli.model.*;
import com.codecool.projectkifli.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private CategoryRespository categoryRespository;
    @Autowired
    private ProductListItemRepository productListItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductAttributeRepository productAttributeRepository;
    @Autowired
    private SimpleProductRepository simpleProductRepository;

    public ProductListDto findAll() {
        logger.debug("Finding all products");
        ProductListDto dto = new ProductListDto(categoryRespository.findAll());
        dto.setProducts(productListItemRepository.findAll());
        return dto;
    }

    public ProductDetailsDto findById(Integer id) {
        logger.debug("Getting product with id " + id);
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            logger.warn("Did not find product {}", id);
            return null;
        }
        return new ProductDetailsDto(product);
    }

    public void delete(Integer id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        if (user == null) {
            logger.warn("User trying to delete product " + id + " is not logged in");
            return;
        }
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            logger.warn("Did not find product " + id);
            return;
        }
        if (product.getOwner().getId().equals(user.getId()) || user.getAuthorities().contains("ROLE_ADMIN")) {
            logger.debug("Deleting product " + id);
            productRepository.deleteById(id);
            return;
        }
        logger.warn("User " + principal.getName() + " is not authorized to delete product " + id);
        throw new AccessDeniedException("TAKA");
    }

    public ProductListDto getFilteredProducts(String searchString, Integer categoryId, Float minimumPrice, Float maximumPrice) {
        logger.trace("Filtering products: searchString={}, categoryId={}, minPrice={}, maxPrice={}", searchString, categoryId, minimumPrice, maximumPrice);
        if (searchString == null || searchString.equals("")) {
            if (categoryId == null || categoryId == 0) {
                logger.trace("Returning all products");
                return filterByPrice(productListItemRepository.findAll(), minimumPrice, maximumPrice);
            }
            logger.trace("Filtering by category {}", categoryId);
            return filterByPrice(productListItemRepository.findByCategoryId(categoryId), minimumPrice, maximumPrice);
        }
        if (categoryId == null || categoryId == 0) {
            logger.trace("Filtering by search string {}", searchString);
            return filterByPrice(productListItemRepository.findBySearchTitleString(searchString), minimumPrice, maximumPrice);
        }
        logger.trace("Filtering by searchString {} and category {}", searchString, categoryId);
        return filterByPrice(productListItemRepository.findBySearchTitleStringAndCategoryId(searchString, categoryId), minimumPrice, maximumPrice);
    }

    public ProductListDto getUserProducts(Integer userId) {
        logger.debug("Getting products of user " + userId);
        ProductListDto dto = new ProductListDto(categoryRespository.findAll());
        dto.setProducts(productListItemRepository.findAllByUserId(userId));
        return dto;
    }

    public void add(ProductPostData data, Principal principal) throws ParseException {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        if (user == null) {
            logger.warn("User trying to add new product is not logged in");
            return;
        }
        SimpleProduct product = new SimpleProduct();
        product.setUserId(user.getId());
        product.setCategoryId(data.getCategoryId());
        product.setTitle(data.getTitle());
        product.setDescription(data.getDescription());
        product.setPrice(data.getPrice());
        product.setType("BUYOUT");
        product.setState("FOR SALE");
        product.setUploadDate(simpleDateFormat.parse("2017/10/01"));
        product.setPremiumExpirationDate(simpleDateFormat.parse("2020/12/02"));

        SimpleProduct save = simpleProductRepository.save(product);
        logger.debug("Saved product " + save.getId() + " in database, inserting attributes now");
        insertAttributes(data.getAttributes(), save.getId(), data.getCategoryId());
    }

    private void insertAttributes(Map<String, String> attributes, Integer productId, Integer categoryId) {
        Category category = categoryRespository.findById(categoryId).orElse(null);
        if (category == null) {
            logger.warn("Did not find category " + categoryId);
            return;
        }
        int insertCount = 0;
        for (CategoryAttribute attribute : category.getAttributes()) {
            String value = attributes.get(attribute.getName());
            ProductAttribute productAttribute = new ProductAttribute();
            productAttribute.setProductId(productId);
            productAttribute.setAttributeId(attribute.getId());
            productAttribute.setValue(value);
            productAttributeRepository.save(productAttribute);
            insertCount++;
        }
        logger.debug("Inserted " + insertCount + " attributes for product " + productId);
    }

    private ProductListDto filterByPrice(List<ProductListItem> products, Float minimum, Float maximum) {
        ProductListDto dto = new ProductListDto(categoryRespository.findAll());
        if (minimum == null && maximum == null) {
            logger.trace("No price filtering parameter given, skipping");
            dto.setProducts(products);
            return dto;
        }
        logger.trace("Filtering price between " + minimum + " and " + maximum);
        if (minimum == null) {
            minimum = (float) 0;
        }
        if (maximum == null) {
            maximum = (float) 999999999;
        }
        List<ProductListItem> filteredList = new ArrayList<>();
        for (ProductListItem product : products) {
            float price = product.getPrice();
            if (price >= minimum && price <= maximum) {
                filteredList.add(product);
            }
        }
        dto.setProducts(filteredList);
        return dto;
    }
}
