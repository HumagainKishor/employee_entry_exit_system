package org.example.service;

import jakarta.inject.Singleton;
import org.example.domain.port.EmployeeRegistrationDao;
import org.example.domain.model.EmployeeDto;

@Singleton
public class EmployeeRegistrationService implements RegistrationService{
    private final EmployeeRegistrationDao employeeRegistrationDao;

    public EmployeeRegistrationService(EmployeeRegistrationDao employeeRegistrationDao) {
        this.employeeRegistrationDao = employeeRegistrationDao;
    }

    @Override
    public EmployeeDto registerEmployee(String name, String address) {
        var employeeDto = EmployeeDto.from(name, address);
        return employeeRegistrationDao.saves(employeeDto);
    }
}


