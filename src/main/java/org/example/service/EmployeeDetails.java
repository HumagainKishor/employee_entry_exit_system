package org.example.service;

import org.example.domain.model.EmployeeDetailsDto;

import java.util.List;

public interface EmployeeDetails {
    List<EmployeeDetailsDto> getEmployeeDetailsById(String empId);
    List<EmployeeDetailsDto> getAllEmployees();

}
