package org.example.api.server;

import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptor;
import io.grpc.ServerInterceptors;
import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import io.micronaut.core.annotation.NonNull;
import org.example.api.endpoint.EmployeeServiceEndPoints;
import org.example.api.interceptor.EmployeeServiceInterceptor;

public class EmployeeServiceGrpcServer implements BeanCreatedEventListener<ServerBuilder<?>> {
    private final EmployeeServiceInterceptor interceptor;
    private final EmployeeServiceEndPoints endPoints;

    public EmployeeServiceGrpcServer(EmployeeServiceInterceptor interceptor, EmployeeServiceEndPoints endPoints) {
        this.interceptor = interceptor;
        this.endPoints = endPoints;
    }


    @Override
    public ServerBuilder<?> onCreated(@NonNull BeanCreatedEvent<ServerBuilder<?>> event) {

        ServerBuilder<?> builder = event.getBean();

        builder.addService(ServerInterceptors.intercept(endPoints.employeeRegistrationEndPoints(), interceptor.getEmployeeRegistrationEHInterceptor()));
        builder.addService(ServerInterceptors.intercept(endPoints.employeeAttendanceEndPoints(), interceptor.getEmployeeAttendanceEHInterceptor()));
        builder.addService(ServerInterceptors.intercept(endPoints.employeeDetailsEndPoints(), interceptor.getEmployeeDetailsEHInterceptor()));

        return builder;

    }
}
