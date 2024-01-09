package org.example.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class EmployeeDto {
    private String id;
    private String name;
    private String address;

    public static EmployeeDto from(String name, String address) {
        return EmployeeDto.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .address(address)
                .build();
    }
}
