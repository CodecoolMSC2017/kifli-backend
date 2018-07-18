package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class CredentialsController {

    @Autowired
    private CredentialsRepository credentialsRepository;
}
