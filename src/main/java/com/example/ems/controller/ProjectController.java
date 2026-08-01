package com.example.ems.controller;


import com.example.ems.dto.ProjectRequest;
import com.example.ems.dto.ProjectResponse;
import com.example.ems.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/project")
@Tag(name = "Project Management", description = "Operations for managing projects")
public class ProjectController {
    private final ProjectService  projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    ////////////////////////////////////////////////////
    ///1-post Mapping
    @Operation(summary = "Create project", description = "Create a new project")
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest project) {
        ProjectResponse response = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    ///2-get Mapping
    @Operation(summary = "Get projects", description = "Get all projects or a specific project by id")
    @GetMapping("/")
    public ResponseEntity<List<ProjectResponse>> getAllProject(@RequestParam(required = false) Long id) {
        return ResponseEntity.ok().body(projectService.getAllProjects(id));
    }

    ///3-put Mapping
    @Operation(summary = "Update project", description = "Update an existing project")
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id,
                                                         @Valid @RequestBody ProjectRequest project) {
        projectService.updateProject(id, project);
        return ResponseEntity.ok().build();
    }

    ///4-delete Mapping
    @Operation(summary = "Delete project", description = "Delete a project by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectResponse> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete all projects", description = "Delete all projects")
    @DeleteMapping
    public ResponseEntity<ProjectResponse> deleteAllProjects() {
        projectService.deleteAllProjects();
        return ResponseEntity.ok().build();
    }

    /// 5- put Mapping to assign Project to department
    @Operation(summary = "Assign project to department", description = "Assign a project to a department")
    @PutMapping("/assign/{id}")
    public ResponseEntity<Void> assignProject(@PathVariable Long id,
                                              @RequestParam Long departmentId){

        projectService.assignProjectToDepartment(id, departmentId);
        return ResponseEntity.ok().build();
    }


}
