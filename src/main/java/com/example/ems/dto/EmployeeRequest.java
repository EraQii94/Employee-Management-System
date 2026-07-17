package com.example.ems.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private String name;


    private String email;

    private String phoneNumber;


    private LocalDate hireDate;


    private Double salary;

    private Long departmentId;
}
