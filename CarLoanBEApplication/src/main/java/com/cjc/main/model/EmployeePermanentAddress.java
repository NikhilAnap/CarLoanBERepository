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
public class EmployeePermanentAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int employeePermanentaddressId;
	private String employeePState;
	private String employeePDistrict;
	private String employeePCity;
	private String employeePArea;
	private String employeePLandmark;
	private long employeePinCode;

}
