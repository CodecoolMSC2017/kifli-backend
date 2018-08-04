package com.codecool.projectkifli.dto;

import com.codecool.projectkifli.model.Category;
import com.codecool.projectkifli.model.ProductListItem;

import java.util.List;

public class ProductListDto {

    private List<ProductListItem> products;
    private List<Category> categories;

    public ProductListDto(List<Category> categories) {
        this.categories = categories;
    }

    public void setProducts(List<ProductListItem> products) {
        this.products = products;
    }

    public List<ProductListItem> getProducts() {
        return products;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
