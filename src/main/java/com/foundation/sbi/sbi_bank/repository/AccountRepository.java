package com.foundation.sbi.sbi_bank.repository;

import com.foundation.sbi.sbi_bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByCustomer_IdentificationNumber(String identificationNumber);
    Account findByAccountNumber(int accountNumber);



}