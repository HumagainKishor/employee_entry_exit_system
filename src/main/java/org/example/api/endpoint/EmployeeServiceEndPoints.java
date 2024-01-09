package org.example.api.endpoint;

public record EmployeeServiceEndPoints(EmployeeRegistrationEndPoints employeeRegistrationEndPoints,
                                       EmployeeAttendanceEndPoints employeeAttendanceEndPoints,
                                       EmployeeDetailsEndPoints employeeDetailsEndPoints) {

}

