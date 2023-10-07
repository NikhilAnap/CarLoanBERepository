package com.cjc.main.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cjc.main.model.EnquiryDetails;
import com.cjc.main.repository.EnquiryRepository;
import com.cjc.main.service.EnquiryService;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	EnquiryRepository enquiryRepository;

	@Value("${spring.mail.username}")
	private String fromMail;

	@Autowired
	JavaMailSender sender;

	@Override
	public EnquiryDetails saveEnquiry(EnquiryDetails enquiryDetails) {

		return enquiryRepository.save(enquiryDetails);
	}

	@Override
	public Iterable<EnquiryDetails> getAllEnquiry() {

		return enquiryRepository.findAll();
	}

	@Override
	public EnquiryDetails getSingleEnquiry(int id) {

		Optional<EnquiryDetails> enquiry = enquiryRepository.findById(id);

		if (enquiry.isPresent()) {

			return enquiry.get();

		}

		return null;
	}

	@Override
	public void deleteEnquiry(int id) {

		enquiryRepository.deleteById(id);

	}

	@Override
	public EnquiryDetails saveCibil(EnquiryDetails enquiryDetails, String customerPanno, int id) {

		enquiryDetails.setId(id);

		return enquiryRepository.save(enquiryDetails);

	}

	@Override
	public EnquiryDetails updateEnquiry(int id, EnquiryDetails enquiryDetails) {

		enquiryDetails.setId(id);

		if (enquiryDetails.getApplicationStatus() != null) {

			SimpleMailMessage simpleMail = new SimpleMailMessage();
			simpleMail.setFrom(fromMail);
			simpleMail.setTo(enquiryDetails.getCustomerEmail());
			simpleMail.setSubject("About car loan application process...");
			simpleMail.setText("Hello,\r\n" + "\r\n" + "\r\n"
					+ "You received this email because you requested for a car loan process.\r\n" + "\r\n"
					+ "Your account details at the following:\r\n" + "\r\n" + "\r\n" + "Cibil Score = "
					+ enquiryDetails.getCustomerCibilDetails().getCibilScore() + "\r\n" + "Cibil Status = "
					+ enquiryDetails.getCustomerCibilDetails().getCibilStatus() + "\r\n" + "Application Status = "
					+ enquiryDetails.getApplicationStatus() + "\r\n" + "\r\n" + "Have a nice day,\r\n" + "\r\n"
					+ "[AutoCredFinanace]");

			sender.send(simpleMail);

		}

		return enquiryRepository.save(enquiryDetails);
	}

	@Override
	public void saveByStatus(int enquiryId, EnquiryDetails enquiryDetails) {

		enquiryDetails.setId(enquiryId);

		enquiryRepository.save(enquiryDetails);

	}

}
