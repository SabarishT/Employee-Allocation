package com.example.employeeallocation.demo.model;

import com.example.employeeallocation.demo.enums.AccountName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "projects")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    private String id;
    private AccountName accountName;
    private String projectName;
    private float allocation;
    private LocalDate startDate;
    private LocalDate endDate;
}

