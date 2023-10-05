package com.cjc.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cjc.main.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
