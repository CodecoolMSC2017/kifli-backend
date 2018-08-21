package com.codecool.projectkifli.service;

import com.codecool.projectkifli.dto.ProductDetailsDto;
import com.codecool.projectkifli.dto.ProductListDto;
import com.codecool.projectkifli.exception.ForbiddenException;
import com.codecool.projectkifli.exception.InvalidInputException;
import com.codecool.projectkifli.model.*;
import com.codecool.projectkifli.repository.*;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProductService {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

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
    private CategoryAttributeRepository categoryAttributeRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    public ProductListDto findAll() {
        logger.debug("Finding all products");
        ProductListDto dto = new ProductListDto();
        dto.setCategories(categoryRespository.findAll());
        dto.setProducts(productListItemRepository.findAll());
        return dto;
    }

    public ProductDetailsDto findById(Integer id) throws NotFoundException {
        logger.debug("Getting product {}", id);
        Product product = get(id);
        return new ProductDetailsDto(product);
    }

    public void delete(Integer id, String username) throws NotFoundException {
        User user = userService.get(username);
        Product product = get(id);
        if (product.getOwner().getId().equals(user.getId()) || user.getAuthorities().contains("ROLE_ADMIN")) {
            logger.info("Deleting product {}", id);
            productRepository.deleteById(id);
            return;
        }
        logger.error("User {} is not authorized to delete product {}", username, id);
        throw new AccessDeniedException("Not authorized to delete this ad");
    }

    public ProductListDto getFilteredProducts(String searchString, Integer categoryId, Float minimumPrice, Float maximumPrice) {
        logger.debug("Filtering products: searchString={}, categoryId={}, minPrice={}, maxPrice={}", searchString, categoryId, minimumPrice, maximumPrice);
        if (searchString == null || searchString.equals("")) {
            if (categoryId == null || categoryId == 0) {
                logger.trace("Finding all products");
                return filterByPrice(productListItemRepository.findAll(), minimumPrice, maximumPrice);
            }
            logger.trace("Filtering by category {}", categoryId);
            return filterByPrice(productListItemRepository.findByCategoryId(categoryId), minimumPrice, maximumPrice);
        }
        if (categoryId == null || categoryId == 0) {
            logger.trace("Filtering by searchString '{}'", searchString);
            return filterByPrice(productListItemRepository.findBySearchTitleString(searchString), minimumPrice, maximumPrice);
        }
        logger.trace("Filtering by searchString '{}' and category {}", searchString, categoryId);
        return filterByPrice(productListItemRepository.findBySearchTitleStringAndCategoryId(searchString, categoryId), minimumPrice, maximumPrice);
    }

    public ProductListDto getUserProducts(Integer userId) {
        logger.debug("Getting products of user {}", userId);
        ProductListDto dto = new ProductListDto();
        dto.setCategories(categoryRespository.findAll());
        dto.setProducts(productListItemRepository.findAllByUserId(userId));
        return dto;
    }

    public ProductDetailsDto add(ProductPostData data, String username) throws NotFoundException, InvalidInputException, ParseException {
        User user = userService.get(username);
        Category category = categoryService.get(data.getCategoryId());

        Product product = new Product();
        setAttributesOfProduct(data, product);
        product.setOwner(user);
        List<ProductAttribute> productAttributes = setupProductAttributes(data.getAttributes(), category);
        product.setAttributes(productAttributes);
        product.setCategory(category);

        logger.trace("Saving new product");
        Product save = productRepository.save(product);
        logger.info("Added new product {}", save.getId());
        return findById(save.getId());
    }

    @Transactional
    public void update(ProductDetailsDto dto, String username) throws NotFoundException, ForbiddenException, InvalidInputException {
        Product product = get(dto.getId());
        if (!product.getOwner().getUsername().equals(username)) {
            logger.warn("User {} is not allowed update product of user {}", username, product.getOwner().getUsername());
            throw new ForbiddenException("You are not allowed to edit this ad!");
        }
        updateBasicProductAttributes(product, dto);
        updateCategoryAttributes(dto, product);
        logger.trace("Saving changes of product {}", product.getId());
        productRepository.save(product);
        logger.info("Updated product {}", product.getId());
    }

    private void setAttributesOfProduct(ProductPostData data, Product product) throws InvalidInputException, ParseException {
        logger.trace("Setting basic attributes");
        setBasicAttributesOfProduct(product, data.getTitle(), data.getDescription(), data.getPrice(), data.getType());

        product.setState("FOR SALE");
        String formattedDate = simpleDateFormat.format(new Date());
        product.setUploadDate(simpleDateFormat.parse(formattedDate));
        product.setPremiumExpirationDate(simpleDateFormat.parse("2020/12/02"));
    }

    private void updateCategoryAttributes(ProductDetailsDto dto, Product product) {
        Map<String, String> dtoAttributes = dto.getAttributes();
        logger.trace("Updating attributes to {}", dtoAttributes);
        for (CategoryAttribute categoryAttribute : product.getCategory().getAttributes()) {
            String newValue = dtoAttributes.get(categoryAttribute.getName());
            if (newValue == null) {
                logger.warn("Value of attribute '{}' from client is null, skipping update of this attribute", categoryAttribute.getName());
                continue;
            }
            ProductAttribute productAttribute = getProductAttributeById(product.getAttributes(), categoryAttribute.getId());
            if (productAttribute == null) {
                logger.warn("Attribute '{}' is missing from product, inserting", categoryAttribute.getName());
                productAttribute = new ProductAttribute();
                productAttribute.setProductId(product.getId());
                productAttribute.setAttributeId(categoryAttribute.getId());
            }
            productAttribute.setValue(newValue);
            productAttributeRepository.save(productAttribute);
        }
    }

    private ProductAttribute getProductAttributeById(List<ProductAttribute> attributes, Integer attributeId) {
        for (ProductAttribute attribute : attributes) {
            if (attribute.getAttributeId().equals(attributeId)) {
                return attribute;
            }
        }
        return null;
    }

    private void updateBasicProductAttributes(Product product, ProductDetailsDto dto) throws InvalidInputException {
        logger.trace("Updating basic attributes");
        setBasicAttributesOfProduct(product, dto.getTitle(), dto.getDescription(), dto.getPrice(), dto.getType());
    }

    private void setBasicAttributesOfProduct(Product product, String title, String description, Float price, String type) throws InvalidInputException {
        if (title == null || title.equals("")) {
            throw new InvalidInputException("Title can't be empty!");
        }
        product.setTitle(title);
        product.setDescription(description);

        if (price == null || price < 0.1) {
            throw new InvalidInputException("Price can't be empty or less than 0.1!");
        }
        product.setPrice(price);

        if (type == null || type.equals("")) {
            throw new InvalidInputException("Type can't be empty!");
        }
        product.setType(type);
    }

    private List<ProductAttribute> setupProductAttributes(Map<String, String> attributes, Category category) {
        logger.trace("Setting up category attributes");
        List<ProductAttribute> productAttributes = new ArrayList<>();

        for (CategoryAttribute attribute : category.getAttributes()) {
            ProductAttribute productAttribute = new ProductAttribute();
            productAttribute.setAttributeId(attribute.getId());
            String value = attributes.get(attribute.getName());
            if (value == null) {
                logger.warn("Value of attribute '{}' from client is null, skipping inserting this attribute", attribute.getName());
                continue;
            }
            productAttribute.setValue(value);
            productAttributes.add(productAttribute);
        }
        return productAttributes;
    }

    private ProductListDto filterByPrice(List<ProductListItem> products, Float minimum, Float maximum) {
        ProductListDto dto = new ProductListDto();
        dto.setCategories(categoryRespository.findAll());
        if (minimum == null && maximum == null) {
            logger.trace("No price filtering parameter given, skipping");
            dto.setProducts(products);
            return dto;
        }
        if (minimum == null) {
            minimum = (float) 0;
        }
        if (maximum == null) {
            maximum = (float) 999999999;
        }
        logger.trace("Filtering price between {} and {}", minimum, maximum);
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

    Product get(Integer id) throws NotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Ad not found!"));
    }

}
