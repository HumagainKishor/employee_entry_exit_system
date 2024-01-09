package org.example.api.endpoint;

import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;
import org.example.employee_attendance.EmployeeAttendanceGrpc;
import org.example.employee_attendance.EmployeeRequest;
import org.example.employee_attendance.ResponseCheckIn;
import org.example.employee_attendance.ResponseCheckOut;
import org.example.domain.model.EmployeeAttendanceDto;
import org.example.service.EmployeeAttendanceService;
import org.example.utils.DateAndTime;

@Singleton
public class EmployeeAttendanceEndPoints extends EmployeeAttendanceGrpc.EmployeeAttendanceImplBase {
    private final EmployeeAttendanceService employeeService;

    public EmployeeAttendanceEndPoints(EmployeeAttendanceService employeeService) {
        this.employeeService = employeeService;
    }


    @Override
    public void checkIn(EmployeeRequest request, StreamObserver<ResponseCheckIn> responseObserver) {
        String empId = request.getEmpId();
        EmployeeAttendanceDto employeeAttendanceDto = employeeService.entryTime(empId);
        ResponseCheckIn rpcResponse = ResponseCheckIn.newBuilder()
                .setEmpId(employeeAttendanceDto.getId())
                .setCheckIn(employeeAttendanceDto.getEntryTime())
                .build();
        responseObserver.onNext(rpcResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void checkOut(EmployeeRequest request, StreamObserver<ResponseCheckOut> responseObserver) {
        String empId = request.getEmpId();
        employeeService.exitTime(empId);
        ResponseCheckOut rpcResponse = ResponseCheckOut.newBuilder()
                .setEmpId(empId)
                .setCheckOut(DateAndTime.getCurrentTimeText())
                .build();
        responseObserver.onNext(rpcResponse);
        responseObserver.onCompleted();
    }
}
