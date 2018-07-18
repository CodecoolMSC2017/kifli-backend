package com.codecool.projectkifli.repository;

import com.codecool.projectkifli.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {

    Credentials findByUserId (Integer id);
}
