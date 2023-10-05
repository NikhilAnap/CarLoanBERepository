package com.cjc.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cjc.main.model.EnquiryDetails;

public interface EnquiryRepository extends JpaRepository<EnquiryDetails, Integer> {

}
