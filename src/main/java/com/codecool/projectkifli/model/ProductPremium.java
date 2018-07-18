package com.codecool.projectkifli.model;

import javax.persistence.*;

@Entity
@Table(name = "premium_ads")
public class ProductPremium {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;
    private String expirationDate;

    public ProductPremium() {
    }

    public ProductPremium(Integer productId, String expirationDate) {
        this.productId = productId;
        this.expirationDate = expirationDate;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
