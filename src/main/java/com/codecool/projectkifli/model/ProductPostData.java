package com.codecool.projectkifli.model;

import java.util.Map;

public class ProductPostData {

    private String title;
    private String description;
    private Float price;
    private String type;
    private Map<String, String> attributes;
    private Integer categoryId;
    private Boolean activation;

    public ProductPostData() {
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

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getActivation() {
        return activation;
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
    }

    @Override
    public String toString() {
        return "ProductPostData{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", attributes=" + attributes +
                ", categoryId=" + categoryId +
                ", activation=" + activation +
                '}';
    }

}
