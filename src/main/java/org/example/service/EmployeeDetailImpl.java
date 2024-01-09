package org.example.service;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.domain.port.EmployeeAttendanceDao;
import org.example.domain.port.EmployeeDetailsDao;
import org.example.domain.model.EmployeeAttendanceDto;
import org.example.domain.model.EmployeeDetailsDto;
import org.example.domain.model.EmployeeDto;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class EmployeeDetailImpl implements EmployeeDetails {
    @Inject
    EmployeeAttendanceDao employeeAttendanceDao;
    @Inject
    EmployeeDetailsDao employeeDetailsDao;


    @Override
    public List<EmployeeDetailsDto> getEmployeeDetailsById(String empId) {
        List<EmployeeDetailsDto> employeeDetailsList = new ArrayList<>();
        boolean isEmployeePresent = employeeAttendanceDao.isPresent(empId);

        if (isEmployeePresent) {
            // Retrieve employee details from EmployeeDetailsDao
            EmployeeDto employeeDto = employeeDetailsDao.findEmployeeById(empId);

            if (employeeDto != null) {
                // Retrieve attendance details from EmployeeAttendanceDao
                List<EmployeeAttendanceDto> attendanceList = employeeAttendanceDao.getEmployeeAttendanceById(empId);

                EmployeeDetailsDto employeeDetailsDto = EmployeeDetailsDto.builder()
                        .employeeDto(employeeDto)
                        .employeeAttendanceDto(attendanceList)
                        .build();

                employeeDetailsList.add(employeeDetailsDto);
            }
        }
        return employeeDetailsList;
    }
    @Override
    public List<EmployeeDetailsDto> getAllEmployees() {
        List<EmployeeDetailsDto> employeeDetailsList = new ArrayList<>();

        List<EmployeeDto> allEmployeeDtos = employeeDetailsDao.getAllEmployees();

        for (EmployeeDto employeeDto : allEmployeeDtos) {
            // Retrieve attendance details for each employee
            List<EmployeeAttendanceDto> attendanceList = employeeAttendanceDao.getEmployeeAttendanceById(employeeDto.getId());

            EmployeeDetailsDto employeeDetailsDto = EmployeeDetailsDto.builder()
                    .employeeDto(employeeDto)
                    .employeeAttendanceDto(attendanceList)
                    .build();

            employeeDetailsList.add(employeeDetailsDto);
        }

        return employeeDetailsList;
    }
}





