package com.example.ems.dto;

import com.example.ems.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectAssignedResponse {
    private Long id;
    private Role role;
    private EmployeeResponse employee;
    private ProjectResponse project;
}

