package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.model.Category;
import com.codecool.projectkifli.model.CategoryPostData;
import com.codecool.projectkifli.model.User;
import com.codecool.projectkifli.service.CategoryService;
import com.codecool.projectkifli.service.UserService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @GetMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Category> findAll() {
        logger.trace("Get all categories");
        return categoryService.findAll();
    }

    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void add(@RequestBody CategoryPostData data, Principal principal, HttpServletResponse response) {
        if (principal == null) {
            logger.error("User trying to add category is not logged in");
            return;
        }
        logger.trace("Post by {}", principal.getName());
        try {
            User user = userService.get(principal.getName());
            Category category = categoryService.add(data);
            logger.info("{} added category {}", user.getUsername(), category.getId());
        } catch (NotFoundException e) {
            logger.error("Did not find user {}", principal.getName());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setHeader("errorMessage", e.getMessage());
        }
    }

}
