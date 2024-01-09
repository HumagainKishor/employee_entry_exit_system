package org.example.adapter.dao.scylladb;

import com.datastax.oss.driver.api.core.CqlSession;
import jakarta.inject.Singleton;
import org.example.domain.port.EmployeeRegistrationDao;
import org.example.domain.exceptions.ScyllaDbError;
import org.example.domain.model.EmployeeDto;

import java.util.UUID;
@Singleton
public class EmployeeRegistrationDaoImpl implements EmployeeRegistrationDao {
    private final CqlSession session;

    public EmployeeRegistrationDaoImpl(CqlSession session) {
        this.session = session;
    }


    @Override
    public EmployeeDto saves(EmployeeDto employee) {
        UUID empIdUuid = UUID.fromString(employee.getId());
        try {
            var preparedStmt = session.prepare("INSERT INTO my_keyspace.employee (empid, address, name) VALUES (?, ?, ?)");
            var boundStmt = preparedStmt.bind()
                    .setUuid("empid", empIdUuid)
                    .setString("address", employee.getAddress())
                    .setString("name", employee.getName());
            session.execute(boundStmt);
            return employee;
        }catch (Exception e){ throw new ScyllaDbError();}
    }
}
