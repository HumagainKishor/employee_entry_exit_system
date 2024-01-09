package org.example.adapter.dao.scylladb;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import jakarta.inject.Singleton;
import org.example.domain.port.EmployeeDetailsDao;
import org.example.domain.exceptions.EmployeeNotFound;
import org.example.domain.exceptions.ScyllaDbError;
import org.example.domain.model.EmployeeAttendanceDto;
import org.example.domain.model.EmployeeDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
public class EmployeeDetailsDaoImpl implements EmployeeDetailsDao {
    private final CqlSession session;

    public EmployeeDetailsDaoImpl(CqlSession session) {
        this.session = session;
    }

    @Override
    public List<EmployeeAttendanceDto> findEmployeeAttendById(String empId) {
        UUID empIdUuid = UUID.fromString(empId);
        try {
            var preparedStmt = session.prepare("SELECT * FROM my_keyspace.employee_time WHERE empid = ?");
            var boundStmt = preparedStmt.bind()
                    //.setString("empid", empId)
                    .setUuid("empid", empIdUuid);
            var resultSet = session.execute(boundStmt);
            List<EmployeeAttendanceDto> employeeList = new ArrayList<>();
            for (Row row : resultSet) {
                //String empid = row.getString("empid");
                UUID empid = row.getUuid("empid");
                String empids = empid.toString();
                String dates = row.getString("dates");
                String entryTime = row.getString("entrytime");
                String exitTime = row.getString("exittime");

                employeeList.add(EmployeeAttendanceDto.from(empids, dates, entryTime, exitTime));

            }
            return employeeList;
        }catch (Exception e){throw new ScyllaDbError();
        }
    }



    @Override
    public EmployeeDto findEmployeeById(String empId) {
        UUID empIdUuid = UUID.fromString(empId);
        try {
            var preparedStmt = session.prepare("SELECT * FROM my_keyspace.employee WHERE empid = ?");
            var boundStmt = preparedStmt.bind()
                    .setUuid("empid", empIdUuid);
            var resultSet = session.execute(boundStmt);
            Row row = resultSet.one();
            if (row != null) {
                return EmployeeDto.from(row.getString("name"), row.getString("address"));
            } else {
                throw new EmployeeNotFound("Employee not found");
            }
        } catch (Exception e) {
            throw new ScyllaDbError();}
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> employeeList = new ArrayList<>();
        try {
            var preparedStmt = session.prepare("SELECT * FROM my_keyspace.employee");
            var boundStmt = preparedStmt.bind();
            var resultSet = session.execute(boundStmt);

            for (Row row : resultSet) {
                UUID empId = row.getUuid("empid");
                String empids = empId.toString();
                String name = row.getString("name");
                String address = row.getString("address");

                employeeList.add(EmployeeDto.builder()
                        .id(empId.toString())
                        .name(name)
                        .address(address)
                        .build());
            }

            return employeeList;
        }catch (Exception e){throw new ScyllaDbError();}
    }

}

