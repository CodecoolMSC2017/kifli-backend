package com.codecool.projectkifli.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_ads")
public class ProductListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private String title;
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

    public ProductListItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getPremiumExpirationDate() {
        return premiumExpirationDate;
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
}
