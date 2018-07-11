package com.codecool.projectkifli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ProjectKifliApplication {

	@RequestMapping("/")
	String home() {
		return "This will be the magnificent page of the Kifli Project!";
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjectKifliApplication.class, args);
	}
}