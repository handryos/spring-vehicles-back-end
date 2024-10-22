package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.NoRepositoryBean;

@EnableJpaRepositories(basePackages = "com.example.demo.repository")
@SpringBootApplication(scanBasePackages = { "com.example.demo" })
@EntityScan(basePackages = "com.example.demo.models")
@NoRepositoryBean
public class CrudApplication {

    public static void main(String[] args) {
	SpringApplication.run(CrudApplication.class, args);
    }

}
