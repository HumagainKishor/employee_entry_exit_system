package org.example.api.endpoint;

import io.grpc.stub.StreamObserver;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.employee_detail.*;
import org.example.domain.model.EmployeeAttendanceDto;
import org.example.domain.model.EmployeeDetailsDto;
import org.example.domain.model.EmployeeDto;
import org.example.service.EmployeeDetails;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class EmployeeDetailsEndPoints extends EmployeeDetailGrpc.EmployeeDetailImplBase {
    @Inject
    EmployeeDetails employeeDetails;


    @Override
    public void getEmployeeById(Request request, StreamObserver<EmployeeList> responseObserver) {
        String empId = request.getEmpId();
        var empModel = employeeDetails.getEmployeeDetailsById(empId);

        // Construct the list of Reply instances
        List<Reply> replyList = new ArrayList<>();
        for (EmployeeDetailsDto employeeDetailsDto : empModel) {
            EmployeeDto employeeDto = employeeDetailsDto.getEmployeeDto();
            List<EmployeeAttendanceDto> attendanceList = employeeDetailsDto.getEmployeeAttendanceDto();

            // Iterate through each attendance entry
            for (EmployeeAttendanceDto attendanceDto : attendanceList) {
                Reply employee = Reply.newBuilder()
                        .setEmpId(employeeDto.getId())
                        .setName(employeeDto.getName())
                        .setAddress(employeeDto.getAddress())
                        .setCheckInTime(attendanceDto.getEntryTime() == null ? "Not Available" : attendanceDto.getEntryTime())
                        //.setCheckOutTime(attendanceDto.getExitTime())
                        .setCheckOutTime(attendanceDto.getExitTime() == null ? "Not Available" : attendanceDto.getExitTime())
                        .setDate(attendanceDto.getDates() == null ? "Not Available" : attendanceDto.getDates())
                        .build();
                replyList.add(employee);
            }
        }
        // Build the EmployeeList message
        EmployeeList employeeList = EmployeeList.newBuilder()
                .addAllEmployees(replyList)
                .build();

        // Send the EmployeeList as a response
        responseObserver.onNext(employeeList);
        responseObserver.onCompleted();

    }

    @Override
    public void getAllEmployee(Empty request, StreamObserver<EmployeeList> responseObserver) {
        // Retrieve all employees and their details
        List<EmployeeDetailsDto> allEmployees = employeeDetails.getAllEmployees();

        // Construct the list of Reply instances
        List<Reply> replyList = new ArrayList<>();

        for (EmployeeDetailsDto employeeDetailsDto : allEmployees) {
            EmployeeDto employeeDto = employeeDetailsDto.getEmployeeDto();
            List<EmployeeAttendanceDto> attendanceList = employeeDetailsDto.getEmployeeAttendanceDto();

            // Iterate through each attendance entry
            for (EmployeeAttendanceDto attendanceDto : attendanceList) {
                Reply employee = Reply.newBuilder()
                        .setEmpId(employeeDto.getId())
                        .setName(employeeDto.getName())
                        .setAddress(employeeDto.getAddress())
                        .setCheckInTime(attendanceDto.getEntryTime() == null ? "Not Available" : attendanceDto.getEntryTime())
                        .setCheckOutTime(attendanceDto.getExitTime() == null ? "Not Available" : attendanceDto.getExitTime())
                        .setDate(attendanceDto.getDates() == null ? "Not Available" : attendanceDto.getDates())
                        .build();
                replyList.add(employee);
            }
        }

        // Build the EmployeeList message
        EmployeeList employeeList = EmployeeList.newBuilder()
                .addAllEmployees(replyList)
                .build();

        // Send the EmployeeList as a response
        responseObserver.onNext(employeeList);
        responseObserver.onCompleted();
    }

}