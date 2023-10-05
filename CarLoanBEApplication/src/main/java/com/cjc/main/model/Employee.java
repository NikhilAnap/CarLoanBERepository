package com.cjc.main.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int employeeId;
	private String employeeName;
	private String employeeDOB;
	private String employeeEmail;
	private Long employeeMobileNo;
	private String employeeGender;
	private String employeeRole;
	@Column(unique = true)
	private String username;
	@Column(unique = true)
	private String password;
	@OneToOne(cascade = CascadeType.ALL)
	private EmployeeLocalAddress employeeLocalAddress;
	@OneToOne(cascade = CascadeType.ALL)
	private EmployeePermanentAddress employeePermanentaddress;
	@OneToOne(cascade = CascadeType.ALL)
	private EmployeeBankDetails employeeBankDetails;
	@OneToOne(cascade = CascadeType.ALL)
	private EmployeeDocument employeeDocuments;

}
