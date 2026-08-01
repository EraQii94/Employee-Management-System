package com.example.ems.controller;

import com.example.ems.dto.ProjectAssignedRequest;
import com.example.ems.dto.ProjectAssignedResponse;
import com.example.ems.service.ProjectAssignedService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projectassignment")
@RequiredArgsConstructor
@Tag(name = "Project Assignments", description = "Operations for assigning employees to projects")
public class ProjectAssignedController {

    private final ProjectAssignedService projectAssignedService;

    @Operation(summary = "Assign employee to project", description = "Assign a single employee to a project with a role")
    @PostMapping("/assignments")
    public ResponseEntity<ProjectAssignedResponse> assign(@Valid @RequestBody ProjectAssignedRequest request){
        ProjectAssignedResponse res = projectAssignedService.assignEmployeeToProject(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/projects/{projectId}/assignments")
    @Operation(summary = "Assign multiple employees to project", description = "Assign multiple employees to a project")
    public ResponseEntity<List<ProjectAssignedResponse>> assignMultipleToProject(@PathVariable Long projectId,
                                                                                  @Valid @RequestBody List<ProjectAssignedRequest> requests){
        // ensure projectId is applied to each request
        for(ProjectAssignedRequest r : requests){
            r.setProjectId(projectId);
        }
        List<ProjectAssignedResponse> res = projectAssignedService.assignMultipleEmployeesToProject(requests);
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "Get employees by project", description = "List all employees (with roles) assigned to a project")
    @GetMapping("/projects/{projectId}/employees")
    public ResponseEntity<List<ProjectAssignedResponse>> getEmployeesByProject(@PathVariable Long projectId){
        List<ProjectAssignedResponse> res = projectAssignedService.getEmployeesByProject(projectId);
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "Get projects by employee", description = "List all projects (with roles) for a specific employee")
    @GetMapping("/employees/{employeeId}/projects")
    public ResponseEntity<List<ProjectAssignedResponse>> getProjectsByEmployee(@PathVariable Long employeeId){
        List<ProjectAssignedResponse> res = projectAssignedService.getProjectsByEmployee(employeeId);
        return ResponseEntity.ok(res);
    }

}

