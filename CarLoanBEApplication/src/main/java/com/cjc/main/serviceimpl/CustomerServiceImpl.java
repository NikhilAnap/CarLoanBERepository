package com.cjc.main.serviceimpl;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cjc.main.model.Customer;
import com.cjc.main.model.CustomerBankDetails;
import com.cjc.main.model.CustomerDocuments;
import com.cjc.main.model.CustomerLocalAddress;
import com.cjc.main.model.CustomerPermanentAddress;
import com.cjc.main.model.EnquiryDetails;
import com.cjc.main.repository.CustomerRepository;
import com.cjc.main.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Value("${spring.mail.username}")
	private String fromMail;

	@Autowired
	JavaMailSender sender;

	@Override
	public void saveCustomer(String customerProfile, String customerPaddr, String customerLaddr, String bankDetails,
			MultipartFile customerAadhar, MultipartFile customerPan, MultipartFile customerProfilePhoto,
			MultipartFile customerSignature, MultipartFile customerSalaryslip, MultipartFile customerDrivingLicense,
			MultipartFile customerBankStatement, MultipartFile customerCarQuotation, MultipartFile customerForm16,
			MultipartFile customerITR) {

		ObjectMapper mapper = new ObjectMapper();

		try {

			Customer customer = mapper.readValue(customerProfile, Customer.class);

			CustomerPermanentAddress customerPermanentaddr = mapper.readValue(customerPaddr,
					CustomerPermanentAddress.class);

			CustomerLocalAddress customerLocaladd = mapper.readValue(customerLaddr, CustomerLocalAddress.class);

			CustomerBankDetails customerbankdetaila = mapper.readValue(bankDetails, CustomerBankDetails.class);

			customer.setCustomerPermanentAddress(customerPermanentaddr);
			customer.setCustomerLocalAddress(customerLocaladd);
			customer.setCustomerBankDetails(customerbankdetaila);

			CustomerDocuments customerDocuments = new CustomerDocuments();

			customerDocuments.setCustomerAadhar(customerAadhar.getBytes());
			customerDocuments.setCustomerPan(customerPan.getBytes());
			customerDocuments.setCustomerProfilePhoto(customerProfilePhoto.getBytes());
			customerDocuments.setCustomerSignature(customerSignature.getBytes());
			customerDocuments.setCustomerSalaryslip(customerSalaryslip.getBytes());
			customerDocuments.setCustomerDrivingLicense(customerDrivingLicense.getBytes());
			customerDocuments.setCustomerBankStatement(customerBankStatement.getBytes());
			customerDocuments.setCustomerCarQuotation(customerCarQuotation.getBytes());
			customerDocuments.setCustomerForm16(customerForm16.getBytes());
			customerDocuments.setCustomerITR(customerITR.getBytes());

			customer.setCustomerDocuments(customerDocuments);

			// Generate Username
			String cname = customer.getCustomerName().toLowerCase();
			int min = 1000;
			int max = 9999;

			int number = (int) (Math.random() * (max - min + 1) + min);
			String username = cname + number;

			// Generate Password
			String cpass = customer.getCustomerName().toLowerCase();
			int min1 = 100;
			int max1 = 999;

			int number1 = (int) (Math.random() * (max1 - min1 + 1) + min1);
			String password = "acf@" + cpass + number1;

			// Generate Application Number
			int min2 = 10000000;
			int max2 = 100000000;

			int applicationNo = (int) (Math.random() * (max2 - min2 + 1) + min2);

			customer.setCustomerUsername(username);
			customer.setCustomerPassword(password);
			customer.setApplicationNo(applicationNo);

			customerRepository.save(customer);

			SimpleMailMessage simpleMail = new SimpleMailMessage();
			simpleMail.setFrom(fromMail);
			simpleMail.setTo(customer.getCustomerEmail());
			simpleMail.setSubject("About car loan application process...");
			simpleMail.setText("Hello,\r\n" + "\r\n" + "\r\n"
					+ "You received this email because you requested for a car loan process.\r\n" + "\r\n"
					+ "Your account details at the following:\r\n" + "\r\n" + "\r\n" + "Application Number = "
					+ customer.getApplicationNo() + "\r\n" + "Username = " + customer.getCustomerUsername() + "\r\n"
					+ "Password = " + customer.getCustomerPassword() + "\r\n" + "\r\n" + "Have a nice day,\r\n" + "\r\n"
					+ "[AutoCredFinanace]");

			sender.send(simpleMail);

		} catch (JsonProcessingException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	@Override
	public Iterable<Customer> getAllCustomer() {

		return customerRepository.findAll();
	}

	@Override
	public Customer getSingleCustomer(int customerId) {

		Optional<Customer> customer = customerRepository.findById(customerId);

		if (customer.isPresent()) {

			return customer.get();

		}

		return null;
	}

	@Override
	public void deleteCustomer(int customerId) {

		customerRepository.deleteById(customerId);

	}

	@Override
	public void updateCustomer(int customerId, String customerProfile, String customerPaddr, String customerLaddr,
			String bankDetails, MultipartFile customerAadhar, MultipartFile customerPan,
			MultipartFile customerProfilePhoto, MultipartFile customerSignature, MultipartFile customerSalaryslip,
			MultipartFile customerDrivingLicense, MultipartFile customerBankStatement,
			MultipartFile customerCarQuotation, MultipartFile customerForm16, MultipartFile customerITR) {

		ObjectMapper mapper = new ObjectMapper();

		try {

			Customer customer = mapper.readValue(customerProfile, Customer.class);

			customer.setCustomerId(customerId);

			CustomerPermanentAddress customerPermanentaddr = mapper.readValue(customerPaddr,
					CustomerPermanentAddress.class);

			CustomerLocalAddress customerLocaladd = mapper.readValue(customerLaddr, CustomerLocalAddress.class);

			CustomerBankDetails customerbankdetaila = mapper.readValue(bankDetails, CustomerBankDetails.class);

			customer.setCustomerPermanentAddress(customerPermanentaddr);
			customer.setCustomerLocalAddress(customerLocaladd);
			customer.setCustomerBankDetails(customerbankdetaila);

			CustomerDocuments customerDocuments = new CustomerDocuments();

			customerDocuments.setCustomerAadhar(customerAadhar.getBytes());
			customerDocuments.setCustomerPan(customerPan.getBytes());
			customerDocuments.setCustomerProfilePhoto(customerProfilePhoto.getBytes());
			customerDocuments.setCustomerSignature(customerSignature.getBytes());
			customerDocuments.setCustomerSalaryslip(customerSalaryslip.getBytes());
			customerDocuments.setCustomerDrivingLicense(customerDrivingLicense.getBytes());
			customerDocuments.setCustomerBankStatement(customerBankStatement.getBytes());
			customerDocuments.setCustomerCarQuotation(customerCarQuotation.getBytes());
			customerDocuments.setCustomerForm16(customerForm16.getBytes());
			customerDocuments.setCustomerITR(customerITR.getBytes());

			customer.setCustomerDocuments(customerDocuments);

			customer.setCustomerUsername(customer.getCustomerUsername());
			customer.setCustomerPassword(customer.getCustomerPassword());
			customer.setApplicationNo(customer.getApplicationNo());

			customerRepository.save(customer);

		} catch (JsonProcessingException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	@Override
	public void updateCustomerStatus(Customer customer) {

		Customer cust = customerRepository.findAllByCustomerId(customer.getCustomerId());

		cust.setApplicationStatus(customer.getApplicationStatus());

		customerRepository.save(cust);
	}

}
