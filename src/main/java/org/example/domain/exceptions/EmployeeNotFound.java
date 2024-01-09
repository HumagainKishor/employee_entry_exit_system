package org.example.domain.exceptions;

public class EmployeeNotFound extends RuntimeException{

    public EmployeeNotFound(String empid){
        super("Employee not found for given empid: " + empid);
    }
    EmployeeNotFound(String msg ,Throwable throwable){

    }
}