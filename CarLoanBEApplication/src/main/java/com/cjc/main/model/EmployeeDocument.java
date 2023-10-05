package com.cjc.main.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmployeeDocument {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int employeeDocumentId;
	@Lob
	private byte[] employeeProfileImg;
	@Lob
	private byte[] employeeAadharCard;
	@Lob
	private byte[] employeePanCard;
	@Lob
	private byte[] employeeSingnature;

}
