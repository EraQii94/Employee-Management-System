package com.example.ems.dto;

import com.example.ems.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAssignedRequest {
    private Long projectId;
    private Long employeeId;
    private Role role;
}

