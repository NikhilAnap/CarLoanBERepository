package com.cjc.main.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjc.main.model.EnquiryDetails;
import com.cjc.main.repository.EnquiryRepository;
import com.cjc.main.service.EnquiryService;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	EnquiryRepository enquiryRepository;

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
	public void saveCibil(EnquiryDetails enquiryDetails, String customerPanno, int id) {

		enquiryDetails.setId(id);

		enquiryRepository.save(enquiryDetails);

	}

	@Override
	public EnquiryDetails updateEnquiry(int id, EnquiryDetails enquiryDetails) {

		enquiryDetails.setId(id);

		return enquiryRepository.save(enquiryDetails);
	}

}
