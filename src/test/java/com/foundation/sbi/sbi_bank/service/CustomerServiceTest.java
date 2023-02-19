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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private CustomerService customerService;

    static Account account;
    static Customer customer;
    static Contact contact;
    static AccountType accountType;
    static String identificationNumber;
    static CustomerDetails customerDetails;
    static ContactDetails contactDetails;
    static AccountDetails accountDetails;
    static Transaction transaction;

    @BeforeEach
    public void initEach() {
        identificationNumber = "BVHPJ";

        customer = new Customer();
        customer.setFirstName("Akshay");
        customer.setMiddleName("kumar");
        customer.setLastName("Jha");
        customer.setIdentificationNumber(identificationNumber);

        contact = new Contact();
        contact.setEmailId("akshay@gmail.com");
        contact.setPhone_No(1234567890);
        contact.setAddress("Bokaro");
        contact.setCity("BKSC");
        contact.setState("Jharkhand");
        contact.setCountry("India");
        customer.setContact(contact);

        accountType = new AccountType();
        accountType.setAccountType("Savings");

        account = new Account();
        account.setAccountNumber(12345);
        account.setCurrentBalance(5000.0);
        account.setId(11);
        account.setCustomer(customer);
        account.setAccountType(accountType);

        contactDetails = new ContactDetails();
        contactDetails.setEmailId("akshay@gmail.com");
        contactDetails.setPhone_No(1234567890);
        contactDetails.setAddress("Bokaro");
        contactDetails.setCity("BKSC");
        contactDetails.setState("Jharkhand");
        contactDetails.setCountry("India");

        accountDetails = new AccountDetails();
        accountDetails.setAccountNumber(12345);
        accountDetails.setCurrentBalance(5000.0);
        accountDetails.setAccountType(accountType.getAccountType());

        customerDetails =  new CustomerDetails();
        customerDetails.setFirstName("Akshay");
        customerDetails.setMiddleName("kumar");
        customerDetails.setLastName("Jha");
        customerDetails.setIdentificationNumber(identificationNumber);
        customerDetails.setContactDetails(contactDetails);
        customerDetails.setAccountDetails(accountDetails);

        transaction = new Transaction();
    }
    @Test
    public void GetCustomerDetails_NonNullAccountTest() {
        Mockito.when(accountRepository.findByCustomer_IdentificationNumber(customer.getIdentificationNumber())).thenReturn(account);
        assertNotNull(customerService.getCustomerDetails(customer.getIdentificationNumber()));
    }

    @Test
    public void GetCustomerDetails_NullAccountTest() {
        Mockito.when(accountRepository.findByCustomer_IdentificationNumber(customer.getIdentificationNumber())).thenReturn(null);
        assertEquals("No data found",customerService.getCustomerDetails(customer.getIdentificationNumber()));
    }

    @Test
    public void deleteByAccountNumberTest(){
        Mockito.when(accountRepository.findByAccountNumber(account.getAccountNumber())).thenReturn(account);
        accountRepository.deleteById(account.getId());
        assertEquals("Data is successfully deleted", customerService.deleteByAccountNumber(account.getAccountNumber()));
    }
    @Test
    public void addCustomerDetailsTest(){
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
        assertNotNull("Account Details added successfully" ,customerService.addCustomerDetails(customerDetails));
    }

    @Test
    public void transferTest(){
        transaction.setFromAccount(12345);
        transaction.setToAccount(12345);
        transaction.getTransferAmount(5000.0);
        Mockito.when(accountRepository.findByAccountNumber(transaction.getFromAccount())).thenReturn(account);
        Mockito.when(accountRepository.findByAccountNumber(transaction.getFromAccount())).thenReturn(account);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        assertNotNull("Successfully Sent" ,customerService.transfer(transaction));
    }

}
