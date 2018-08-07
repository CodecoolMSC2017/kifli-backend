package com.codecool.projectkifli.service;

import com.codecool.projectkifli.model.Category;
import com.codecool.projectkifli.model.CategoryAttribute;
import com.codecool.projectkifli.model.CategoryPostData;
import com.codecool.projectkifli.repository.CategoryRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRespository categoryRespository;


    public List<Category> findAll() {
        logger.debug("Finding all categories");
        return categoryRespository.findAll();
    }

    public Category add(CategoryPostData data) {
        Category category = new Category();
        category.setName(data.getName());
        category.setAttributes(getCategoryAttributes(data.getAttributes()));
        return categoryRespository.save(category);
    }

    private List<CategoryAttribute> getCategoryAttributes(List<Map<String, String>> data) {
        List<CategoryAttribute> categoryAttributes = new ArrayList<>();
        for (Map<String, String> attribute : data) {
            CategoryAttribute categoryAttribute = new CategoryAttribute();
            categoryAttribute.setName(attribute.get("name"));
            categoryAttribute.setType(attribute.get("type"));
            categoryAttributes.add(categoryAttribute);
        }
        return categoryAttributes;
    }
}
