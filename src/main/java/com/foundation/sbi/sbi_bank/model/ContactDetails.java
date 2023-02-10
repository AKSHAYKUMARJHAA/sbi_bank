package com.foundation.sbi.sbi_bank.model;

public class ContactDetails {
    private String emailId;
    private int phone_No;
    private String address;
    private String city;
    private String state;
    private String country;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public int getPhone_No() {
        return phone_No;
    }

    public void setPhone_No(int phone_No) {
        this.phone_No = phone_No;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
