package com.employee.loader.repository;

import com.employee.loader.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findBySourceIsNull();
    List<Employee> findBySourceIs(String source);
}
