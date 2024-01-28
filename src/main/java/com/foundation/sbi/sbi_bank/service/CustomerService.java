package com.foundation.sbi.sbi_bank.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foundation.sbi.sbi_bank.entity.Account;
import com.foundation.sbi.sbi_bank.entity.AccountType;
import com.foundation.sbi.sbi_bank.entity.Contact;
import com.foundation.sbi.sbi_bank.entity.Customer;
import com.foundation.sbi.sbi_bank.exception.InsufficientFundsException;
import com.foundation.sbi.sbi_bank.exception.ResourceNotFoundException;
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        Customer customer = customerRepository.findByIdentificationNumber(customerDetails.getIdentificationNumber());
        List<Account> accounts = accountRepository.findByCustomer_IdentificationNumber(customerDetails.getIdentificationNumber());
        if (customer != null && !accounts.isEmpty()) {
            for (Account account : accounts) {
                if (account.getAccountNumber() == customerDetails.getAccountDetails().getAccountNumber()) {
                    if (account.isDeleted()) {
                        throw new RuntimeException("Account number is already present but deleted : " + customerDetails.getAccountDetails().getAccountNumber());
                    }
                    throw new RuntimeException("Account number is already present : " + customerDetails.getAccountDetails().getAccountNumber());
                }
                if (account.getAccountType().getAccountType().equalsIgnoreCase(customerDetails.getAccountDetails().getAccountType()) && !account.isDeleted()) {
                    throw new RuntimeException("Account type is already present : " + customerDetails.getAccountDetails().getAccountType());
                }
            }
        }
        var accountDataToSave = setAccountDataToSave(customerDetails, customer);
        accountRepository.save(accountDataToSave);
        return "Account Details added successfully";
    }

    private Account setAccountDataToSave(CustomerDetails customerDetails, Customer customerFromDb) {
        Account account = new Account();
        account.setAccountNumber(customerDetails.getAccountDetails().getAccountNumber());
        account.setCurrentBalance(customerDetails.getAccountDetails().getCurrentBalance());

        if (customerFromDb == null) {
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
        } else {
            account.setCustomer(customerFromDb);
        }

        AccountType accountType = new AccountType();
        accountType.setAccountType(customerDetails.getAccountDetails().getAccountType());
        account.setAccountType(accountType);
        return account;

    }

    private void validateRequestData(CustomerDetails customerDetails) {
        log.info("method call validateRequestData....");
        if (customerDetails.getIdentificationNumber() == null) {
            log.info("Invalid Identification number");
            throw new RuntimeException("Invalid Identification number");
        }
        if (customerDetails.getAccountDetails() == null) {
            log.info("Account details are mandatory....");
            throw new RuntimeException("Account details are mandatory....");
        }
    }

    public List<CustomerDetails> getCustomerDetails(String identificationNumber) {
        List<CustomerDetails> customerDetailsList = new ArrayList<>();
        List<Account> accountList = accountRepository.findByCustomer_IdentificationNumberAndIsDeletedFalse(identificationNumber);
        if (!accountList.isEmpty()) {
            for (Account account : accountList) {
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
                customerDetailsList.add(customerDetails);
            }
            return customerDetailsList;
        } else {
            throw new ResourceNotFoundException("No data found");
        }
    }

    public String deleteByAccountNumber(int accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        account.setDeleted(true);
        accountRepository.save(account);
        return "Data is successfully deleted";
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = InsufficientFundsException.class, isolation = Isolation.READ_COMMITTED)
    public String transfer(Transaction transaction) {
        try {
            // Fetching account details for paying account
            Account fromAccount = accountRepository.findByAccountNumber(transaction.getFromAccount());

            if (fromAccount.getCurrentBalance() < transaction.getTransferAmount()) {
                throw new InsufficientFundsException("Insufficient Fund");
            }
            // Deducting amount
            var remainingBalance = fromAccount.getCurrentBalance() - transaction.getTransferAmount();
            fromAccount.setCurrentBalance(remainingBalance);

            // Updating amount into paying account
            accountRepository.save(fromAccount);

            // Fetching account details for payee account
            Account toAccount = accountRepository.findByAccountNumber(transaction.getToAccount());

            // Adding amount
            var updatedAmount = toAccount.getCurrentBalance() + transaction.getTransferAmount();
            toAccount.setCurrentBalance(updatedAmount);

            // Updating amount into payee account
            accountRepository.save(toAccount);

            return "Amount Sent Successfully..";
        } catch (InsufficientFundsException e) {
            // Log the error
            throw new InsufficientFundsException("Failed to transfer funds: " + e.getMessage());
        }
    }



    //takes the input customerUpdate object, converts it to a JSON object,
    // and then updates the customer object in the repository with the new values provided in the JSON object.
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
        for (int i = 0; i < field.length(); i++) {
            Object key = field.get(i);
            Object value = json.get(key.toString());
            beanWrapper.setPropertyValue((String) key, value);
        }

        customerRepository.save(customer);
        return "Customer updated..";
    }

    public String updateAccount(AccountUpdate accountUpdate, String idn) {
        List<Account> accountList = accountRepository.findByCustomer_IdentificationNumberAndIsDeletedFalse(idn);
        Account account = getAccount(accountUpdate.getAccountNumber(), accountList);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.convertValue(accountUpdate, JsonNode.class);
        JSONObject json = new JSONObject(jsonNode.toString());
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(account);
        JSONArray field = json.getJSONArray("field");
        for (int i = 0; i < field.length(); i++) {
            Object key = field.get(i);
            Object value = json.get(key.toString());
            beanWrapper.setPropertyValue((String) key, value);
        }
        accountRepository.save(account);
        return "Account updated..";
    }

    private static Account getAccount(int accountNumber, List<Account> accountList) {
        for (Account acc : accountList) {
            if (acc.getAccountNumber() == accountNumber) {
                return acc;
            }
        }
        throw new ResourceNotFoundException("Account number not found.");
    }


    public String updateContact(ContactUpdate contactUpdate, String idn) {
        Customer customer = customerRepository.findByIdentificationNumber(idn);
        Contact contact = contactRepository.findById(customer.getContact().getId()).get();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.convertValue(contactUpdate, JsonNode.class);
        JSONObject json = new JSONObject(jsonNode.toString());
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(contact);
        JSONArray field = json.getJSONArray("field");
        for (int i = 0; i < field.length(); i++) {
            Object key = field.get(i);
            Object value = json.get(key.toString());
            beanWrapper.setPropertyValue((String) key, value);
        }
        contactRepository.save(contact);
        return "Contact updated..";
    }

    public String updateAccountType(AccountTypeUpdate accountTypeUpdate, String idn) {
        List<Account> accountList = accountRepository.findByCustomer_IdentificationNumberAndIsDeletedFalse(idn);
        Account account = getAccount(accountTypeUpdate.getAccountNumber(), accountList);
        AccountType accountType = accountTypeRepository.findById(account.getAccountType().getId()).get();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.convertValue(accountTypeUpdate, JsonNode.class);
        JSONObject json = new JSONObject(jsonNode.toString());
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(accountType);
        JSONArray field = json.getJSONArray("field");
        for (int i = 0; i < field.length(); i++) {
            Object key = field.get(i);
            Object value = json.get(key.toString());
            beanWrapper.setPropertyValue((String) key, value);
        }
        accountTypeRepository.save(accountType);
        return "AccountType updated..";
    }
}
