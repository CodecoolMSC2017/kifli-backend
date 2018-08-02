package com.codecool.projectkifli.repository;

import com.codecool.projectkifli.model.ProductListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductListItemRepository extends JpaRepository<ProductListItem, Integer> {

    @Query(value = "SELECT * FROM product_ads WHERE lower(title) LIKE %?1%", nativeQuery = true)
    List<ProductListItem> findBySearchTitleString(String searchString);

    List<ProductListItem> findAllByUserId(Integer userId);

    List<ProductListItem> findByCategoryId(Integer id);

    @Query(value = "SELECT * FROM product_ads WHERE lower(title) LIKE %?1% " +
            "AND category_id = ?2", nativeQuery = true)
    List<ProductListItem> findBySearchTitleStringAndCategoryId(String searchString, Integer categoryId);
}
