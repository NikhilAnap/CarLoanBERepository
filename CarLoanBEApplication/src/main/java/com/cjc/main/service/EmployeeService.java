package com.cjc.main.service;

import org.springframework.web.multipart.MultipartFile;

import com.cjc.main.model.Employee;

public interface EmployeeService {

	public void saveEmployee(String employeeDetails, String employeeLocalAddress, String employeePermanentAddress,
			String employeeBankDetails, MultipartFile employeeProfileImg, MultipartFile employeeAadharCard,
			MultipartFile employeePanCard, MultipartFile employeeSingnature);

	public Iterable<Employee> getAllEmployee();

	public Employee getSingleEmployee(int employeeId);

	public void updateEmployee(int employeeId, String employeeDetails, String employeeLocalAddress,
			String employeePermanentAddress, String employeeBankDetails, MultipartFile employeeProfileImg,
			MultipartFile employeeAadharCard, MultipartFile employeePanCard, MultipartFile employeeSingnature);

	public void deleteEmployee(int employeeId);

}
