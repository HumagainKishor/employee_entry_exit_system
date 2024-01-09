//package org.example.adapter.dao.postgres;
//
//import jakarta.inject.Singleton;
//import org.example.adapter.dao.postgres.Repository.EmployeeRepository;
//import org.example.domain.port.EmployeeRegistrationDao;
//import org.example.domain.model.EmployeeDto;
//
//
//@Singleton
//public class EmployeeRegistrationDaoImplementation implements EmployeeRegistrationDao {
//    private final EmployeeRepository employeeRepository;
//
//    public EmployeeRegistrationDaoImplementation(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }
//
//    @Override
//    public EmployeeDto saves(EmployeeDto employeeDto) {
//        return null;
////        return employeeRepository.save(new Employee());
//    }
//}
