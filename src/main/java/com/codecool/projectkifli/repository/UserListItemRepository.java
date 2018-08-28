package com.codecool.projectkifli.repository;

import com.codecool.projectkifli.model.UserListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserListItemRepository extends JpaRepository<UserListItem, Integer> {

}
