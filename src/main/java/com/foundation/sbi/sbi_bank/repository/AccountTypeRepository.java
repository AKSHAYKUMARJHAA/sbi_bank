package com.foundation.sbi.sbi_bank.repository;

import com.foundation.sbi.sbi_bank.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {


    @Override
    Optional<AccountType> findById(Long aLong);
}