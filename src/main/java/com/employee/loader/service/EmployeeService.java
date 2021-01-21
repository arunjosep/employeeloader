package com.employee.loader.service;

import com.employee.loader.exception.UnparsableDataException;
import com.employee.loader.model.Employee;
import com.employee.loader.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        List<Employee> employeeList = new ArrayList<Employee>();
        employeeRepository.findBySourceIsNull().forEach(emp -> employeeList.add(emp));
        return employeeList;
    }

    public Employee getEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id).filter(emp -> emp.getSource() == null);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            return null;
        }
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    public Employee addEmployee(Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }

    public Employee addEmployee(String nameAndAge, String source) throws UnparsableDataException {
        Employee employee = new Employee(nameAndAge, source);
        employeeRepository.save(employee);
        return employee;
    }

    public Employee updateEmployee(Employee employee, Long id) {
        Employee savedEmployee = employeeRepository.findById(id).filter(emp -> emp.getSource() == null).get();
        if (savedEmployee == null) {
            employeeRepository.save(employee);
            return employee;
        }

        savedEmployee.updateFrom(employee);
        employeeRepository.save(savedEmployee);
        return savedEmployee;
    }

    public void removeEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public long clearSource(String source) {
        AtomicLong count = new AtomicLong(0);
        employeeRepository.findBySourceIs(source).forEach(employee -> {
            employee.setSource(null);
            employeeRepository.save(employee);
            count.getAndIncrement();
        });
        return count.get();
    }

    public void deleteBySource(String source) {
        employeeRepository.findBySourceIs(source).forEach(employee -> {
            employeeRepository.delete(employee);
        });
    }

}
