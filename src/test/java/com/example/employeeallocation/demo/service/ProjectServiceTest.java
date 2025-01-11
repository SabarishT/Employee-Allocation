package com.example.employeeallocation.demo.service;

import com.example.employeeallocation.demo.enums.AccountName;
import com.example.employeeallocation.demo.model.Project;
import com.example.employeeallocation.demo.repo.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testSaveProject() {
        // Arrange
        Project project = new Project();
        project.setId("PROJ001");
        project.setAccountName(AccountName.CALIBO_LLC);
        project.setProjectName("Digital Transformation");
        project.setAllocation(1.0F);
        project.setStartDate(LocalDate.parse("2023-01-01"));
        project.setEndDate(LocalDate.parse("2023-12-31"));

        when(projectRepository.save(project)).thenReturn(project);

        // Act
        Project savedProject = projectService.saveProject(project);

        // Assert
        assertNotNull(savedProject);
        assertEquals("PROJ001", savedProject.getId());
        verify(projectRepository, times(1)).save(project);
    }
}

