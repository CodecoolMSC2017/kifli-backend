package com.codecool.projectkifli.service;

import com.codecool.projectkifli.dto.ProductListDto;
import com.codecool.projectkifli.model.*;
import com.codecool.projectkifli.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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

    public List<ProductListItem> findAll() {
        return productListItemRepository.findAll();
    }

    public Product findById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public void delete(Integer id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        Product product = productRepository.findById(id).orElse(null);
        if (product.getOwner().getId().equals(user.getId()) || user.getAuthorities().contains("ROLE_ADMIN")) {
            productRepository.deleteById(id);
            return;
        }
        throw new AccessDeniedException("TAKA");
    }

    public ProductListDto getFilteredProducts(String searchString, Integer categoryId, Float minimumPrice, Float maximumPrice) {
        if (searchString == null || searchString.equals("")) {
            if (categoryId == null || categoryId == 0) {
                return filterByPrice(productListItemRepository.findAll(), minimumPrice, maximumPrice);
            }
            return filterByPrice(productListItemRepository.findByCategoryId(categoryId), minimumPrice, maximumPrice);
        }
        if (categoryId == null || categoryId == 0) {
            return filterByPrice(productListItemRepository.findBySearchTitleString(searchString), minimumPrice, maximumPrice);
        }
        return filterByPrice(productListItemRepository.findBySearchTitleStringAndCategoryId(searchString, categoryId), minimumPrice, maximumPrice);
    }

    public ProductListDto getUserProducts(Integer userId) {
        ProductListDto dto = new ProductListDto(categoryRespository.findAll());
        dto.setProducts(productListItemRepository.findAllByUserId(userId));
        return dto;
    }

    public void add(ProductPostData data, Principal principal) throws ParseException {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
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
        insertAttributes(data.getAttributes(), save.getId(), data.getCategoryId());
    }

    private void insertAttributes(Map<String, String> attributes, Integer productId, Integer categoryId) {
        Category category = categoryRespository.findById(categoryId).orElse(null);
        for (CategoryAttribute attribute : category.getAttributes()) {
            String value = attributes.get(attribute.getName());
            ProductAttribute productAttribute = new ProductAttribute();
            productAttribute.setProductId(productId);
            productAttribute.setAttributeId(attribute.getId());
            productAttribute.setValue(value);
            productAttributeRepository.save(productAttribute);
        }
    }

    public List<ProductListItem> getProductsByCategory(Integer id) {
        return productListItemRepository.findByCategoryId(id);
    }

    private ProductListDto filterByPrice(List<ProductListItem> products, Float minimum, Float maximum) {
        ProductListDto dto = new ProductListDto(categoryRespository.findAll());
        if (minimum == null && maximum == null) {
            dto.setProducts(products);
            return dto;
        }
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
