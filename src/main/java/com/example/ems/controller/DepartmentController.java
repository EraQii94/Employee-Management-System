package com.example.ems.controller;


import com.example.ems.dto.DepartmentRequest;
import com.example.ems.dto.DepartmentResponse;
import com.example.ems.service.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@Tag(name = "Department Management", description = "Operations for managing departments")
public class DepartmentController {


    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /// ///////////////////////////////////////////////////////////////////////
    ///
    ///
    /// 1-post mapping
    @Operation(summary = "Create department", description = "Create a new department")
    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(@Valid @RequestBody DepartmentRequest departmentRequest) {
        DepartmentResponse createdDepartment = departmentService.createDepartment(departmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    ///2-get mapping
    @Operation(summary = "List departments", description = "Get all departments")
    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    ///3-get mapping by id
    @Operation(summary = "Get department", description = "Retrieve a department by id")
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    ///4-put mapping
    @Operation(summary = "Update department", description = "Update an existing department")
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable Long id,
                                                               @Valid @RequestBody DepartmentRequest request) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, request));
    }

    ///5-delete all and by id
    @Operation(summary = "Delete department", description = "Delete a department by id")
    @DeleteMapping("/{id}") ///can remove all the department if we just ignored the path variable
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }





}
