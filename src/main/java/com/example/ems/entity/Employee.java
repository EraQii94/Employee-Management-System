package com.example.ems.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false, unique = true)
    @Email
    @NotBlank
    private String email;


    @Pattern(regexp = "^[+]?[0-9]{10,13}$", message = "Phone number must be 10-13 digits")
    @NotBlank
    private String phoneNumber;


    @NotBlank
    private LocalDate hireDate;

    @NotBlank
    @Positive
    private double salary;

    //Relations to other Entities
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "employee")
    private List<ProjectAssigned> projectAssigneds;

    //constructor
    public Employee(String name, String email, String phoneNumber, LocalDate hireDate, double salary) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.salary = salary;
    }

}
