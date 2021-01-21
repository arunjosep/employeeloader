package com.employee.loader.model;

import com.employee.loader.exception.UnparsableDataException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private Integer age;

    @JsonIgnore
    @Column
    private String source;

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Employee() {
    }

    public Employee(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Employee(String nameAndAge, String source) throws UnparsableDataException {
        try {
            this.age = Integer.parseInt(nameAndAge.substring(nameAndAge.lastIndexOf(" ") + 1).trim());
        } catch (NumberFormatException ex) {
            throw new UnparsableDataException(nameAndAge);
        }

        this.name = nameAndAge.substring(0, nameAndAge.lastIndexOf(" ")).trim();
        this.source = source;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", age=" + age + " ]";
    }

    public void updateFrom(Employee employee) {
        this.name = employee.name;
        this.age = employee.age;
    }
}
