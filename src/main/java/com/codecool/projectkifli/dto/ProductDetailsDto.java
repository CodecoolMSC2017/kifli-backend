package com.codecool.projectkifli.dto;

import com.codecool.projectkifli.model.CategoryAttribute;
import com.codecool.projectkifli.model.Product;
import com.codecool.projectkifli.model.ProductAttribute;
import com.codecool.projectkifli.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsDto {

    private Integer id;
    private String title;
    private String description;
    private Float price;
    private String type;
    private String state;
    private String uploadDate;
    private String premiumExpirationDate;
    private List<Integer> pictureIds;
    private String categoryName;
    private Map<String, String> attributes;
    private String ownerEmail;
    private String ownerPhone = "test";

    public ProductDetailsDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.type = product.getType();
        this.state = product.getState();
        this.uploadDate = product.getUploadDate();
        this.premiumExpirationDate = product.getPremiumExpirationDate();
        this.pictureIds = product.getPictureIds();
        this.categoryName = product.getCategory().getName();

        this.attributes = setupAttributes(product);

        User owner = product.getOwner();
        this.ownerEmail = owner.getEmail();
        // this.ownerPhone = owner.getCredentials().getPhone();
    }

    private Map<String, String> setupAttributes(Product product) {
        Map<String, String> attributes = new HashMap<>();
        for (ProductAttribute attribute : product.getAttributes()) {
            CategoryAttribute categoryAttribute = getCategoryAttributeById(
                    attribute.getAttributeId(),
                    product.getCategory().getAttributes()
            );
            attributes.put(categoryAttribute.getName(), attribute.getValue());
        }
        return attributes;
    }

    private CategoryAttribute getCategoryAttributeById(Integer attributeId, List<CategoryAttribute> attributes) {
        for (CategoryAttribute categoryAttribute : attributes) {
            if (categoryAttribute.getId().equals(attributeId)) {
                return categoryAttribute;
            }
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getState() {
        return state;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public String getPremiumExpirationDate() {
        return premiumExpirationDate;
    }

    public List<Integer> getPictureIds() {
        return pictureIds;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }
}
