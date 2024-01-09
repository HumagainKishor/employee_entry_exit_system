package org.example.api.interceptor;

import io.grpc.*;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.example.employee_registration.EmployeeRegistrationRequest;

@Singleton
@Slf4j
public class EmployeeRegistrationEHInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> serverCall,
            Metadata metadata,
            ServerCallHandler<ReqT, RespT> serverCallHandler) {

        // Extract the method name
        String methodName = serverCall.getMethodDescriptor().getFullMethodName();
        log.info("Intercepting call to method: {}", methodName);


        return new
                ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(serverCallHandler.startCall(serverCall, metadata)) {
                    @Override
                    public void onMessage(ReqT message) {
                        if (message instanceof EmployeeRegistrationRequest) {
                            EmployeeRegistrationRequest registrationRequest = (EmployeeRegistrationRequest) message;

                            // Validate request fields
                            if (registrationRequest.getName().isEmpty()) {
                                throw Status.INVALID_ARGUMENT.withDescription("Employee name cannot be empty").asRuntimeException();
                            }
                            if (registrationRequest.getAddress().isEmpty()) {
                                throw Status.INVALID_ARGUMENT.withDescription("Employee address cannot be empty").asRuntimeException();
                            }

                            log.info("Received employee registration request: {}", message);
                        }
                        super.onMessage(message);
                    }
                };
    }
}

