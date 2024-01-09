package org.example.api.interceptor;

import jakarta.inject.Singleton;
import lombok.Getter;

@Singleton
@Getter
public class EmployeeServiceInterceptor {
    private final EmployeeRegistrationEHInterceptor employeeRegistrationEHInterceptor;
    private final EmployeeDetailsEHInterceptor employeeDetailsEHInterceptor;
    private final EmployeeAttendanceEHInterceptor employeeAttendanceEHInterceptor;



    public EmployeeServiceInterceptor(EmployeeRegistrationEHInterceptor employeeRegistrationEHInterceptor,
                                      EmployeeDetailsEHInterceptor employeeDetailsEHInterceptor,
                                      EmployeeAttendanceEHInterceptor employeeAttendanceEHInterceptor) {
        this.employeeRegistrationEHInterceptor = employeeRegistrationEHInterceptor;
        this.employeeDetailsEHInterceptor = employeeDetailsEHInterceptor;
        this.employeeAttendanceEHInterceptor = employeeAttendanceEHInterceptor;
    }
}
