package com.example.employeeallocation.demo.repo;

import com.example.employeeallocation.demo.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findByPrimarySkillAndSecondarySkill(String primarySkill, String secondarySkill);
    List<Employee> findByPrimarySkillIsNull(String primarySkill);
    List<Employee> findByAllocatedProjectsContaining(String projectId);
}
