package org.example.domain.port;

import org.example.domain.model.EmployeeDto;

public interface EmployeeRegistrationDao {

    EmployeeDto saves(EmployeeDto employeeDto);


}
