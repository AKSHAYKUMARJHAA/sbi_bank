package com.foundation.sbi.sbi_bank.exception;

public class ExceptionResponse {
    private String url;
    private String message;

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
