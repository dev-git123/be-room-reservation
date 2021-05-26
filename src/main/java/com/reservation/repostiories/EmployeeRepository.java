package com.reservation.repostiories;

import com.reservation.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    public Optional<Employee> findByEmployeeNo(String employeeNo);
}
