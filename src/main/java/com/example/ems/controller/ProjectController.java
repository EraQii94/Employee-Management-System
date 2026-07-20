package com.example.ems.controller;


import com.example.ems.dto.ProjectRequest;
import com.example.ems.dto.ProjectResponse;
import com.example.ems.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {
    private final ProjectService  projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    ////////////////////////////////////////////////////
    ///1-post Mapping
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest project) {
        ProjectResponse response = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    ///2-get Mapping
    @GetMapping("/{id}")
    public ResponseEntity<List<ProjectResponse>> getAllProject(@PathVariable(required = false) Long id) {
        return ResponseEntity.ok().body(projectService.getAllProjects(id));
    }

    ///3-put Mapping
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id,
                                                         @RequestBody ProjectRequest project) {
        projectService.updateProject(id, project);
        return ResponseEntity.ok().build();
    }

    ///4-delete Mapping
    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectResponse> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<ProjectResponse> deleteAllProjects() {
        projectService.deleteAllProjects();
        return ResponseEntity.ok().build();
    }

    /// 5- put Mapping to assign employee to department
    @PutMapping("/assign/{id}")
    public ResponseEntity<Void> assignProject(@PathVariable Long id,
                                              @RequestParam Long departmentId){

        projectService.assignProjectToDepartment(id, departmentId);
        return ResponseEntity.ok().build();
    }


}
