package org.example.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class EmployeeDetailsDto {
    private EmployeeDto employeeDto;
    private List<EmployeeAttendanceDto> employeeAttendanceDto;
}
