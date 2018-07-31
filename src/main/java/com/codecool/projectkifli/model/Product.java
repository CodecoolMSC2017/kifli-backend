package com.codecool.projectkifli.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "product_ads")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private String title;
    private String description;
    private Float price;
    private String type;
    private String state;
    private String uploadDate;
    private String premiumExpirationDate;

    @ElementCollection
    @CollectionTable(
            name = "product_pictures",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
    )
    @Column(name = "id")
    private List<Integer> pictureIds;

    public Product() {
    }

    public Product(
            Integer id,
            Integer userId,
            String title,
            String description,
            Float price,
            String type,
            String state,
            String uploadDate,
            String premiumExpirationDate) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.type = type;
        this.state = state;
        this.uploadDate = uploadDate;
        this.premiumExpirationDate = premiumExpirationDate;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setPremiumExpirationDate(String premiumExpirationDate) {
        this.premiumExpirationDate = premiumExpirationDate;
    }

    public List<Integer> getPictureIds() {
        return pictureIds;
    }

    public void setPictureIds(List<Integer> pictureIds) {
        this.pictureIds = pictureIds;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                ", uploadDate='" + uploadDate + '\'' +
                ", premiumExpirationDate='" + premiumExpirationDate + '\'' +
                ", pictureIds=" + pictureIds +
                '}';
    }
}
