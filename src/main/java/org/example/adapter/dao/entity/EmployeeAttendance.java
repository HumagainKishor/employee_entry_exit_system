package org.example.adapter.dao.entity;

import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@MappedEntity("employee_attendance")
public class EmployeeAttendance {
    @Id
    @GeneratedValue
    private String empId;
    private String checkInTime;
    private String checkOutTime;
    private String date;

    public EmployeeAttendance() {}

    public EmployeeAttendance(Row row) {
        this.empId = row.getString("empid");
        this.checkInTime = row.getString("entrytime");
        this.checkOutTime = row.getString("exittime");
        this.date = row.getString("dates");
    }

    public String getId() {
        return empId;
    }

    public void setId(String id) {
        this.empId = id;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
