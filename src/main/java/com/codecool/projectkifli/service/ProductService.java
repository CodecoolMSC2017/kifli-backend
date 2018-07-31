package com.codecool.projectkifli.service;

import com.codecool.projectkifli.model.*;
import com.codecool.projectkifli.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public Product findById(Integer id) throws ChangeSetPersister.NotFoundException {
        return productRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public void delete(Integer id, Principal principal) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        Product product = productRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if (product.getUserId().equals(user.getId()) || user.getAuthorities().contains("ROLE_ADMIN")) {
            productRepository.deleteById(id);
            return;
        }
        throw new AccessDeniedException("TAKA");
    }


    public List<Product> search(String searchString) {
        List<Product> bySearchTitleString = productRepository.findBySearchTitleString(searchString.toLowerCase());
        for (Product product : bySearchTitleString) {
            System.out.println(product);
        }
        return bySearchTitleString;
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
}
