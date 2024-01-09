package org.example.service;

import org.example.domain.model.EmployeeAttendanceDto;

public interface EmployeeAttendanceService {
    EmployeeAttendanceDto entryTime(String empId);
    EmployeeAttendanceDto exitTime(String empId);
}

