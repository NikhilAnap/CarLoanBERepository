package com.cjc.main.service;

import java.io.ByteArrayInputStream;

import org.springframework.web.multipart.MultipartFile;

import com.cjc.main.model.Customer;
import com.cjc.main.model.SanctionDetails;

public interface CustomerService {

	public void saveCustomer(String customerProfile, String customerPaddr, String customerLaddr, String bankDetails,
			MultipartFile customerAadhar, MultipartFile customerPan, MultipartFile customerProfilePhoto,
			MultipartFile customerSignature, MultipartFile customerSalaryslip, MultipartFile customerDrivingLicense,
			MultipartFile customerBankStatement, MultipartFile customerCarQuotation, MultipartFile customerForm16,
			MultipartFile customerITR);

	public Iterable<Customer> getAllCustomer();

	public Customer getSingleCustomer(int customerId);

	public void deleteCustomer(int customerId);

	public void updateCustomer(int customerId, String customerProfile, String customerPaddr, String customerLaddr,
			String bankDetails, MultipartFile customerAadhar, MultipartFile customerPan,
			MultipartFile customerProfilePhoto, MultipartFile customerSignature, MultipartFile customerSalaryslip,
			MultipartFile customerDrivingLicense, MultipartFile customerBankStatement,
			MultipartFile customerCarQuotation, MultipartFile customerForm16, MultipartFile customerITR);

	public void updateCustomerStatus(Customer customer);

	public void updateCustomerSanctionDetails(int customerId, SanctionDetails sanctionDetails);

	public ByteArrayInputStream generatePdf(int customerId);

	public void sendSanctionLetter(int customerId);

}
