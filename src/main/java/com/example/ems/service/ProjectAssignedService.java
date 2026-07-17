package com.example.ems.service;

import com.example.ems.dto.ProjectAssignedRequest;
import com.example.ems.dto.ProjectAssignedResponse;
import com.example.ems.entity.Employee;
import com.example.ems.entity.Project;
import com.example.ems.entity.ProjectAssigned;
import com.example.ems.exception.EntityNotFound;
import com.example.ems.mapper.ProjectAssignedMapper;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.repository.ProjectAssignedRepository;
import com.example.ems.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectAssignedService {

    private final ProjectAssignedRepository projectAssignedRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    public ProjectAssignedResponse assignEmployeeToProject(ProjectAssignedRequest request){
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new EntityNotFound("Employee not found with ID: " + request.getEmployeeId()));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new EntityNotFound("Project not found with ID: " + request.getProjectId()));

        ProjectAssigned pa = new ProjectAssigned();
        pa.setEmployee(employee);
        pa.setProject(project);
        pa.setRole(request.getRole());

        projectAssignedRepository.save(pa);
        return ProjectAssignedMapper.toResponse(pa);
    }

    public List<ProjectAssignedResponse> assignMultipleEmployeesToProject(List<ProjectAssignedRequest> requests){
        return requests
                .stream()
                .map(this::assignEmployeeToProject)
                .toList();
    }

    public List<ProjectAssignedResponse> getEmployeesByProject(Long projectId){
        // ensure project exists
        projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFound("Project not found with ID: " + projectId));

        List<ProjectAssigned> list = projectAssignedRepository.findByProjectId(projectId);
        return list
                .stream()
                .map(ProjectAssignedMapper::toResponse)
                .toList();
    }

    public List<ProjectAssignedResponse> getProjectsByEmployee(Long employeeId){
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFound("Employee not found with ID: " + employeeId));

        List<ProjectAssigned> list = projectAssignedRepository.findByEmployeeId(employeeId);

        return list
                .stream()
                .map(ProjectAssignedMapper::toResponse)
                .toList();
    }

}

