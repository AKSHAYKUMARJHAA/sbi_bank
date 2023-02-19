package com.foundation.sbi.sbi_bank.repository;

import com.foundation.sbi.sbi_bank.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {


    @Override
    Optional<AccountType> findById(Long aLong);

}