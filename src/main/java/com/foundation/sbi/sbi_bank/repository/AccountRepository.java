package com.foundation.sbi.sbi_bank.repository;

import com.foundation.sbi.sbi_bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByCustomer_IdentificationNumberAndIsDeletedFalse(String identificationNumber);
    List<Account> findByCustomer_IdentificationNumber(String identificationNumber);
    Account findByAccountNumber(int accountNumber);


}