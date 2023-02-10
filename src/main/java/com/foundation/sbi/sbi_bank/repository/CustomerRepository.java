package com.foundation.sbi.sbi_bank.repository;

import com.foundation.sbi.sbi_bank.entity.Contact;
import com.foundation.sbi.sbi_bank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByFirstName(String customerName);

    Optional<Customer> findByIdentificationNumber(String identificationNumber);


}