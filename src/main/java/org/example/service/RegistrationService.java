package org.example.service;

import org.example.domain.model.EmployeeDto;

public interface RegistrationService {
    EmployeeDto registerEmployee(String name, String address);

}
