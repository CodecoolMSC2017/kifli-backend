package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.model.Category;
import com.codecool.projectkifli.repository.CategoryRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRespository categoryRespository;

    @GetMapping(
            value = "/categories",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Category> findAll() {
        return categoryRespository.findAll();
    }

}
