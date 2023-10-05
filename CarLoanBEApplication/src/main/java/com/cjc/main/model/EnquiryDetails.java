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
public class EnquiryDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int enquiryId;
	private String customerName;
	private String customerEmail;
	private long customerAadharno;
	@Column(unique = true)
	private String customerPanno;
	private long customerMobileno;
	@OneToOne(cascade = CascadeType.ALL)
	private CustomerCibilDetails customerCibilDetails;
	private String applicationStatus;

}
