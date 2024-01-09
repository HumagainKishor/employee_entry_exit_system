package org.example.adapter.dao.entity;

import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@Serdeable
public class Employee {
    @Id
    @GeneratedValue
    private String empId ;
    private String name;
    private String address;
    public Employee(){}

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
