package com.cjc.main.service;

import com.cjc.main.model.EnquiryDetails;

public interface EnquiryService {

	public EnquiryDetails saveEnquiry(EnquiryDetails enquiryDetails);

	public Iterable<EnquiryDetails> getAllEnquiry();

	public EnquiryDetails getSingleEnquiry(int enquiryId);

	public void deleteEnquiry(int enquiryId);

	public void saveCibil(EnquiryDetails enquiryDetails, String customerPanno, int enquiryId);

	public EnquiryDetails updateEnquiry(int enquiryId, EnquiryDetails enquiryDetails);

}
