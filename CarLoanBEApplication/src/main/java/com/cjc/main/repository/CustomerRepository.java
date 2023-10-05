package com.cjc.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cjc.main.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
