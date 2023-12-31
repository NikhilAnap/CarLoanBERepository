package com.cjc.main.controller;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cjc.main.model.Customer;
import com.cjc.main.model.SanctionDetails;
import com.cjc.main.service.CustomerService;

@CrossOrigin("*")
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@PostMapping("/save_customer")
	public ResponseEntity<String> saveCustomer(
			@RequestPart(name = "customerprofile", required = true) String customerProfile,
			@RequestPart(name = "permanentadd", required = true) String customerPaddr,
			@RequestPart(name = "localadd", required = true) String customerLaddr,
			@RequestPart(name = "bankdetails", required = true) String bankDetails,
			@RequestPart(name = "aadhar", required = true) MultipartFile customerAadhar,
			@RequestPart(name = "pan", required = false) MultipartFile customerPan,
			@RequestPart(name = "profilePhoto", required = false) MultipartFile customerProfilePhoto,
			@RequestPart(name = "signature", required = false) MultipartFile customerSignature,
			@RequestPart(name = "salarySlip", required = false) MultipartFile customerSalaryslip,
			@RequestPart(name = "drivingLicense", required = false) MultipartFile customerDrivingLicense,
			@RequestPart(name = "bankStatement", required = false) MultipartFile customerBankStatement,
			@RequestPart(name = "carQuotation", required = false) MultipartFile customerCarQuotation,
			@RequestPart(name = "form16", required = false) MultipartFile customerForm16,
			@RequestPart(name = "itr", required = false) MultipartFile customerITR) {

		customerService.saveCustomer(customerProfile, customerPaddr, customerLaddr, bankDetails, customerAadhar,
				customerPan, customerProfilePhoto, customerSignature, customerSalaryslip, customerDrivingLicense,
				customerBankStatement, customerCarQuotation, customerForm16, customerITR);

		return new ResponseEntity<String>("Customer Saved Successfully!!!", HttpStatus.OK);

	}

	@GetMapping("/getAll_customer")
	public ResponseEntity<Iterable<Customer>> getAllCustomer() {

		Iterable<Customer> customers = customerService.getAllCustomer();

		return new ResponseEntity<Iterable<Customer>>(customers, HttpStatus.OK);

	}

	@GetMapping("/getSingle_customer/{customerId}")
	public ResponseEntity<Customer> getSingleCustomer(@PathVariable int customerId) {

		Customer customer = customerService.getSingleCustomer(customerId);

		return new ResponseEntity<Customer>(customer, HttpStatus.OK);

	}

	@PutMapping("/update_customer/{customerId}")
	public ResponseEntity<String> updateCustomer(@PathVariable int customerId,
			@RequestPart(name = "customerprofile", required = true) String customerProfile,
			@RequestPart(name = "permanentadd", required = true) String customerPaddr,
			@RequestPart(name = "localadd", required = true) String customerLaddr,
			@RequestPart(name = "bankdetails", required = true) String bankDetails,
			@RequestPart(name = "aadhar", required = false) MultipartFile customerAadhar,
			@RequestPart(name = "pan", required = false) MultipartFile customerPan,
			@RequestPart(name = "profilePhoto", required = false) MultipartFile customerProfilePhoto,
			@RequestPart(name = "signature", required = false) MultipartFile customerSignature,
			@RequestPart(name = "salarySlip", required = false) MultipartFile customerSalaryslip,
			@RequestPart(name = "drivingLicense", required = false) MultipartFile customerDrivingLicense,
			@RequestPart(name = "bankStatement", required = false) MultipartFile customerBankStatement,
			@RequestPart(name = "carQuotation", required = false) MultipartFile customerCarQuotation,
			@RequestPart(name = "form16", required = false) MultipartFile customerForm16,
			@RequestPart(name = "itr", required = false) MultipartFile customerITR) {

		customerService.updateCustomer(customerId, customerProfile, customerPaddr, customerLaddr, bankDetails,
				customerAadhar, customerPan, customerProfilePhoto, customerSignature, customerSalaryslip,
				customerDrivingLicense, customerBankStatement, customerCarQuotation, customerForm16, customerITR);

		return new ResponseEntity<String>("Customer Updated Successfully!!!", HttpStatus.OK);

	}

	@PutMapping("/update_customer_status")
	public ResponseEntity<String> updateCustomerStatus(@RequestBody Customer customer) {
		customerService.updateCustomerStatus(customer);
		return new ResponseEntity<String>("Customer Updated Successfully!!!", HttpStatus.OK);
	}

	@PutMapping("/update_customer_SanctionDetails/{customerId}")
	public ResponseEntity<String> updateCustomerSanctionDetails(@PathVariable int customerId,
			@RequestBody SanctionDetails sanctionDetails) {
		System.err.println(customerId);
		System.err.println(sanctionDetails);
		customerService.updateCustomerSanctionDetails(customerId, sanctionDetails);
		return new ResponseEntity<String>("Customer Updated Successfully!!!", HttpStatus.OK);
	}

	@DeleteMapping("/delete_customer/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable int customerId) {

		customerService.deleteCustomer(customerId);

		return new ResponseEntity<String>("Customer Details Deleted!!!", HttpStatus.OK);

	}

	@GetMapping("/generate_pdf/{customerId}")
	public ResponseEntity<InputStreamResource> generatePdf(@PathVariable int customerId) {

		ByteArrayInputStream pdfByteArrayInputStream = customerService.generatePdf(customerId);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Disposition", "inline=Sanction Letter.pdf");

		InputStreamResource resource = new InputStreamResource(pdfByteArrayInputStream);

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(resource);

	}

	@GetMapping("/send_sanction_letter/{customerId}")
	public ResponseEntity<String> sendSanctionLetter(@PathVariable int customerId) {

		customerService.sendSanctionLetter(customerId);

		return new ResponseEntity<String>("Sanction Letter Send!!!", HttpStatus.OK);

	}

	@PutMapping("/save_disbusment/{customerId}")
	public ResponseEntity<String> saveDisbusment(@PathVariable int customerId) {

		customerService.saveDisbusment(customerId);

		return new ResponseEntity<String>("Customer Disbusment Saved!!!", HttpStatus.OK);

	}

	@PutMapping("/save_disbusment_status/{customerId}")
	public ResponseEntity<String> updateDisbusmentStatus(@PathVariable int customerId) {

		customerService.updateDisbusmentStatus(customerId);

		return new ResponseEntity<String>("Customer Disbusment Status Updated!!!", HttpStatus.OK);

	}

}
