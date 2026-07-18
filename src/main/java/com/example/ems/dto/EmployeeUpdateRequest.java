package com.example.ems.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeUpdateRequest {

    private String name;

    @Email
    private String email;

    @Pattern(regexp = "^[+]?[0-9]{10,13}$", message = "Phone number must be 10-13 digits")
    private String phoneNumber;

    private LocalDate hireDate;

    @Positive
    private Double salary;

    private Long departmentId;
}

