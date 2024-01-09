package org.example.domain.port;

import org.example.domain.model.EmployeeAttendanceDto;

import java.util.List;

public interface EmployeeAttendanceDao {
    boolean isPresent(String empId);

    boolean isTodayPresent(String empId);

    void saveEntry(String empId, String dates, String entrytime);
    void saveExit(String empId, String dates, String exittime);
    List<EmployeeAttendanceDto> getEmployeeAttendanceById(String empId);

    void updateEntryTime(String empId, String dates);
    EmployeeAttendanceDto getTodayAttend(String empId);

}

