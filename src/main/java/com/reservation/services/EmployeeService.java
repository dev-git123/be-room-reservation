package com.reservation.services;

import com.reservation.models.Employee;
import com.reservation.repostiories.EmployeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;


@Service
public class EmployeeService {
    private Logger  logger =  LogManager.getLogger(EmployeeService.class);;
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostConstruct
    public void init() throws ParseException {
        Employee employee1 = new Employee();
        employee1.setFirstName("Thin");
        employee1.setLastName("Hlaing");
        employee1.setEmployeeNo("e-123");
        employee1.setDateOfBirth( new SimpleDateFormat("yyyy/MM/dd").parse("1989/09/09"));
        employee1.setDepartment("Engineering");
        create(employee1);

        Employee employee2 = new Employee();
        employee2.setFirstName("Suzi");
        employee2.setLastName("Han");
        employee2.setEmployeeNo("e-456");
        employee2.setDateOfBirth( new SimpleDateFormat("yyyy/MM/dd").parse("1989/09/09"));
        employee2.setDepartment("Design");
        create(employee2);

        logger.info("initializing employee on db ... ");

    }

    public Employee create(Employee employee){
       return employeeRepository.save(employee);
    }

    public List<Employee> getAll(){
        logger.info("Find all employee ... ");
        return employeeRepository.findAll();
    }

    public Employee findById(UUID id){
        logger.info("Find employee Id "+ id);
        return employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee Id "+ id +" is not found!"));
    }

    public Employee findByEmployeeNo(String empNo){
        logger.info("Find employeeNo "+ empNo);
        return employeeRepository.findByEmployeeNo(empNo).orElseThrow(() -> new EntityNotFoundException("Employee No "+ empNo +" is not found!"));
    }
}
