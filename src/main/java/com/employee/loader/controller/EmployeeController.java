package com.employee.loader.controller;

import com.employee.loader.model.Employee;
import com.employee.loader.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/")
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @RequestMapping("/{id}")
    public Employee getEmployee(@PathVariable("id") Long id) {
        return employeeService.getEmployee(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public Employee addEmp(@RequestBody Employee emp) {
        return employeeService.addEmployee(emp);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Employee updateEmp(@RequestBody Employee emp, @PathVariable("id") Long id) {
        return employeeService.updateEmployee(emp, id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void removeEmployee(@PathVariable("id") Long id) {
        employeeService.removeEmployee(id);
    }

}
