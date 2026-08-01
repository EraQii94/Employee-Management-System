package com.example.ems.controller;


import com.example.ems.dto.EmployeeRequest;
import com.example.ems.dto.EmployeeUpdateRequest;
import com.example.ems.dto.EmployeeResponse;
import com.example.ems.service.EmployeeService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@Tag(name = "Employee Management", description = "Operations for managing employees")
public class EmployeeController {

    public EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    ////////////////////////////////////////////////////////////////////////////////
    ///1-post mapping
    @Operation(summary = "Create employee", description = "Create a new employee")
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse created = employeeService.createEmployee(employeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    ///2-get Mapping by id
    @Operation(summary = "Get employee", description = "Retrieve an employee by id")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id) {
        EmployeeResponse emp = employeeService.getEmployeeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(emp);
    }

    ///3-get mapping
    @Operation(summary = "List employees", description = "Get all employees; optional filter by departmentId")
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(@RequestParam(required = false) Long departmentId){
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAllEmployees(departmentId));
    }

    ///4-put Mapping
    @Operation(summary = "Update employee", description = "Update an existing employee")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id,
                                                           @Valid @RequestBody EmployeeUpdateRequest employeeRequest){

        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeRequest));
    }

    ///5- put Mapping to assign employee to department
    @Operation(summary = "Assign employee to department", description = "Assign an employee to a department")
    @PutMapping("/assign/{employeeId}")
    public ResponseEntity<EmployeeResponse> assignEmployee(@PathVariable Long employeeId,
                                                           @RequestParam Long departmentId){

        return ResponseEntity.ok(employeeService.assignEmployeeToDepartment(employeeId, departmentId));

    }



    /// 6-delete employee
    @Operation(summary = "Delete employee", description = "Delete an employee by id")
    @DeleteMapping
    public ResponseEntity<Void> deleteEmployee(@RequestParam Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
