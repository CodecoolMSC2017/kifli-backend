package com.codecool.projectkifli.repository;

import com.codecool.projectkifli.model.ProductListItem;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ProductListItemRepository extends JpaRepository<ProductListItem, Integer> {

    @Query(value = "SELECT * FROM product_ads WHERE lower(title) LIKE %?1% AND activation = true", nativeQuery = true)
    List<ProductListItem> findBySearchTitleString(String searchString);

    List<ProductListItem> findAllByUserId(Integer userId);

    @Query(value = "SELECT * FROM product_ads WHERE category_id = ?1 " +
            "AND activation = true", nativeQuery = true)
    List<ProductListItem> findByCategoryId(Integer id);

    @Query(value = "SELECT * FROM product_ads WHERE lower(title) LIKE %?1% " +
            "AND category_id = ?2 AND activation = true", nativeQuery = true)
    List<ProductListItem> findBySearchTitleStringAndCategoryId(String searchString, Integer categoryId);

    @Query(value = "SELECT * FROM product_ads WHERE activation = false", nativeQuery = true)
    List<ProductListItem> findNotActivationAds();

    @Query(value = "SELECT * FROM product_ads WHERE activation = true", nativeQuery = true)
    List<ProductListItem> findAllProduct();

    @Query(value = "SELECT * FROM product_ads WHERE activation = true", nativeQuery = true)
    Page<ProductListItem> findAllProduct(Pageable pageable);
}
