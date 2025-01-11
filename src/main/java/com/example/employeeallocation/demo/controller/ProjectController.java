package com.example.employeeallocation.demo.controller;

import com.example.employeeallocation.demo.model.Project;
import com.example.employeeallocation.demo.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/add")
    public Project addProject(@RequestBody Project project) {
        return projectService.saveProject(project);
    }
}
