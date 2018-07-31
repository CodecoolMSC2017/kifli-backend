package com.codecool.projectkifli.repository;

import com.codecool.projectkifli.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRespository extends JpaRepository<Category, Integer> {
}
