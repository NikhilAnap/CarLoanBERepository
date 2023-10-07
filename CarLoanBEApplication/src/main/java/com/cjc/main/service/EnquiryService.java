package com.cjc.main.service;

import com.cjc.main.model.EnquiryDetails;

public interface EnquiryService {

	public EnquiryDetails saveEnquiry(EnquiryDetails enquiryDetails);

	public Iterable<EnquiryDetails> getAllEnquiry();

	public EnquiryDetails getSingleEnquiry(int id);

	public void deleteEnquiry(int id);

	public EnquiryDetails  saveCibil(EnquiryDetails enquiryDetails, String customerPanno, int id);

	public EnquiryDetails updateEnquiry(int id, EnquiryDetails enquiryDetails);

	public void saveByStatus(int enquiryId, EnquiryDetails enquiryDetails);


}
