package com.foundation.sbi.sbi_bank.repository;

import com.foundation.sbi.sbi_bank.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    //Optional<Contact> findByCustomer_IdentificationNumber(String identificationNumber);

}