package com.foundation.sbi.sbi_bank.model;

import java.util.List;

public class AccountTypeUpdate {

    private String accountType;

    private List<String> field;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<String> getField() {
        return field;
    }

    public void setField(List<String> field) {
        this.field = field;
    }
}
