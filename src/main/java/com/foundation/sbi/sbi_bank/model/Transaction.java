package com.foundation.sbi.sbi_bank.model;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Transaction {
    private int toAccount;
    private int transferAmount;
    private int fromAccount;

}
