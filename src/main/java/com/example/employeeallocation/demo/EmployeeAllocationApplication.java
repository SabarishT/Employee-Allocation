package com.example.employeeallocation.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EmployeeAllocationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeAllocationApplication.class, args);
    }

}
