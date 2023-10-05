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
public class CustomerDocuments {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int customerDocumentId;
	@Lob
	private byte[] customerAadhar;
	@Lob
	private byte[] customerPan;
	@Lob
	private byte[] customerProfilePhoto;
	@Lob
	private byte[] customerSignature;
	@Lob
	private byte[] customerSalaryslip;
	@Lob
	private byte[] customerDrivingLicense;
	@Lob
	private byte[] customerBankStatement;
	@Lob
	private byte[] customerCarQuotation;
	@Lob
	private byte[] customerForm16;
	@Lob
	private byte[] customerITR;

}
