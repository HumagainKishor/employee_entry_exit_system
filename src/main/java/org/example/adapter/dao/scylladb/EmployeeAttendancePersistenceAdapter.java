package org.example.adapter.dao.scylladb;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import jakarta.inject.Singleton;
import org.example.domain.port.EmployeeAttendanceDao;
import org.example.domain.exceptions.ScyllaDbError;
import org.example.domain.model.EmployeeAttendanceDto;
import org.example.utils.DateAndTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
public class EmployeeAttendancePersistenceAdapter implements EmployeeAttendanceDao {
    private final CqlSession session;

    public EmployeeAttendancePersistenceAdapter(CqlSession session) {
        this.session = session;
    }

    @Override
    public boolean isPresent(String empId) {
        UUID empIdUuid = UUID.fromString(empId);

        try{
            var preparedStmt = session.prepare("SELECT * FROM my_keyspace.employee WHERE empid = ?");
            var boundStmt = preparedStmt.bind()
                    .setUuid("empid", empIdUuid);
            ResultSet resultSet = session.execute(boundStmt);
            Row row = resultSet.one();

            return row != null;
        }catch (Exception e){
            throw new ScyllaDbError();
        }
    }

    @Override
    public boolean isTodayPresent(String empId) {
        UUID empIdUuid = UUID.fromString(empId);
        try {
            var preparedStmt = session.prepare("SELECT * FROM my_keyspace.employee_time WHERE empid = ? AND dates =?");
            var boundStmt = preparedStmt.bind()
                    .setUuid("empid", empIdUuid)
                    .setString("dates", DateAndTime.getCurrentDateText());
            ResultSet resultSet = session.execute(boundStmt);
            Row row = resultSet.one();

            return row != null;
        }catch (Exception e){throw new ScyllaDbError();}
    }


    @Override
    public void saveEntry(String empId, String dates, String entrytime) {
        UUID empIdUuid = UUID.fromString(empId);
        try {
            var preparedStmt = session.prepare("INSERT INTO my_keyspace.employee_time (empid, dates, entrytime) VALUES (?, ?, ?)");
            var boundStmt = preparedStmt.bind()
                    .setUuid("empid", empIdUuid)
                    .setString("dates", dates)
                    .setString("entrytime", entrytime);
            session.execute(boundStmt);
        }catch (Exception e){throw new ScyllaDbError();}
    }

    @Override
    public void saveExit(String empId, String dates, String exittime) {
        UUID empIdUuid = UUID.fromString(empId);
        try {
            var preparedStmt = session.prepare("INSERT INTO my_keyspace.employee_time (empid, dates, exittime) VALUES (?,?,?)");
            var boundStmt = preparedStmt.bind()
                    .setUuid("empid", empIdUuid)
                    .setString("dates", dates)
                    .setString("exittime", exittime);
            session.execute(boundStmt);
        }catch (Exception e){throw new ScyllaDbError();}
    }

    @Override
    public List<EmployeeAttendanceDto> getEmployeeAttendanceById(String empId) {
        UUID empIdUuid = UUID.fromString(empId);
        try {
            List<EmployeeAttendanceDto> employeeAttendanceDtoList = new ArrayList<>();

            var preparedStmt = session.prepare("SELECT * FROM my_keyspace.employee_time WHERE empId = ?");
            var boundStmt = preparedStmt.bind()
                    .setUuid("empid", empIdUuid);
            ResultSet resultSet = session.execute(boundStmt);
            for (Row row : resultSet) {
                UUID empIds = row.getUuid("empid");
                String empID = empIds.toString();
                String date = row.getString("dates");
                String entryTime = row.getString("entrytime");
                String exitTime = row.getString("exittime");

                employeeAttendanceDtoList.add(EmployeeAttendanceDto.from(empID, date, entryTime, exitTime));

            }
            return employeeAttendanceDtoList;
        }catch (Exception e){throw new ScyllaDbError();}
    }

    @Override
    public void updateEntryTime(String empId, String dates) {

    }

    @Override
    public EmployeeAttendanceDto getTodayAttend(String empId) {
        UUID empIdUuid = UUID.fromString(empId);
        try {
            String currentDate = DateAndTime.getCurrentDateText();

            var preparedStmt = session.prepare("SELECT * FROM my_keyspace.employee_time WHERE empid = ? AND dates = ?");
            var boundStmt = preparedStmt.bind()
                    .setUuid("empid", empIdUuid)
                    .setString("dates", currentDate);

            ResultSet resultSet = session.execute(boundStmt);
            Row row = resultSet.one();

            if (row != null) {
                String entryTime = row.getString("entrytime");
                String exitTime = row.getString("exittime");
                return EmployeeAttendanceDto.from(empId, currentDate, entryTime, exitTime);
            } else {
                throw new IllegalArgumentException("No entry found for today.");
            }
        }catch (Exception e){throw new ScyllaDbError();}
    }

}


