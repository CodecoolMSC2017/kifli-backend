package com.codecool.projectkifli.dto;

import com.codecool.projectkifli.model.Product;

import java.util.List;

public class AdSpaceDto {

    private Integer id;
    private String title;
    private Float price;
    private String type;
    private List<Integer> pictureIds;

    public AdSpaceDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.type = product.getType();
        this.pictureIds = product.getPictureIds();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Float getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public List<Integer> getPictureIds() {
        return pictureIds;
    }
}
