package com.foundation.sbi.sbi_bank.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foundation.sbi.sbi_bank.entity.Account;
import com.foundation.sbi.sbi_bank.entity.AccountType;
import com.foundation.sbi.sbi_bank.entity.Contact;
import com.foundation.sbi.sbi_bank.entity.Customer;
import com.foundation.sbi.sbi_bank.model.*;
import com.foundation.sbi.sbi_bank.repository.AccountRepository;
import com.foundation.sbi.sbi_bank.repository.AccountTypeRepository;
import com.foundation.sbi.sbi_bank.repository.ContactRepository;
import com.foundation.sbi.sbi_bank.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;

@Slf4j
@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;

    public String addCustomerDetails(CustomerDetails customerDetails) {
        validateRequestData(customerDetails);
        Account accounts = accountRepository.findByCustomer_IdentificationNumber(customerDetails.getIdentificationNumber());
        if (accounts != null) {
            throw new RuntimeException("Customer with same details already exists");
        }
        var accountDataToSave = setAccountDataToSave(customerDetails);
        accountRepository.save(accountDataToSave);
        return "Account Details added successfully";
    }

    private Account setAccountDataToSave(CustomerDetails customerDetails) {
        Account account = new Account();
        account.setAccountNumber(customerDetails.getAccountDetails().getAccountNumber());
        account.setCurrentBalance(customerDetails.getAccountDetails().getCurrentBalance());

        Customer customer = new Customer();
        customer.setFirstName(customerDetails.getFirstName());
        customer.setMiddleName(customerDetails.getMiddleName());
        customer.setLastName(customerDetails.getLastName());
        customer.setIdentificationNumber(customerDetails.getIdentificationNumber());
        account.setCustomer(customer);

        Contact contact = new Contact();
        contact.setEmailId(customerDetails.getContactDetails().getEmailId());
        contact.setPhone_No(customerDetails.getContactDetails().getPhone_No());
        contact.setAddress(customerDetails.getContactDetails().getAddress());
        contact.setCity(customerDetails.getContactDetails().getCity());
        contact.setState(customerDetails.getContactDetails().getState());
        contact.setCountry(customerDetails.getContactDetails().getCountry());
        customer.setContact(contact);

        AccountType accountType = new AccountType();
        accountType.setAccountType(customerDetails.getAccountDetails().getAccountType());
        account.setAccountType(accountType);
        Account save1 = accountRepository.save(account);
        return save1;
    }

    private void validateRequestData(CustomerDetails customerDetails) {
        log.info("method call validateRequestData....");
        if (customerDetails.getIdentificationNumber() != null) {
            log.info("Invalid Identification number");
        }
        if (customerDetails.getAccountDetails() != null) {
            log.info("Account details are mandatory....");
        }
    }

    public CustomerDetails getCustomerDetails(String identificationNumber) {
        Account account = accountRepository.findByCustomer_IdentificationNumber(identificationNumber);
        if (account != null) {
            CustomerDetails customerDetails = new CustomerDetails();
            customerDetails.setFirstName(account.getCustomer().getFirstName());
            customerDetails.setMiddleName(account.getCustomer().getMiddleName());
            customerDetails.setLastName(account.getCustomer().getLastName());
            customerDetails.setIdentificationNumber(account.getCustomer().getIdentificationNumber());

            AccountDetails accountDetails = new AccountDetails();
            accountDetails.setAccountNumber(account.getAccountNumber());
            accountDetails.setCurrentBalance(account.getCurrentBalance());
            accountDetails.setAccountType(account.getAccountType().getAccountType());
            customerDetails.setAccountDetails(accountDetails);

            ContactDetails contactDetails = new ContactDetails();
            contactDetails.setEmailId(account.getCustomer().getContact().getEmailId());
            contactDetails.setPhone_No(account.getCustomer().getContact().getPhone_No());
            contactDetails.setCity(account.getCustomer().getContact().getCity());
            contactDetails.setAddress(account.getCustomer().getContact().getAddress());
            contactDetails.setState(account.getCustomer().getContact().getState());
            contactDetails.setCountry(account.getCustomer().getContact().getCountry());
            customerDetails.setContactDetails(contactDetails);
            return customerDetails;
        } else {
            return null;
        }
    }

    public String deleteByAccountNumber(int accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        accountRepository.deleteById(account.getId());
        return "Data is successfully deleted";
    }

    public String transfer(Transaction transaction) {
        //Fetching account details for paying account
        Account fromAccount = accountRepository.findByAccountNumber(transaction.getFromAccount());
        if (fromAccount.getCurrentBalance() < transaction.getTransferAmount(5000.0)) {
            return "Insufficient fund";
        }
        //Deducting amount
        var remainingBalance = fromAccount.getCurrentBalance() - transaction.getTransferAmount(5000.0);
        fromAccount.setCurrentBalance(remainingBalance);
        //Updating amount into paying account
        accountRepository.save(fromAccount);


        //Fetching account details for payee account
        Account toAccount = accountRepository.findByAccountNumber(transaction.getToAccount());
        //adding amount
        var updatedAmount = toAccount.getCurrentBalance() + transaction.getTransferAmount(5000.0);
        toAccount.setCurrentBalance(updatedAmount);
        //Updating amount into payee account

        accountRepository.save(toAccount);
        return "Successfully Sent..";
    }

    public String updateCustomer(CustomerUpdate customerUpdate, String idn) {
        Customer customer = customerRepository.findByIdentificationNumber(idn);

        ObjectMapper objectMapper = new ObjectMapper();
        // Pojo(CustomerUpdate) request is converted to json
        JsonNode jsonNode = objectMapper.convertValue(customerUpdate, JsonNode.class);
        JSONObject json = new JSONObject(jsonNode.toString());

        //DB customer is converted to beanwapper(in this line we have to the db fetch object)
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(customer);

        // Get the particular field to be updated
        JSONArray field = json.getJSONArray("field");

        //Update the particular field in Beanwarpper
        for (Object key : field) {
            Object value = json.get(key.toString());
            beanWrapper.setPropertyValue((String) key, value);
        }

        customerRepository.save(customer);
        return "Customer updated..";
    }

    public String updateAccount(AccountUpdate accountUpdate, String idn) {
        Account account = accountRepository.findByCustomer_IdentificationNumber(idn);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.convertValue(accountUpdate, JsonNode.class);
        JSONObject json = new JSONObject(jsonNode.toString());
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(account);
        JSONArray field = json.getJSONArray("field");
        for (Object key : field) {
            Object value = json.get((String) key);
            beanWrapper.setPropertyValue((String) key, value);

        }
        accountRepository.save(account);
        return "Account updated..";
    }


    public String updateContact(ContactUpdate contactUpdate, String idn) {
        Customer customer = customerRepository.findByIdentificationNumber(idn);
        Contact contact = contactRepository.findById(customer.getId()).get();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.convertValue(contactUpdate, JsonNode.class);
        JSONObject json = new JSONObject(jsonNode.toString());
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(contact);
        JSONArray field = json.getJSONArray("field");
        for (Object key : field) {
            Object value = json.get((String) key);
            beanWrapper.setPropertyValue((String) key, value);
        }
        contactRepository.save(contact);
        return "Contact updated..";
    }

    public String updateAccountType(AccountTypeUpdate accountTypeUpdate, String idn) {
        Account account = accountRepository.findByCustomer_IdentificationNumber(idn);
        AccountType accountType = accountTypeRepository.findById(account.getAccountType().getId()).get();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.convertValue(accountTypeUpdate, JsonNode.class);
        JSONObject json = new JSONObject(jsonNode.toString());
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(accountType);
        JSONArray field = json.getJSONArray("field");
        for (Object key : field) {
            Object value = json.get((String) key);
            beanWrapper.setPropertyValue((String) key, value);
        }
        accountTypeRepository.save(accountType);
        return "AccountType updated..";
    }
}
