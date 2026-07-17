package com.example.ems.service;


import com.example.ems.dto.ProjectRequest;
import com.example.ems.dto.ProjectResponse;
import com.example.ems.entity.Department;
import com.example.ems.entity.Project;
import com.example.ems.exception.EntityNotFound;
import com.example.ems.exception.RequiredRequest;
import com.example.ems.mapper.ProjectMapper;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final DepartmentRepository departmentRepository;


    ///o Create projects with name, description, start date, and end date
    public ProjectResponse  createProject(ProjectRequest request) {
        if(request == null){
            throw new EntityNotFound("Project request cannot be null");
        }
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        projectRepository.save(project);
        ProjectResponse projectResponse = ProjectMapper.toResponse(project);
        return projectResponse;
    }

    ///o View all projects or filter by department
    public List<ProjectResponse> getAllProjects(Long departmentId) {
        List<Project> projects;
        if(departmentId == null){
            projects = projectRepository.findAll();
        } else {
            projects = projectRepository.findByDepartmentId(departmentId);
        }
        return projects.stream()
                .map(ProjectMapper::toResponse)
                .toList();
    }


    ///o Update project details
    public void updateProject(Long id, ProjectRequest request) {
        if(request == null){
            throw new RequiredRequest("Project request cannot be null");
        }
        if (id == null) {
            throw new RequiredRequest("Project ID cannot be null");
        }
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            Project existingProject = project.get();
            existingProject.setName(request.getName());
            existingProject.setDescription(request.getDescription());
            existingProject.setStartDate(request.getStartDate());
            existingProject.setEndDate(request.getEndDate());
            projectRepository.save(existingProject);
        } else {
            throw new EntityNotFound("Project not found with ID: " + id);
        }
        System.out.println("Project updated successfully: " + project.get().getName());

    }

    ///o Delete projects
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public void deleteAllProjects() {
        projectRepository.deleteAll();
    }


    ///o Assign projects to departments
    public void assignProjectToDepartment(Long projectId, Long departmentId) {
        Optional<Project> project = projectRepository.findById(projectId);
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (project.isPresent() && department.isPresent()) {
            Project existingProject = project.get();
            existingProject.setDepartment(department.get());
            projectRepository.save(existingProject);
        } else {
            throw new EntityNotFound("Project or Department not found");
        }
        System.out.println("Project assigned to department successfully: " + project.get().getName() + " -> " + department.get().getName());
    }


}
