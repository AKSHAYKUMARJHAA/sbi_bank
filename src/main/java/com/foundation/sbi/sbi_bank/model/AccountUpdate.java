package com.foundation.sbi.sbi_bank.model;

import java.util.List;

public class AccountUpdate {

        private int currentBalance;
       // private String accountType;
        private List<String> field;

        // Getters and setters for the fields
        public int getCurrentBalance() {
            return currentBalance;
        }
        public void setCurrentBalance(int currentBalance) {
            this.currentBalance = currentBalance;
        }
    /*    public String getAccountType() {
            return accountType;
        }*/
       /* public void setAccountType(String accountType) {
            this.accountType = accountType;
        }*/
        public List<String> getField() {
            return field;
        }
        public void setField(List<String> field) {
            this.field = field;
        }
    }



