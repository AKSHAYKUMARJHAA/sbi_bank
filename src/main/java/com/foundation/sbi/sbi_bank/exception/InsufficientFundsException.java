package com.foundation.sbi.sbi_bank.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message){
        super(message);
    }

}
