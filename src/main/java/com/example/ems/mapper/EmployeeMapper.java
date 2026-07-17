package com.example.ems.mapper;

import com.example.ems.dto.EmployeeRequest;
import com.example.ems.dto.EmployeeResponse;
import com.example.ems.entity.Employee;
import org.springframework.stereotype.Component;


@Component
public class EmployeeMapper {


    public static Employee toEntity(EmployeeRequest request) {
        if (request == null) {
            return null;
        }

        Employee employee = new Employee();

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setSalary(request.getSalary());
        employee.setHireDate(request.getHireDate());


        return employee;
    }



    public static EmployeeResponse toResponse(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeResponse employeeResponse = new EmployeeResponse();

        employeeResponse.setName(employee.getName());
        employeeResponse.setEmail(employee.getEmail());
        employeeResponse.setPhoneNumber(employee.getPhoneNumber());
        employeeResponse.setSalary(employee.getSalary());
        employeeResponse.setHireDate(employee.getHireDate());

        return employeeResponse;
    }

}
