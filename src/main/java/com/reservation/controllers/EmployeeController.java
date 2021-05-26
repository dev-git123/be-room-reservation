package com.reservation.controllers;

import com.reservation.models.Employee;
import com.reservation.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin("*")
public class EmployeeController {

   private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> find() {
        return employeeService.getAll();
    }
}
