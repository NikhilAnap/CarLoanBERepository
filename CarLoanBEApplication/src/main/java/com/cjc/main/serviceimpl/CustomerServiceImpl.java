package com.cjc.main.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cjc.main.controller.CustomerController;
import com.cjc.main.model.Customer;
import com.cjc.main.model.CustomerBankDetails;
import com.cjc.main.model.CustomerDocuments;
import com.cjc.main.model.CustomerLocalAddress;
import com.cjc.main.model.CustomerPermanentAddress;
import com.cjc.main.model.EnquiryDetails;
import com.cjc.main.model.LedgerDisbusment;
import com.cjc.main.model.SanctionDetails;
import com.cjc.main.repository.CustomerRepository;
import com.cjc.main.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Value("${spring.mail.username}")
	private String fromMail;

	@Autowired
	JavaMailSender sender;

	private String filePath = "E:\\Bank Project\\SanctionLetter.doc";
	File file = new File(filePath);

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

	@Override
	public void updateCustomerSanctionDetails(int customerId, SanctionDetails sanctionDetails) {

		Customer cust = customerRepository.findAllByCustomerId(customerId);

		cust.setSanctionDetails(sanctionDetails);

		customerRepository.save(cust);
	}

	public ByteArrayInputStream generatePdf(int customerId) {

		Customer customer = getSingleCustomer(customerId);

		String title = "Sanction Letter";

		Document doc = new Document();
		doc.setPageSize(PageSize.LETTER);
		doc.setMargins(30, 30, 30, 30);
		doc.setMarginMirroring(false);

		ByteArrayOutputStream output = new ByteArrayOutputStream();

		PdfWriter.getInstance(doc, output);

		doc.open();

		Rectangle rect = new Rectangle(595, 780, 18, 18); // you can resize rectangle
		rect.enableBorderSide(1);
		rect.enableBorderSide(2);
		rect.enableBorderSide(4);
		rect.enableBorderSide(8);
		// rect.setBorderColor(BaseColor.BLACK);
		rect.setBorderWidth(2);
		rect.setBorder(Rectangle.BOX);

		doc.add(rect);

		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15, CMYKColor.BLACK);
		Paragraph titlePara = new Paragraph(title, titleFont);
		titlePara.setAlignment("center");

		doc.add(titlePara);

		Date date = new Date();

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String pdfDateFormat = format.format(date);
		String pdfDate = "Date :- " + pdfDateFormat + "";

		Font dateFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
		Paragraph datePara = new Paragraph(pdfDate, dateFont);

		datePara.setSpacingBefore(20);

		doc.add(datePara);

		String name = customer.getCustomerName();
		String bank = "Auto Cred Finance";

		Font bankFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
		Paragraph bankName = new Paragraph(bank, bankFont);

		String to = "Dear " + name + " ,";
		Paragraph toPara = new Paragraph(to);
		toPara.setSpacingBefore(40);

		doc.add(toPara);

		String text = "Thank you for choosing " + bankName + ". Based on the application and information\r\n"
				+ "provided therei n,we are pleased to extend an offer to you for a loan as per the preliminary terms and conditions mentioned below:\r\n"
				+ "";
		Paragraph textPara = new Paragraph(text);
		textPara.setSpacingBefore(25);

		doc.add(textPara);

		PdfPTable table1 = new PdfPTable(2);
		table1.setWidths(new int[] { 2, 2 });
		table1.setWidthPercentage(60F);
		table1.setSpacingBefore(25);
		table1.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell application = new PdfPCell();

		application.setFixedHeight(20f);
		application.setPhrase(new Phrase("Application No.:"));
		table1.addCell(application);
		application.setPhrase(new Phrase(String.valueOf(customer.getApplicationNo())));
		table1.addCell(application);

		application.setPhrase(new Phrase("Sanctioned Date:"));
		table1.addCell(application);
		// customer.getSanctionDetails().getAgreementDate()
		application.setPhrase(new Phrase());
		table1.addCell(application);

		application.setPhrase(new Phrase("Applicant Name:"));
		table1.addCell(application);
		// customer.getCustomerName()
		application.setPhrase(new Phrase());
		table1.addCell(application);

		application.setPhrase(new Phrase("Mobile No.:"));
		table1.addCell(application);
		// String.valueOf(customer.getCustomerMobileno())
		application.setPhrase(new Phrase());
		table1.addCell(application);

		doc.add(table1);

		PdfPTable table2 = new PdfPTable(2);
		table2.setWidths(new int[] { 3, 5 });
		table2.setWidthPercentage(100F);
		table2.setSpacingBefore(25);
		table2.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell loan = new PdfPCell();

		loan.setFixedHeight(20f);
		loan.setPhrase(new Phrase("Loan Type:"));
		table2.addCell(loan);
		loan.setPhrase(new Phrase("Car Loan"));
		table2.addCell(loan);

		loan.setFixedHeight(20f);
		loan.setPhrase(new Phrase("Loan Amount Sanctioned:"));
		table2.addCell(loan);
		// String.valueOf(customer.getSanctionDetails().getSanctionAmount())
		loan.setPhrase(new Phrase());
		table2.addCell(loan);

		loan.setFixedHeight(40f);
		loan.setPhrase(new Phrase("Reference Interest Rate:"));
		table2.addCell(loan);
		// String.valueOf(customer.getSanctionDetails().getRateOfInterest()
		loan.setPhrase(new Phrase(
				"% per annum (Interest Type: Floating rate of interest I Periodicity of Interest Application: Monthly)"));
		table2.addCell(loan);

		loan.setFixedHeight(40f);
		loan.setPhrase(new Phrase("Floating Interest Rate:"));
		table2.addCell(loan);
		loan.setPhrase(new Phrase("Reference rate applicable at the time of disbursement - 4.90% per annum"));
		table2.addCell(loan);

		loan.setFixedHeight(20f);
		loan.setPhrase(new Phrase("Loan Tenor (In years):"));
		table2.addCell(loan);
		// String.valueOf(customer.getSanctionDetails().getTenure())
		loan.setPhrase(new Phrase());
		table2.addCell(loan);

		loan.setFixedHeight(20f);
		loan.setPhrase(new Phrase("Total Processing Charges:"));
		table2.addCell(loan);
		loan.setPhrase(new Phrase("Up to 0.5% of the total loan amount"));
		table2.addCell(loan);

		loan.setFixedHeight(20f);
		loan.setPhrase(new Phrase("Origination Fee (inclusive of GST):"));
		table2.addCell(loan);
		loan.setPhrase(new Phrase("2500"));
		table2.addCell(loan);

		loan.setFixedHeight(20f);
		loan.setPhrase(new Phrase("Sanction Letter Validity :"));
		table2.addCell(loan);
		loan.setPhrase(new Phrase("180 days from the date of sanction"));
		table2.addCell(loan);

		loan.setFixedHeight(20f);
		loan.setPhrase(new Phrase("Amount of EMI (INR):"));
		table2.addCell(loan);
		loan.setPhrase(new Phrase("95,000"));
		table2.addCell(loan);

		doc.add(table2);

		String additional = "Additional conditions to comply prior to loan disbursal:";

		Font additionalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
		Paragraph additionalPara = new Paragraph(additional, additionalFont);

		additionalPara.setSpacingBefore(25);

		doc.add(additionalPara);

		String condition = "1. Repayment from " + bankName + "\r\n"
				+ "2.	Legal vetting and search to be conducted\r\n" + "3.	NOC and offered collateral\r\n"
				+ "4.	Confirmation form,official ID and copy of ID\r\n" + "";
		Paragraph conditionPara = new Paragraph(condition);
		conditionPara.setSpacingBefore(5);

		doc.add(conditionPara);

		doc.close();

		SanctionDetails sanctionDetails = customer.getSanctionDetails();
		sanctionDetails.setSanctionLetter(output.toByteArray());
		customer.setSanctionDetails(sanctionDetails);

		updateSanctionLetter(customer);

		ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
		return input;
	}

	@Override
	public void sendSanctionLetter(int customerId) {

		Customer customer = getSingleCustomer(customerId);

		MimeMessage mimeMessage = sender.createMimeMessage();

		try {

			OutputStream stream = new FileOutputStream(file);
			stream.write(customer.getSanctionDetails().getSanctionLetter());

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setFrom(fromMail);
			System.out.println(customer.getCustomerEmail());
			helper.setSubject("Sanction Letter of Car Loan...");
			helper.setText("Sanction letter of your loan application");
			helper.addAttachment("SanctionLetter.pdf", file);

			sender.send(mimeMessage);

		} catch (MessagingException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public void updateSanctionLetter(Customer customer) {

		customer.setCustomerId(customer.getCustomerId());

		SanctionDetails sanctionDetails = customer.getSanctionDetails();

		sanctionDetails.setSanctionId(sanctionDetails.getSanctionId());

		customer.setSanctionDetails(sanctionDetails);

		customerRepository.save(customer);

	}

	@Override
	public void saveDisbusment(int customerId) {

		Date date = new Date();

		// Date newDate = new Date(2023, 10, 13);

		Customer customer = getSingleCustomer(customerId);

		for (int i = 1; i < +customer.getSanctionDetails().getTenure(); i++) {

			int paymentNumber = i;

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String paymentDate = format.format(date);

			double principalAmount = customer.getSanctionDetails().getSanctionAmount();
			int tenure = customer.getSanctionDetails().getTenure();
			double interest = customer.getSanctionDetails().getRateOfInterest();

			double emi = principalAmount * interest * ((1 + interest) * 120) / (((1 + interest) * 120) - 1);

			double balance = principalAmount - emi;

			LedgerDisbusment disbusment = new LedgerDisbusment();
			disbusment.setPaymentNumber(paymentNumber);
			disbusment.setLastDateOfpayment(paymentDate);
			disbusment.setEmiPaid(emi);
			disbusment.setStatus("Pending");

			customer.getLedgerDisbusment().add(disbusment);

			customerRepository.save(customer);

		}
	}

	@Override
	public void updateDisbusmentStatus(int customerId) {

		Date date = new Date();

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String paidDate = format.format(date);

		Customer customer = customerRepository.findAllByCustomerId(customerId);

		LedgerDisbusment disbusment = new LedgerDisbusment();

		disbusment.setStatus("Paid");
		disbusment.setPaidDate(paidDate);

		customer.getLedgerDisbusment().add(disbusment);

		customerRepository.save(customer);

	}

}
