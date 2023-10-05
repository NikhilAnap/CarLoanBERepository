package com.cjc.main.serviceimpl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cjc.main.model.Employee;
import com.cjc.main.model.EmployeeBankDetails;
import com.cjc.main.model.EmployeeDocument;
import com.cjc.main.model.EmployeeLocalAddress;
import com.cjc.main.model.EmployeePermanentAddress;
import com.cjc.main.repository.EmployeeRepository;
import com.cjc.main.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Value("${spring.mail.username}")
	private String fromMail;

	@Autowired
	JavaMailSender sender;

	@Override
	public void saveEmployee(String employeeDetails, String employeeLocalAddress, String employeePermanentAddress,
			String employeeBankDetails, MultipartFile employeeProfileImg, MultipartFile employeeAadharCard,
			MultipartFile employeePanCard, MultipartFile employeeSingnature) {

		ObjectMapper mapper = new ObjectMapper();

		try {

			Employee employee = mapper.readValue(employeeDetails, Employee.class);

			EmployeeLocalAddress localAddress = mapper.readValue(employeeLocalAddress, EmployeeLocalAddress.class);
			EmployeePermanentAddress permanentAddress = mapper.readValue(employeePermanentAddress,
					EmployeePermanentAddress.class);
			EmployeeBankDetails bankDetails = mapper.readValue(employeeBankDetails, EmployeeBankDetails.class);

			EmployeeDocument employeeDocument = new EmployeeDocument();
			employeeDocument.setEmployeeProfileImg(employeeProfileImg.getBytes());
			employeeDocument.setEmployeeAadharCard(employeeAadharCard.getBytes());
			employeeDocument.setEmployeePanCard(employeePanCard.getBytes());
			employeeDocument.setEmployeeSingnature(employeeSingnature.getBytes());

			employee.setEmployeeLocalAddress(localAddress);
			employee.setEmployeePermanentaddress(permanentAddress);
			employee.setEmployeeBankDetails(bankDetails);
			employee.setEmployeeDocuments(employeeDocument);

			// Generate Username
			String cname = employee.getEmployeeName().toLowerCase();
			int min = 10000;
			int max = 99999;

			int number = (int) (Math.random() * (max - min + 1) + min);
			String username = cname + number;

			// Generate Password
			String cpass = employee.getEmployeeName().toLowerCase();
			int min1 = 1000;
			int max1 = 9999;

			int number1 = (int) (Math.random() * (max1 - min1 + 1) + min1);
			String password = "acf@" + cpass + number1;

			employee.setUsername(username);
			employee.setPassword(password);

			employeeRepository.save(employee);

			SimpleMailMessage simpleMail = new SimpleMailMessage();
			simpleMail.setFrom(fromMail);
			simpleMail.setTo(employee.getEmployeeEmail());
			simpleMail.setSubject("About AutoCredFinanace details...");
			simpleMail.setText("Hello,\r\n" + "\r\n" + "\r\n"
					+ "You received this email because you appointed in AutoCredFinanace.\r\n" + "\r\n"
					+ "Your account details at the following:\r\n" + "\r\n" + "\r\n" + "Your Role = "
					+ employee.getEmployeeRole() + "\r\n" + "Username = " + employee.getUsername() + "\r\n"
					+ "Password = " + employee.getPassword() + "\r\n" + "\r\n" + "Have a nice day,\r\n" + "\r\n"
					+ "[AutoCredFinanace]");

			sender.send(simpleMail);

		} catch (JsonProcessingException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	@Override
	public Iterable<Employee> getAllEmployee() {

		return employeeRepository.findAll();
	}

	@Override
	public Employee getSingleEmployee(int employeeId) {

		Optional<Employee> employee = employeeRepository.findById(employeeId);

		if (employee.isPresent()) {

			return employee.get();

		}

		return null;
	}

	@Override
	public void updateEmployee(int employeeId, String employeeDetails, String employeeLocalAddress,
			String employeePermanentAddress, String employeeBankDetails, MultipartFile employeeProfileImg,
			MultipartFile employeeAadharCard, MultipartFile employeePanCard, MultipartFile employeeSingnature) {

		ObjectMapper mapper = new ObjectMapper();

		try {

			Employee employee = mapper.readValue(employeeDetails, Employee.class);

			employee.setEmployeeId(employeeId);

			EmployeeLocalAddress localAddress = mapper.readValue(employeeLocalAddress, EmployeeLocalAddress.class);
			EmployeePermanentAddress permanentAddress = mapper.readValue(employeePermanentAddress,
					EmployeePermanentAddress.class);
			EmployeeBankDetails bankDetails = mapper.readValue(employeeBankDetails, EmployeeBankDetails.class);

			EmployeeDocument employeeDocument = new EmployeeDocument();
			employeeDocument.setEmployeeProfileImg(employeeProfileImg.getBytes());
			employeeDocument.setEmployeeAadharCard(employeeAadharCard.getBytes());
			employeeDocument.setEmployeePanCard(employeePanCard.getBytes());
			employeeDocument.setEmployeeSingnature(employeeSingnature.getBytes());

			employee.setEmployeeLocalAddress(localAddress);
			employee.setEmployeePermanentaddress(permanentAddress);
			employee.setEmployeeBankDetails(bankDetails);
			employee.setEmployeeDocuments(employeeDocument);

			employeeRepository.save(employee);

		} catch (JsonProcessingException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}


	}

	@Override
	public void deleteEmployee(int employeeId) {

		employeeRepository.deleteById(employeeId);

	}

}
