package com.codecool.projectkifli.repository;

import com.codecool.projectkifli.model.ProductListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductListItemRepository extends JpaRepository<ProductListItem, Integer> {

    List<ProductListItem> findAllByUserId(Integer userId);

    List<ProductListItem> findByCategoryId(Integer id);
}
