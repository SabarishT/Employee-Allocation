package com.example.employeeallocation.demo.service;

import com.example.employeeallocation.demo.model.Employee;
import com.example.employeeallocation.demo.repo.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        employee1 = new Employee();
        employee1.setId("E001");
        employee1.setName("John Doe");
        employee1.setExperience(5);
        employee1.setPrimarySkill("Java");
        employee1.setSecondarySkill("Spring Boot");
        employee1.setAllocatedProjects(Arrays.asList("ProjectA"));

        employee2 = new Employee();
        employee2.setId("E002");
        employee2.setName("Jane Doe");
        employee2.setExperience(8);
        employee2.setPrimarySkill("Java");
        employee2.setSecondarySkill("Microservices");
        employee2.setAllocatedProjects(Arrays.asList("ProjectA"));
    }

    @Test
    void testSaveEmployee() {
        when(employeeRepository.save(employee1)).thenReturn(employee1);

        Employee savedEmployee = employeeService.saveEmployee(employee1);

        assertNotNull(savedEmployee);
        assertEquals("John Doe", savedEmployee.getName());
        verify(employeeRepository, times(1)).save(employee1);
    }

    @Test
    void testAllocateEmployeeToProject() {
        doNothing().when(kafkaProducerService).sendMessage(anyString(), anyString());

        employeeService.allocateEmployeeToProject(employee1);

        verify(employeeRepository, times(1)).save(employee1);
        verify(kafkaProducerService, times(1))
                .sendMessage(eq("employee-allocation"), contains("Employee John Doe allocated"));
    }

    @Test
    void testGetSecondMostExperiencedEmployee() {
        when(employeeRepository.findByAllocatedProjectsContaining("ProjectA"))
                .thenReturn(Arrays.asList(employee2, employee1));

        Optional<Employee> result = employeeService.getSecondMostExperiencedEmployee("ProjectA");

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
    }

    @Test
    void testGetSecondMostExperiencedEmployee_NoSecondEmployee() {
        when(employeeRepository.findByAllocatedProjectsContaining("ProjectA"))
                .thenReturn(List.of(employee1));

        Optional<Employee> result = employeeService.getSecondMostExperiencedEmployee("ProjectA");

        assertFalse(result.isPresent());
    }

    @Test
    void testGetEmployeesBySkills() {
        when(employeeRepository.findByPrimarySkillAndSecondarySkill("Java", "Spring Boot"))
                .thenReturn(List.of(employee1));

        List<Employee> employees = employeeService.getEmployeesBySkills("Java", "Spring Boot");

        assertFalse(employees.isEmpty());
        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.get(0).getName());
    }

    @Test
    void testGetEmployeesByNotAllocatedWithPrimarySkill() {
        when(employeeRepository.findByPrimarySkillIsNull("Java")).thenReturn(List.of());

        List<Employee> employees = employeeService.getEmployeesByNotAllocatedWithPrimarySkill("Java");

        assertTrue(employees.isEmpty());
    }
}

