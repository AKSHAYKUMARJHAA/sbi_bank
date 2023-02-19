package com.foundation.sbi.sbi_bank.model;
public class Transaction {
    private int toAccount;
    private int transferAmount;
    private int fromAccount;
    public int getToAccount() {
        return toAccount;
    }
    public void setToAccount(int toAccount) {
        this.toAccount = toAccount;
    }

    public int getTransferAmount(double v) {
        return transferAmount;
    }

    public void setTransferAmount(int transferAmount) {
        this.transferAmount = transferAmount;
    }

    public int getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(int fromAccount) {
        this.fromAccount = fromAccount;
    }
}
