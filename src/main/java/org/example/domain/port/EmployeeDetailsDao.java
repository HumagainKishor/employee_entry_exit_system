package org.example.domain.port;

import org.example.domain.model.EmployeeAttendanceDto;
import org.example.domain.model.EmployeeDto;

import java.util.List;

public interface EmployeeDetailsDao {
    List<EmployeeAttendanceDto> findEmployeeAttendById(String empId);

    EmployeeDto findEmployeeById(String empId);
    List<EmployeeDto> getAllEmployees();
}
