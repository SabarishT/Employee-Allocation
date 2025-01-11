package com.example.employeeallocation.demo.service;

import com.example.employeeallocation.demo.model.Employee;
import com.example.employeeallocation.demo.repo.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final KafkaProducerService kafkaProducerService;

    public EmployeeService(EmployeeRepository employeeRepository, KafkaProducerService kafkaProducerService) {
        this.employeeRepository = employeeRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void allocateEmployeeToProject(Employee employee) {
        logger.info("Allocating employee to project");
        employeeRepository.save(employee);

        logger.info("employee-allocation Notifications sending into Kafka");
        kafkaProducerService.sendMessage("employee-allocation",
                "Employee " + employee.getName() + " allocated to " + employee.getAllocatedProjects());
    }

    public Optional<Employee> getSecondMostExperiencedEmployee(String projectId) {
        List<Employee> employees = employeeRepository.findByAllocatedProjectsContaining(projectId);

        List<Employee> sortedEmployees = employees.stream()
                .sorted((e1, e2) -> Integer.compare(e2.getExperience(), e1.getExperience()))
                .toList();

        return (sortedEmployees.size() > 1) ? Optional.of(sortedEmployees.get(1)) : Optional.empty();
    }

    @Cacheable(value = "employeesBySkills", key = "#primarySkill + '-' + #secondarySkill")
    public List<Employee> getEmployeesBySkills(String primarySkill, String secondarySkill) {
        return employeeRepository.findByPrimarySkillAndSecondarySkill(primarySkill, secondarySkill);
    }

    @Cacheable(value = "employeesByPrimarySkillNull", key = "#primarySkill")
    public List<Employee> getEmployeesByNotAllocatedWithPrimarySkill(String primarySkill) {
        return employeeRepository.findByPrimarySkillIsNull(primarySkill);
    }
}
