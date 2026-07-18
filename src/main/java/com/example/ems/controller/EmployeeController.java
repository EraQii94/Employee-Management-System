package com.example.ems.controller;


import com.example.ems.dto.EmployeeRequest;
import com.example.ems.dto.EmployeeUpdateRequest;
import com.example.ems.dto.EmployeeResponse;
import com.example.ems.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    public EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    ////////////////////////////////////////////////////////////////////////////////
    ///1-post mapping
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse created = employeeService.createEmployee(employeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    ///2-get Mapping by id
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id) {
        EmployeeResponse emp = employeeService.getEmployeeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(emp);
    }

    ///3-get mapping
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(@RequestParam(required = false) Long departmentId){
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAllEmployees(departmentId));
    }

    ///4-put Mapping
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateRequest employeeRequest){
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeRequest));
    }

    ///5- put Mapping to assign employee to department
    @PutMapping("/assign/{employeeId}")
    public ResponseEntity<EmployeeResponse> assignEmployee(@PathVariable Long employeeId,
                                                           @RequestParam Long departmentId){

        return ResponseEntity.ok(employeeService.assignEmployeeToDepartment(employeeId, departmentId));

    }



    /// 6-delete employee
    @DeleteMapping
    public ResponseEntity<Void> deleteEmployee(@RequestParam Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
