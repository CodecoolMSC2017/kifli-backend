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
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private CategoryAttributeRepository categoryAttributeRepository;

    public ProductListDto findAll() {
        logger.debug("Finding all products");
        ProductListDto dto = new ProductListDto(categoryRespository.findAll());
        dto.setProducts(productListItemRepository.findAll());
        return dto;
    }

    public ProductDetailsDto findById(Integer id) {
        logger.debug("Getting product {}", id);
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            logger.error("Did not find product {}", id);
            return null;
        }
        return new ProductDetailsDto(product);
    }

    public void delete(Integer id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        if (user == null) {
            logger.error("Did not find user {} trying to delete product {}", principal.getName(), id);
            return;
        }
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            logger.error("Did not find product {}", id);
            return;
        }
        if (product.getOwner().getId().equals(user.getId()) || user.getAuthorities().contains("ROLE_ADMIN")) {
            logger.info("Deleting product {}", id);
            productRepository.deleteById(id);
            return;
        }
        logger.error("User {} is not authorized to delete product {}", principal.getName(), id);
        throw new AccessDeniedException("TAKA");
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
        ProductListDto dto = new ProductListDto(categoryRespository.findAll());
        dto.setProducts(productListItemRepository.findAllByUserId(userId));
        return dto;
    }

    public ProductDetailsDto add(ProductPostData data, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        if (user == null) {
            logger.error("Did not find user {}", principal.getName());
            return null;
        }
        Category category = categoryRespository.findById(data.getCategoryId()).orElse(null);
        if (category == null) {
            logger.error("Did not find category {}", data.getCategoryId());
            return null;
        }
        Product product = new Product();
        if (!setAttributesOfProduct(data, product)) {
            return null;
        }
        product.setOwner(user);
        List<ProductAttribute> productAttributes = setupProductAttributes(data.getAttributes(), null, category);
        product.setAttributes(productAttributes);
        product.setCategory(category);

        logger.trace("Saving new product");
        Product save = productRepository.save(product);
        logger.info("Added new product {}", save.getId());
        return findById(save.getId());
    }

    @Transactional
    public void update(ProductDetailsDto dto, String username) {
        Product product = productRepository.findById(dto.getId()).orElse(null);
        if (product == null) {
            logger.error("Did not find product {}", dto.getId());
            return;
        }
        if (!product.getOwner().getUsername().equals(username)) {
            logger.warn("User {} is not allowed update product of user {}", username, product.getOwner().getUsername());
            return;
        }
        if (!updateBasicProductAttributes(product, dto)) {
            return;
        }
        updateCategoryAttributes(dto, product);
        logger.trace("Saving changes of product {}", product.getId());
        productRepository.save(product);
        logger.info("Updated product {}", product.getId());
    }

    private boolean setAttributesOfProduct(ProductPostData data, Product product) {
        logger.trace("Setting basic attributes");

        if (!setBasicAttributesOfProduct(product, data.getTitle(), data.getDescription(), data.getPrice(), data.getType())) {
            return false;
        }

        product.setState("FOR SALE");
        String formattedDate = simpleDateFormat.format(new Date());
        try {
            product.setUploadDate(simpleDateFormat.parse(formattedDate));
            product.setPremiumExpirationDate(simpleDateFormat.parse("2020/12/02"));
        } catch (ParseException e) {
            logger.error("ParseException at parsing date");
            return false;
        }
        return true;
    }

    private void setupNewCategory(Product product, ProductDetailsDto dto) {
        logger.trace("Setting up new category");
        Category category = categoryRespository.findById(dto.getCategoryId()).orElse(null);
        if (category == null) {
            logger.error("Did not find category {}", dto.getCategoryId());
            return;
        }
        removeCurrentAttributes(product);
        List<ProductAttribute> attributes = setupProductAttributes(dto.getAttributes(), product.getId(), category);
        product.setCategory(category);
        product.setAttributes(attributes);
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

    @Transactional
    private void removeCurrentAttributes(Product product) {
        logger.trace("Deleting current attributes");
        productAttributeRepository.deleteAllByProductId(product.getId());
        productAttributeRepository.flush();
    }

    private boolean updateBasicProductAttributes(Product product, ProductDetailsDto dto) {
        logger.trace("Updating basic attributes");
         return setBasicAttributesOfProduct(product, dto.getTitle(), dto.getDescription(), dto.getPrice(), dto.getType());
    }

    private boolean setBasicAttributesOfProduct(Product product, String title, String description, Float price, String type) {
        if (title == null || title.equals("")) {
            logger.error("Title can't be empty!");
            return false;
        }
        product.setTitle(title);

        if (description == null || description.equals("")) {
            logger.error("Description can't be empty!");
            return false;
        }
        product.setDescription(description);

        if (price == null || price < 0.1) {
            logger.error("Price can't be empty or less than 0.1!");
            return false;
        }
        product.setPrice(price);

        if (type == null || type.equals("")) {
            logger.error("Type can't be empty!");
            return false;
        }
        product.setType(type);
        return true;
    }

    private List<ProductAttribute> setupProductAttributes(Map<String, String> attributes, Integer productId, Category category) {
        logger.trace("Setting up category attributes");
        List<ProductAttribute> productAttributes = new ArrayList<>();

        for (CategoryAttribute attribute : category.getAttributes()) {
            ProductAttribute productAttribute = new ProductAttribute();
            productAttribute.setAttributeId(attribute.getId());
            productAttribute.setProductId(productId);
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
        ProductListDto dto = new ProductListDto(categoryRespository.findAll());
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
}
