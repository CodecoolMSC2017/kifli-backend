package com.codecool.projectkifli.dto;

import com.codecool.projectkifli.model.*;

import java.util.Date;
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
    private Date uploadDate;
    private Date premiumExpirationDate;
    private List<Integer> pictureIds;
    private Integer categoryId;
    private String categoryName;
    private Map<String, String> attributes;
    private Integer ownerId;
    private String ownerName;
    private String ownerEmail;
    private String ownerPhone;
    private Boolean activation;

    public ProductDetailsDto() {
    }

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
        Category category = product.getCategory();
        this.categoryId = category.getId();
        this.categoryName = category.getName();

        this.attributes = setupAttributes(product);

        User owner = product.getOwner();
        this.ownerId = owner.getId();
        this.ownerName = owner.getFirstName() + " " + owner.getLastName();
        this.ownerEmail = owner.getEmail();
        this.ownerPhone = owner.getCredentials().getPhone();

        this.activation = product.getActivation();
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

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Date getPremiumExpirationDate() {
        return premiumExpirationDate;
    }

    public void setPremiumExpirationDate(Date premiumExpirationDate) {
        this.premiumExpirationDate = premiumExpirationDate;
    }

    public List<Integer> getPictureIds() {
        return pictureIds;
    }

    public void setPictureIds(List<Integer> pictureIds) {
        this.pictureIds = pictureIds;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public Boolean getActivation() {
        return activation;
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
    }
}
