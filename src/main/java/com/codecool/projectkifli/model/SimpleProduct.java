package com.codecool.projectkifli.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_ads")
public class SimpleProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private Integer categoryId;
    private String title;
    private String description;
    private Float price;
    private String type;
    private String state;
    private Date uploadDate;
    private Date premiumExpirationDate;

    public SimpleProduct() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getCategoryId() {
        return categoryId;
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

    public Date getUploadDate() {
        return uploadDate;
    }

    public Date getPremiumExpirationDate() {
        return premiumExpirationDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setPremiumExpirationDate(Date premiumExpirationDate) {
        this.premiumExpirationDate = premiumExpirationDate;
    }

}
