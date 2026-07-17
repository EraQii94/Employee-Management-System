package com.example.ems.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest {
    private String name;

    private String location;

    private Double budget;

}
