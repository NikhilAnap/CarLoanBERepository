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
public class SanctionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int sanctionId;
	private int tenure;
	private double rateOfInterest;
	private String agreementDate;
	private double customerTotalLoanRequired;
	private String bankName;
	private Long bankAccountNumber;
	private double sanctionAmount;
	private String amountPaidDate;
	@Lob
	private byte[] sanctionLetter;

}
