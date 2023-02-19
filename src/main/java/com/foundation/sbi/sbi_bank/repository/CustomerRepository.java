package com.foundation.sbi.sbi_bank.repository;

import com.foundation.sbi.sbi_bank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

  Customer findByIdentificationNumber(String identificationNumber);

}