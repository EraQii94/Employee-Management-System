package com.example.ems.mapper;

import com.example.ems.dto.ProjectRequest;
import com.example.ems.dto.ProjectResponse;
import com.example.ems.entity.Project;

public class ProjectMapper {
    public static Project toEntity(ProjectRequest request){
        if(request == null){
            return null;
        }

        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        return project;
    }


    public static ProjectResponse toResponse(Project project){
        if(project == null){
            return null;
        }
        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setId(project.getId());
        projectResponse.setName(project.getName());
        projectResponse.setDescription(project.getDescription());
        projectResponse.setStartDate(project.getStartDate());
        projectResponse.setEndDate(project.getEndDate());

        return projectResponse;
    }
}
