package com.example.ems.service;

import com.example.ems.dto.EmployeeRequest;
import com.example.ems.dto.EmployeeResponse;
import com.example.ems.entity.Department;
import com.example.ems.entity.Employee;
import com.example.ems.exception.EmailAlreadyExists;
import com.example.ems.exception.EntityNotFound;
import com.example.ems.mapper.EmployeeMapper;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;



    /// o Add new employees with personal information (name, email, phone,hire date, salary)
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExists("Employee with email " + request.getEmail() + " already exists.");
        }
        Employee employee = EmployeeMapper.toEntity(request);

        //checking department
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFound("Department not found with ID: " + request.getDepartmentId()));
            employee.setDepartment(department);
        }

        //save Empployee
        employeeRepository.save(employee);
        return EmployeeMapper.toResponse(employee);
    }

    ///Get employee as response DTO.
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Employee not found with ID: " + id));
        return EmployeeMapper.toResponse(employee);
    }

    /// o Assign employees to departments
    public EmployeeResponse assignEmployeeToDepartment(Long employeeId, Long departmentId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFound("Employee not found with ID: " + employeeId));
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new EntityNotFound("Department not found with ID: " + departmentId));
        employee.setDepartment(department);
        employeeRepository.save(employee);
        return EmployeeMapper.toResponse(employee);
    }


    /// o View all employees or filter by department
    public List<EmployeeResponse> getAllEmployees(Long departmentId) {
        List<Employee> employees;

        if(departmentId == null){
            return employeeRepository.findAll()
                    .stream()
                    .map(EmployeeMapper::toResponse)
                    .toList();
        }
        employees = employeeRepository.findByDepartmentId(departmentId);
        return employees.stream()
                .map(EmployeeMapper::toResponse)
                .toList();

    }


    ///o Update employee information
    public EmployeeResponse updateEmployee(Long employeeId, EmployeeRequest request) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFound("Employee not found with ID: " + employeeId);
        }
        Employee employee = EmployeeMapper.toEntity(request);
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setHireDate(request.getHireDate());
        employee.setSalary(request.getSalary());

        employeeRepository.save(employee);
        return EmployeeMapper.toResponse(employee);
    }

    ///o Remove employees from the system
    public void deleteEmployee(){
        employeeRepository.deleteAll();
        System.out.println("All Employees has been deleted");
    }


}
