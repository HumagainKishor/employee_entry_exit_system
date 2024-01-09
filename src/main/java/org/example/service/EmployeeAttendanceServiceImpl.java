package org.example.service;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.domain.port.EmployeeAttendanceDao;
import org.example.domain.port.EmployeeDetailsDao;
import org.example.domain.exceptions.EmployeeNotFound;
import org.example.domain.model.EmployeeAttendanceDto;
import org.example.utils.DateAndTime;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class EmployeeAttendanceServiceImpl implements EmployeeAttendanceService {
    @Inject
    EmployeeAttendanceDao employeeAttendanceDao;
    @Inject
    EmployeeDetailsDao employeeDetailsDao;


    @Override
    public EmployeeAttendanceDto entryTime(String empId) {
        boolean isEmployee = employeeAttendanceDao.isPresent(empId); //return true if employee is registered.
        String entryTime = DateAndTime.getCurrentTimeText();
        String currentDate = DateAndTime.getCurrentDateText();

        if(isEmployee){
            boolean isTodayAttendance = employeeAttendanceDao.isTodayPresent(empId);//return true if employee's today's attendance is present
            if(!isTodayAttendance){
                employeeAttendanceDao.saveEntry(empId, currentDate, entryTime);
                return EmployeeAttendanceDto.from(empId,currentDate,entryTime,null);

            }else {
                List<EmployeeAttendanceDto> employeeAttendanceDtoList = employeeAttendanceDao.getEmployeeAttendanceById(empId);

                // Filter entries for the current date
                List<EmployeeAttendanceDto> todayEntries = employeeAttendanceDtoList.stream()
                        .filter(entry -> entry.getDates().equals(currentDate))
                        .collect(Collectors.toList());

                if (!todayEntries.isEmpty()) {
                    // If there are entries for today then return the entry time
                    EmployeeAttendanceDto todayEntry = todayEntries.get(0);
                    return EmployeeAttendanceDto.from(empId, currentDate, todayEntry.getEntryTime(), todayEntry.getExitTime());
                } else {
                    throw new IllegalArgumentException("Employee not registered for today: ");
                }
            }
        }else {throw new EmployeeNotFound(empId);}
    }

    @Override
    public EmployeeAttendanceDto exitTime(String empId) {
        boolean isEmployee = employeeAttendanceDao.isPresent(empId);
        String exitTime = DateAndTime.getCurrentTimeText();
        String currentDate = DateAndTime.getCurrentDateText();

        if(isEmployee){
            boolean isTodayAttend = employeeAttendanceDao.isTodayPresent(empId);
            if(isTodayAttend){
                employeeAttendanceDao.saveExit(empId,currentDate,exitTime);
            }
        }else {throw new EmployeeNotFound(empId);}
        return null;
    }
}
