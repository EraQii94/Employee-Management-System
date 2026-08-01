package com.example.ems.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @NotBlank
    private Double budget;

}
