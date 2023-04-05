package com.foundation.sbi.sbi_bank.model;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
@Getter
@Setter
public class AccountDetails {
    private int accountNumber;
    private Double currentBalance;
    @NotNull
    private String accountType;

    }

