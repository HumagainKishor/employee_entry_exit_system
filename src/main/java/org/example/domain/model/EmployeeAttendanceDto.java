package org.example.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmployeeAttendanceDto {
    private String id;
    private String dates;
    private String entryTime;
    private String exitTime;
    private EmployeeDto employeeDto;

    public static EmployeeAttendanceDto from(String empid,String dates,String entryTime,String exitTime, EmployeeDto employeeDto){
        return EmployeeAttendanceDto.builder()
                .id(empid)
                .dates(dates)
                .entryTime(entryTime)
                .exitTime(exitTime)
                .employeeDto(employeeDto)
                .build();
    }
    public static EmployeeAttendanceDto from(String empid,String dates,String entryTime,String exitTime){
        return EmployeeAttendanceDto.builder()
                .id(empid)
                .dates(dates)
                .entryTime(entryTime)
                .exitTime(exitTime)
                .build();
    }

}

