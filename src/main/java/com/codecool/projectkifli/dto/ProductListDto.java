package com.codecool.projectkifli.dto;

import com.codecool.projectkifli.model.Category;
import com.codecool.projectkifli.model.ProductListItem;

import java.util.List;

public class ProductListDto {

    private List<ProductListItem> products;
    private List<Category> categories;
    private Integer page;
    private Integer totalPages;
    private Boolean activation;

    public List<ProductListItem> getProducts() {
        return products;
    }

    public void setProducts(List<ProductListItem> products) {
        this.products = products;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Boolean getActivation() {
        return activation;
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
    }
}
