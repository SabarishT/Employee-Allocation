package com.example.employeeallocation.demo.controller;

import com.example.employeeallocation.demo.model.Employee;
import com.example.employeeallocation.demo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        if (employee.getAllocatedProjects() != null && employee.getAllocatedProjects().size() > 3) {
            return ResponseEntity.badRequest().body("An employee cannot be allocated to more than 3 projects.");
        }
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping("/project/{projectId}/second-most-experienced")
    public ResponseEntity<Employee> getSecondMostExperiencedEmployee(@PathVariable String projectId) {
        return employeeService.getSecondMostExperiencedEmployee(projectId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/skills")
    public List<Employee> getEmployeesBySkills(
            @RequestParam String primarySkill,
            @RequestParam String secondarySkill) {
        return employeeService.getEmployeesBySkills(primarySkill, secondarySkill);
    }

    @GetMapping("/unallocated")
    public List<Employee> getEmployeesByNotAllocatedWithPrimarySkill(@RequestParam String primarySkill) {
        return employeeService.getEmployeesByNotAllocatedWithPrimarySkill(primarySkill);
    }
}

