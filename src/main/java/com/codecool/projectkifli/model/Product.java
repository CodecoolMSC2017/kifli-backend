package com.codecool.projectkifli.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product_ads")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Float price;
    private String type;
    private String state;
    private Date uploadDate;
    private Date premiumExpirationDate;

    @ElementCollection
    @CollectionTable(
            name = "product_pictures",
            joinColumns = @JoinColumn(name = "productId", referencedColumnName = "id")
    )
    @Column(name = "id")
    private List<Integer> pictureIds;

    @OneToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private List<ProductAttribute> attributes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User owner;

    public Product() {
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

    public Date getUploadDate() {
        return uploadDate;
    }

    public Date getPremiumExpirationDate() {
        return premiumExpirationDate;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Integer> getPictureIds() {
        return pictureIds;
    }

    public void setPictureIds(List<Integer> pictureIds) {
        this.pictureIds = pictureIds;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ProductAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttribute> attributes) {
        this.attributes = attributes;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                ", uploadDate=" + uploadDate +
                ", premiumExpirationDate=" + premiumExpirationDate +
                ", pictureIds=" + pictureIds +
                ", category=" + category +
                ", attributes=" + attributes +
                ", owner=" + owner +
                '}';
    }
}
