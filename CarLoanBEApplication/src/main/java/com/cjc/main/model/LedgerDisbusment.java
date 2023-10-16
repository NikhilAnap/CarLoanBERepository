package com.cjc.main.model;

import java.util.Date;

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
public class LedgerDisbusment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ledgerId;
	private int paymentNumber;
	private String lastDateOfpayment;
	private double balance;
	private double emiPaid;
	private String status;
	private String paidDate;

}
