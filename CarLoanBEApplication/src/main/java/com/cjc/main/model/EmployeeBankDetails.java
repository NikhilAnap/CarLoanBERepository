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
public class EmployeeBankDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int employeeBankDetailsId;
	private long employeeAccNo;
	private String employeeBankName;
	private String employeeBranchName;
	private long employeeIFSCNo;
	private long employeeBankCode;
	private long employeeCIFNo;

}
