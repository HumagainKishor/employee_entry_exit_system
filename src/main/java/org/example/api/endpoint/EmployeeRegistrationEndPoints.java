package org.example.api.endpoint;

import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;
import org.example.employee_registration.EmployeeRegistrationRequest;
import org.example.employee_registration.EmployeeRegistrationServiceGrpc;
import org.example.employee_registration.RegisterResponse;
import org.example.domain.exceptions.NullRequestException;
import org.example.domain.model.EmployeeDto;
import org.example.service.RegistrationService;
@Singleton
public class EmployeeRegistrationEndPoints extends EmployeeRegistrationServiceGrpc.EmployeeRegistrationServiceImplBase {
    private final RegistrationService registrationService;


    public EmployeeRegistrationEndPoints(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public void employeeRegister(EmployeeRegistrationRequest request, StreamObserver<RegisterResponse> responseObserver) {

        String name = request.getName();
        String address = request.getAddress();
        if (name.isEmpty() || address.isEmpty()){
            throw new NullRequestException("Name or Address is Empty or both are Empty.");
        }

        EmployeeDto employeeDto = registrationService.registerEmployee(name,address);
        //build the response
        RegisterResponse rpcResponse = RegisterResponse.newBuilder()
                .setEmpId(employeeDto.getId())
                .setName(employeeDto.getName())
                .setAddress(employeeDto.getAddress())
                .build();
        responseObserver.onNext(rpcResponse);
        responseObserver.onCompleted();
    }
}
