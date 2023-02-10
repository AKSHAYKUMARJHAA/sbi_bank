package com.foundation.sbi.sbi_bank.service;

import com.foundation.sbi.sbi_bank.entity.Account;
import com.foundation.sbi.sbi_bank.entity.AccountType;
import com.foundation.sbi.sbi_bank.entity.Contact;
import com.foundation.sbi.sbi_bank.entity.Customer;
import com.foundation.sbi.sbi_bank.model.AccountDetails;
import com.foundation.sbi.sbi_bank.model.ContactDetails;
import com.foundation.sbi.sbi_bank.model.CustomerDetails;
import com.foundation.sbi.sbi_bank.model.Transaction;
import com.foundation.sbi.sbi_bank.repository.AccountRepository;
import com.foundation.sbi.sbi_bank.repository.AccountTypeRepository;
import com.foundation.sbi.sbi_bank.repository.ContactRepository;
import com.foundation.sbi.sbi_bank.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        var accountDataToSave = setAccountDataToSave(customerDetails);
        accountRepository.save(accountDataToSave);
        return "Account Details added successfully ";
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
        accountType.setType(customerDetails.getAccountDetails().getAccountType());
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

    public Object getCustomerDetails(String identificationNumber) {
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
            accountDetails.setAccountType(account.getAccountType().getType());
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
            return "No data found";
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
        if (fromAccount.getCurrentBalance() < transaction.getTransferAmount()) {
            return "Insufficient fund";
        }
        //Deducting amount
        var remainingBalance = fromAccount.getCurrentBalance() - transaction.getTransferAmount();
        fromAccount.setCurrentBalance(remainingBalance);
        //Updating amount into paying account
        accountRepository.save(fromAccount);



        //Fetching account details for payee account
        Account toAccount = accountRepository.findByAccountNumber(transaction.getToAccount());
        //adding amount
        var updatedAmount = toAccount.getCurrentBalance() + transaction.getTransferAmount();


        toAccount.setCurrentBalance(updatedAmount);
        //Updating amount into payee account
        accountRepository.save(toAccount);
        return "Successfully Sent";
    }
}
