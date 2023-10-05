package com.cjc.main.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cjc.main.model.CustomerCibilDetails;
import com.cjc.main.model.EnquiryDetails;
import com.cjc.main.service.EnquiryService;

@RestController
@RequestMapping("/enquiry")
public class EnquiryController {

	@Autowired
	EnquiryService enquiryService;

	@PostMapping("/save_enquiry")
	public ResponseEntity<EnquiryDetails> saveEnquiry(@RequestBody EnquiryDetails enquiryDetails) {

		EnquiryDetails enquiry = enquiryService.saveEnquiry(enquiryDetails);

		return new ResponseEntity<EnquiryDetails>(enquiry, HttpStatus.CREATED);

	}

	@GetMapping("/getAll_enquiry")
	public ResponseEntity<Iterable<EnquiryDetails>> getAllEnquiry() {

		Iterable<EnquiryDetails> enquiries = enquiryService.getAllEnquiry();

		return new ResponseEntity<Iterable<EnquiryDetails>>(enquiries, HttpStatus.OK);

	}

	@GetMapping("/getSingle_enquiry/{enquiryId}")
	public ResponseEntity<EnquiryDetails> getSingleEnquiry(@PathVariable int enquiryId) {

		EnquiryDetails enquiry = enquiryService.getSingleEnquiry(enquiryId);

		return new ResponseEntity<EnquiryDetails>(enquiry, HttpStatus.OK);

	}

	@PutMapping("/update_enquiry/{enquiryId}")
	public ResponseEntity<EnquiryDetails> updateEnquiry(@PathVariable int enquiryId,
			@RequestBody EnquiryDetails enquiryDetails) {

		EnquiryDetails enquiry = enquiryService.updateEnquiry(enquiryId, enquiryDetails);

		return new ResponseEntity<EnquiryDetails>(enquiry, HttpStatus.OK);

	}

	@DeleteMapping("/delete_enquiry/{enquiryId}")
	public ResponseEntity<String> deleteEnquiry(@PathVariable int enquiryId) {

		enquiryService.deleteEnquiry(enquiryId);

		return new ResponseEntity<String>("Enquiry Deleted!!!", HttpStatus.OK);

	}

	@PutMapping("/check_cibil/{customerPanno}")
	public ResponseEntity<String> checkCibilScore(@PathVariable String customerPanno) {

		Iterable<EnquiryDetails> enquiries = enquiryService.getAllEnquiry();

		System.out.println(customerPanno);

		for (EnquiryDetails enquiryDetails : enquiries) {

			if (customerPanno.equals(enquiryDetails.getCustomerPanno())) {

				int enquiryId = enquiryDetails.getEnquiryId();

				int min = 600;
				int max = 900;

				int cibilScore = (int) (Math.random() * (max - min + 1) + min);

				CustomerCibilDetails customerCibilDetails = new CustomerCibilDetails();

				if (cibilScore < 700) {

					customerCibilDetails.setCibilScore(cibilScore);
					customerCibilDetails.setCibilStatus("CIBIL_BAD");

					enquiryDetails.setCustomerCibilDetails(customerCibilDetails);

					enquiryService.saveCibil(enquiryDetails, customerPanno, enquiryId);

					return new ResponseEntity<String>("CIBIL_BAD", HttpStatus.OK);

				} else if (cibilScore > 699 && cibilScore < 750) {

					customerCibilDetails.setCibilScore(cibilScore);
					customerCibilDetails.setCibilStatus("CIBIL_GOOD");

					enquiryDetails.setCustomerCibilDetails(customerCibilDetails);

					enquiryService.saveCibil(enquiryDetails, customerPanno, enquiryId);

					return new ResponseEntity<String>("CIBIL_GOOD", HttpStatus.OK);

				} else if (cibilScore > 749 && cibilScore < 800) {

					customerCibilDetails.setCibilScore(cibilScore);
					customerCibilDetails.setCibilStatus("CIBIL_BEST");

					enquiryDetails.setCustomerCibilDetails(customerCibilDetails);

					enquiryService.saveCibil(enquiryDetails, customerPanno, enquiryId);

					return new ResponseEntity<String>("CIBIL_BEST", HttpStatus.OK);

				} else {

					customerCibilDetails.setCibilScore(cibilScore);
					customerCibilDetails.setCibilStatus("CIBIL_EXCELLENT");

					enquiryDetails.setCustomerCibilDetails(customerCibilDetails);

					enquiryService.saveCibil(enquiryDetails, customerPanno, enquiryId);

					return new ResponseEntity<String>("CIBIL_EXCELLENT", HttpStatus.OK);

				}
			}
		}

		return new ResponseEntity<String>("Enter Valid Pan Number!!!", HttpStatus.BAD_REQUEST);

	}

	@GetMapping("/get_by_status/{applicationStatus}")
	public ResponseEntity<Iterable<EnquiryDetails>> getByStatus(@PathVariable String applicationStatus) {

		Iterable<EnquiryDetails> enquiries = enquiryService.getAllEnquiry();

		List enquiryList = (List) new ArrayList();

		for (EnquiryDetails details : enquiries) {

			if (applicationStatus.equalsIgnoreCase(details.getApplicationStatus())) {

				enquiryList.add(details);

			}

		}

		return new ResponseEntity<Iterable<EnquiryDetails>>((Iterable<EnquiryDetails>) enquiryList, HttpStatus.OK);

	}

	@GetMapping("/get_by_pan/{customerPanno}")
	private ResponseEntity<EnquiryDetails> getByPanCard(@PathVariable String customerPanno) {

		Iterable<EnquiryDetails> enquiries = enquiryService.getAllEnquiry();

		for (EnquiryDetails details : enquiries) {

			if (customerPanno.equals(details.getCustomerPanno())) {

				return new ResponseEntity<EnquiryDetails>(details, HttpStatus.OK);

			}

		}

		// return new ResponseEntity<EnquiryDetails>(null, HttpStatus.BAD_REQUEST);

		return null;

	}

}
