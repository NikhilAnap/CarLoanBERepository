package com.cjc.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cjc.main.model.Employee;
import com.cjc.main.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@PostMapping("/save_employee")
	public ResponseEntity<String> saveEmployee(
			@RequestPart(name = "details", required = true) String employeeDetails,
			@RequestPart(name = "localAddr", required = true) String employeeLocalAddress,
			@RequestPart(name = "permanentAddr", required = true) String employeePermanentAddress,
			@RequestPart(name = "bankDetails", required = true) String employeeBankDetails,
			@RequestPart(name = "profileImg", required = true) MultipartFile employeeProfileImg,
			@RequestPart(name = "aadhar", required = true) MultipartFile employeeAadharCard,
			@RequestPart(name = "pan", required = true) MultipartFile employeePanCard,
			@RequestPart(name = "signature", required = true) MultipartFile employeeSingnature) {

		employeeService.saveEmployee(employeeDetails, employeeLocalAddress,
				employeePermanentAddress, employeeBankDetails, employeeProfileImg, employeeAadharCard, employeePanCard,
				employeeSingnature);

		return new ResponseEntity<String>("Saved Employee Successfully!!!", HttpStatus.OK);

	}

	@GetMapping("/get_all_employee")
	public ResponseEntity<Iterable<Employee>> getAllEmployee() {

		Iterable<Employee> employees = employeeService.getAllEmployee();

		return new ResponseEntity<Iterable<Employee>>(employees, HttpStatus.OK);

	}

	@GetMapping("/get_single_employee/{employeeId}")
	public ResponseEntity<Employee> getSingleEmployee(@PathVariable int employeeId) {

		Employee employee = employeeService.getSingleEmployee(employeeId);

		return new ResponseEntity<Employee>(employee, HttpStatus.OK);

	}

	@PutMapping("/update_employee/{employeeId}")
	public ResponseEntity<String> updateEmployee(@PathVariable int employeeId,
			@RequestPart(name = "details", required = true) String employeeDetails,
			@RequestPart(name = "localAddr", required = true) String employeeLocalAddress,
			@RequestPart(name = "permanentAddr", required = true) String employeePermanentAddress,
			@RequestPart(name = "bankDetails", required = true) String employeeBankDetails,
			@RequestPart(name = "profileImg", required = true) MultipartFile employeeProfileImg,
			@RequestPart(name = "aadhar", required = true) MultipartFile employeeAadharCard,
			@RequestPart(name = "pan", required = true) MultipartFile employeePanCard,
			@RequestPart(name = "signature", required = true) MultipartFile employeeSingnature) {

		employeeService.updateEmployee(employeeId, employeeDetails, employeeLocalAddress,
				employeePermanentAddress, employeeBankDetails, employeeProfileImg, employeeAadharCard, employeePanCard,
				employeeSingnature);

		return new ResponseEntity<String>("Employee Updated Successfully!!!", HttpStatus.OK);

	}

	@DeleteMapping("/delete_employee/{employeeId}")
	public ResponseEntity<String> deleteEmployee(@PathVariable int employeeId) {

		employeeService.deleteEmployee(employeeId);

		return new ResponseEntity<String>("Employee Deleted Successfully!!!", HttpStatus.OK);

	}

}
