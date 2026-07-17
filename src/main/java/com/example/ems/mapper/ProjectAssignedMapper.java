package com.example.ems.mapper;

import com.example.ems.dto.ProjectAssignedRequest;
import com.example.ems.dto.ProjectAssignedResponse;
import com.example.ems.entity.Department;
import com.example.ems.entity.ProjectAssigned;
import com.example.ems.exception.RequiredRequest;

public class ProjectAssignedMapper {

    public static ProjectAssignedResponse toResponse(ProjectAssigned pa){
        if(pa == null) return null;
        ProjectAssignedResponse res = new ProjectAssignedResponse();
        res.setId(pa.getId());
        res.setRole(pa.getRole());
        res.setEmployee(EmployeeMapper.toResponse(pa.getEmployee()));
        res.setProject(ProjectMapper.toResponse(pa.getProject()));
        return res;
    }



}

