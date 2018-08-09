package com.codecool.projectkifli.repository;

import com.codecool.projectkifli.model.CategoryAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, Integer> {

    CategoryAttribute findByName(String name);

}
