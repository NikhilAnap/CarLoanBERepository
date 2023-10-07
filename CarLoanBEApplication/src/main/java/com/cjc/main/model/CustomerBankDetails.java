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
public class CustomerBankDetails {

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bankAccountNumber;
	private String bankName;
	private String branchName;
	private String ifscNumber;
	private Long cardNumber;

}
