package com.cjc.main.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmployeeLocalAddress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int employeeLocalAddressId;
	private String employeeState;
	private String employeeDistrict;
	private String employeeCity;
	private String employeeArea;
	private String employeeLandmark;
	private long employeePinCode;


}
