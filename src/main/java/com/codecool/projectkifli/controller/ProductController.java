package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.dto.AdSpaceDto;
import com.codecool.projectkifli.model.Product;
import com.codecool.projectkifli.model.User;
import com.codecool.projectkifli.repository.ProductRepository;
import com.codecool.projectkifli.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<AdSpaceDto> getAllProducts() {
        List<AdSpaceDto> adSpaceDtos = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        products.forEach(product -> adSpaceDtos.add(new AdSpaceDto(product)));
        return adSpaceDtos;
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Product findById(@PathVariable("id") Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Integer id, Principal principal) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        Product product = productRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if (product.getUserId().equals(user.getId()) || user.getAuthorities().contains("ROLE_ADMIN")) {
            productRepository.deleteById(id);
            return;
        }
        throw new AccessDeniedException("TAKA");
    }

    @GetMapping(
            value = "search/{searchString}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Product> findBySearchTitle(@PathVariable("searchString") String searchString) {
        List<Product> bySearchTitleString = productRepository.findBySearchTitleString(searchString.toLowerCase());

        for (Product product : bySearchTitleString) {
            System.out.println(product);
        }
        return bySearchTitleString;
    }
}
