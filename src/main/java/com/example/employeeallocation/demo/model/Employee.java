package com.example.employeeallocation.demo.model;

import com.example.employeeallocation.demo.enums.CapabilityCentre;
import com.example.employeeallocation.demo.enums.Designation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    private String id;
    private String name;
    private CapabilityCentre capabilityCentre;
    private LocalDate dateOfJoining;
    private Designation designation;
    private String primarySkill;
    private String secondarySkill;
    private int experience;
    private List<String> allocatedProjects;
}

