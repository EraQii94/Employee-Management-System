package com.example.ems.service;

import com.example.ems.dto.DepartmentRequest;
import com.example.ems.dto.DepartmentResponse;
import com.example.ems.entity.Department;
import com.example.ems.exception.EntityNotFound;
import com.example.ems.exception.ThereAreEmployeeAssigned;
import com.example.ems.mapper.DepartmentMapper;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    ///creating new Department
    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest) {
        Department department = new Department();
        department.setName(departmentRequest.getName());
        department.setLocation(departmentRequest.getLocation());
        department.setBudget(departmentRequest.getBudget());

        Department savedDepartment = departmentRepository.save(department);
        return DepartmentMapper.toResponse(savedDepartment);
    }

    ///o View all departments in the system
    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(DepartmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    /// o View departments in the system using id
    public DepartmentResponse getDepartmentById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            return DepartmentMapper.toResponse(department.get());
        } else {
            throw new EntityNotFound("Department not found with ID: " + id);
        }
    }

    ///o Update department information
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest departmentRequest) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Department not found"));

        department.setName(departmentRequest.getName());
        department.setLocation(departmentRequest.getLocation());
        department.setBudget(departmentRequest.getBudget());

        Department updatedDepartment = departmentRepository.save(department);
        return DepartmentMapper.toResponse(updatedDepartment);
    }


    ///o Delete departments (only if no employees are assigned)
    public void deleteDepartment(Long id) {
        //find department
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Department not found"));

        //check if employees are assigned to the department
        if (employeeRepository.existsByDepartmentId(id)) {
            throw new ThereAreEmployeeAssigned("Cannot delete department with assigned employees");
        }

        departmentRepository.delete(department);
        System.out.println("Department with ID " + id + " deleted successfully.");
    }

}
