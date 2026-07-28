package com.example.ems.mapper;

import com.example.ems.dto.DepartmentRequest;
import com.example.ems.dto.DepartmentResponse;
import com.example.ems.entity.Department;

public class DepartmentMapper {

    ///To Entity Mapping
    public static Department toEntity (DepartmentRequest request) {
        //check first
        if(request == null){
            return null;
        }
        Department department = new Department();

        department.setName(request.getName());
        department.setLocation(request.getLocation());
        department.setBudget(request.getBudget());

        return department;
    }

    ///To DTO Mapping
    public static DepartmentResponse toResponse(Department department) {
        if(department == null){
            return null;
        }
        DepartmentResponse departmentResponse = new DepartmentResponse();

        departmentResponse.setId(department.getId());
        departmentResponse.setName(department.getName());
        departmentResponse.setLocation(department.getLocation());
        departmentResponse.setBudget(department.getBudget());

        return departmentResponse;
    }

}