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
	public EnquiryDetails getSingleEnquiry(int enquiryId) {

		Optional<EnquiryDetails> enquiry = enquiryRepository.findById(enquiryId);

		if (enquiry.isPresent()) {

			return enquiry.get();

		}

		return null;
	}

	@Override
	public void deleteEnquiry(int enquiryId) {

		enquiryRepository.deleteById(enquiryId);

	}

	@Override
	public void saveCibil(EnquiryDetails enquiryDetails, String customerPanno, int enquiryId) {

		enquiryDetails.setEnquiryId(enquiryId);

		enquiryRepository.save(enquiryDetails);

	}

	@Override
	public EnquiryDetails updateEnquiry(int enquiryId, EnquiryDetails enquiryDetails) {

		enquiryDetails.setEnquiryId(enquiryId);

		return enquiryRepository.save(enquiryDetails);
	}

}
