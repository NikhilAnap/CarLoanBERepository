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
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int customerId;
	private String customerName;
	private String customerEmail;
	private long customerAadharno;
	private String customerPanno;
	private long customerMobileno;
	@Column(unique = true)
	private String customerUsername;
	@Column(unique = true)
	private String customerPassword;
	@Column(unique = true)
	private int applicationNo;
	@OneToOne(cascade = CascadeType.ALL)
	private CustomerLocalAddress customerLocalAddress;
	@OneToOne(cascade = CascadeType.ALL)
	private CustomerPermanentAddress customerPermanentAddress;
	@OneToOne(cascade = CascadeType.ALL)
	private CustomerBankDetails customerBankDetails;
	@OneToOne(cascade = CascadeType.ALL)
	private CustomerDocuments customerDocuments;

}
