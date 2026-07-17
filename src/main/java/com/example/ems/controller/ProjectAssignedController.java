package com.example.ems.controller;

import com.example.ems.dto.ProjectAssignedRequest;
import com.example.ems.dto.ProjectAssignedResponse;
import com.example.ems.service.ProjectAssignedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projectassignment")
@RequiredArgsConstructor
public class ProjectAssignedController {

    private final ProjectAssignedService projectAssignedService;

    @PostMapping("/assignments")
    public ResponseEntity<ProjectAssignedResponse> assign(@RequestBody ProjectAssignedRequest request){
        ProjectAssignedResponse res = projectAssignedService.assignEmployeeToProject(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/projects/{projectId}/assignments")
    public ResponseEntity<List<ProjectAssignedResponse>> assignMultipleToProject(@PathVariable Long projectId, @RequestBody List<ProjectAssignedRequest> requests){
        // ensure projectId is applied to each request
        for(ProjectAssignedRequest r : requests){
            r.setProjectId(projectId);
        }
        List<ProjectAssignedResponse> res = projectAssignedService.assignMultipleEmployeesToProject(requests);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/projects/{projectId}/employees")
    public ResponseEntity<List<ProjectAssignedResponse>> getEmployeesByProject(@PathVariable Long projectId){
        List<ProjectAssignedResponse> res = projectAssignedService.getEmployeesByProject(projectId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/employees/{employeeId}/projects")
    public ResponseEntity<List<ProjectAssignedResponse>> getProjectsByEmployee(@PathVariable Long employeeId){
        List<ProjectAssignedResponse> res = projectAssignedService.getProjectsByEmployee(employeeId);
        return ResponseEntity.ok(res);
    }

}

