package com.example.ems.dto;

import com.example.ems.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAssignedRequest {

    @NotNull
    private Long projectId;
    @NotNull
    private Long employeeId;
    @NotNull
    private Role role;
}

