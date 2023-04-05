package com.foundation.sbi.sbi_bank.service;

import com.foundation.sbi.sbi_bank.entity.Account;
import com.foundation.sbi.sbi_bank.entity.AccountType;
import com.foundation.sbi.sbi_bank.entity.Contact;
import com.foundation.sbi.sbi_bank.entity.Customer;
import com.foundation.sbi.sbi_bank.exception.ResourceNotFoundException;
import com.foundation.sbi.sbi_bank.model.*;
import com.foundation.sbi.sbi_bank.repository.AccountRepository;
import com.foundation.sbi.sbi_bank.repository.AccountTypeRepository;
import com.foundation.sbi.sbi_bank.repository.ContactRepository;
import com.foundation.sbi.sbi_bank.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    AccountRepository accountRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    ContactRepository contactRepository;
    @Mock
    AccountTypeRepository accountTypeRepository;
    @Mock
    CustomerDetails customerDetails;
    @Mock
    Transaction transaction;
    @InjectMocks
    CustomerService customerService;

    @Test
    public void addCustomerDetails_IfCustomerIdentificationNumberIsNull() {
        Mockito.when(customerDetails.getIdentificationNumber()).thenReturn(null);
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> customerService.addCustomerDetails(customerDetails));
        Assertions.assertTrue(runtimeException.getMessage().equalsIgnoreCase("Invalid Identification number"));
    }

    @Test
    public void addCustomerDetails_IfAccountDetailsAreMandatory() {
        Mockito.when(customerDetails.getIdentificationNumber()).thenReturn("1");
        Mockito.when(customerDetails.getAccountDetails()).thenReturn(null);
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> customerService.addCustomerDetails(customerDetails));
        Assertions.assertTrue(runtimeException.getMessage().equalsIgnoreCase("Account details are mandatory...."));
    }

    @Test
    public void addCustomerDetails_IfAccountNumberIsPresentButDeleted() {
        Mockito.when(customerDetails.getIdentificationNumber()).thenReturn("1");
        AccountDetails details = new AccountDetails();
        details.setAccountType("Savings");
        details.setCurrentBalance(5000.0);
        details.setAccountNumber(2727);
        Mockito.when(customerDetails.getAccountDetails()).thenReturn(details);

        Contact contact = new Contact();
        contact.setAddress("BKSC");
        contact.setCity("Bokaro");
        contact.setState("Jharkhand");
        contact.setCountry("India");
        contact.setEmailId("akshay@gmail.com");
        contact.setPhone_No(70404032);

        Customer customer = new Customer();
        customer.setId(12);
        customer.setFirstName("Akshay");
        customer.setMiddleName("Kumar");
        customer.setLastName("Jhaa");
        customer.setIdentificationNumber("1");
        customer.setContact(contact);

        AccountType type = new AccountType();
        type.setAccountType("Savings");

        Account account = new Account();
        account.setAccountNumber(2727);
        account.setCurrentBalance(500.0);
        account.setId(13);
        account.setCustomer(customer);
        account.setAccountType(type);
        account.setDeleted(true);
        Mockito.when(customerRepository.findByIdentificationNumber("1")).thenReturn(customer);
        Mockito.when(accountRepository.findByCustomer_IdentificationNumber("1")).thenReturn(List.of(account));
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> customerService.addCustomerDetails(customerDetails));
        runtimeException.getMessage().equalsIgnoreCase("Account number is already present but deleted :");
    }

    @Test
    public void addCustomerDetails_IfAccountNumberIsAlreadyPresent() {

        Mockito.when(customerDetails.getIdentificationNumber()).thenReturn("1");
        AccountDetails details = new AccountDetails();
        details.setAccountType("Savings");
        details.setCurrentBalance(5000.0);
        details.setAccountNumber(2727);
        Mockito.when(customerDetails.getAccountDetails()).thenReturn(details);

        Contact contact = new Contact();
        contact.setAddress("BKSC");
        contact.setCity("Bokaro");
        contact.setState("Jharkhand");
        contact.setCountry("India");
        contact.setEmailId("akshay@gmail.com");
        contact.setPhone_No(70404032);

        Customer customer = new Customer();
        customer.setId(12);
        customer.setFirstName("Akshay");
        customer.setMiddleName("Kumar");
        customer.setLastName("Jha");
        customer.setIdentificationNumber("1");
        customer.setContact(contact);

        AccountType type = new AccountType();
        type.setAccountType("Savings");

        Account account = new Account();
        account.setAccountNumber(2727);
        account.setCurrentBalance(500.0);
        account.setId(13);
        account.setCustomer(customer);
        account.setAccountType(type);
        account.setDeleted(false);
        Mockito.when(customerRepository.findByIdentificationNumber("1")).thenReturn(customer);
        Mockito.when(accountRepository.findByCustomer_IdentificationNumber("1")).thenReturn(List.of(account));
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> customerService.addCustomerDetails(customerDetails));
        runtimeException.getMessage().equalsIgnoreCase("Account number is already present :");
    }

    @Test
    public void addCustomerDetails_IfAccountTypeIsAlreadyPresent() {
        Mockito.when(customerDetails.getIdentificationNumber()).thenReturn("1");
        AccountDetails details = new AccountDetails();
        details.setAccountType("Savings");
        details.setCurrentBalance(5000.0);
        details.setAccountNumber(2727);
        Mockito.when(customerDetails.getAccountDetails()).thenReturn(details);

        Contact contact = new Contact();
        contact.setAddress("BKSC");
        contact.setCity("Bokaro");
        contact.setState("Jharkhand");
        contact.setCountry("India");
        contact.setEmailId("akshay@gmail.com");
        contact.setPhone_No(70404032);

        Customer customer = new Customer();
        customer.setId(12);
        customer.setFirstName("Akshay");
        customer.setMiddleName("Kumar");
        customer.setLastName("Jha");
        customer.setIdentificationNumber("1");
        customer.setContact(contact);

        AccountType type = new AccountType();
        type.setAccountType("Savings");

        Account account = new Account();
        account.setAccountNumber(2728);
        account.setCurrentBalance(500.0);
        account.setId(13);
        account.setCustomer(customer);
        account.setAccountType(type);
        account.setDeleted(false);
        Mockito.when(customerRepository.findByIdentificationNumber("1")).thenReturn(customer);
        Mockito.when(accountRepository.findByCustomer_IdentificationNumber("1")).thenReturn(List.of(account));
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> customerService.addCustomerDetails(customerDetails));
        runtimeException.getMessage().equalsIgnoreCase("Account type is already present :");
    }

    @Test
    public void addCustomerDetails_AccountDetailsAddedSucessfully() {
        Mockito.when(customerDetails.getIdentificationNumber()).thenReturn("1");
        AccountDetails details = new AccountDetails();
        details.setAccountType("Savings");
        details.setCurrentBalance(5000.0);
        details.setAccountNumber(2727);
        Mockito.when(customerDetails.getAccountDetails()).thenReturn(details);

        ContactDetails details1 = new ContactDetails();
        details1.setPhone_No(12345667);
        details1.setAddress("BKSC");
        details1.setCity("Bokaro");
        details1.setEmailId("akshay@gmail.com");
        details1.setState("Jharkhand");
        details1.setCountry("India");
        Mockito.when(customerDetails.getContactDetails()).thenReturn(details1);


        Contact contact = new Contact();
        contact.setAddress("BKSC");
        contact.setCity("Bokaro");
        contact.setState("Jharkhand");
        contact.setCountry("India");
        contact.setEmailId("akshay@gmail.com");
        contact.setPhone_No(70404032);

        Customer customer = new Customer();
        customer.setId(12);
        customer.setFirstName("Akshay");
        customer.setMiddleName("Kumar");
        customer.setLastName("Jha");
        customer.setIdentificationNumber("1");
        customer.setContact(contact);

        AccountType type = new AccountType();
        type.setAccountType("Savings");

        Account account = new Account();
        account.setAccountNumber(2728);
        account.setCurrentBalance(500.0);
        account.setId(13);
        account.setCustomer(customer);
        account.setAccountType(type);
        account.setDeleted(false);
        Mockito.when(customerRepository.findByIdentificationNumber("1")).thenReturn(null);
        Mockito.when(accountRepository.findByCustomer_IdentificationNumber("1")).thenReturn(List.of(account));
        Assertions.assertEquals("Account Details added successfully", customerService.addCustomerDetails(customerDetails));
    }

    @Test
    public void deleteByAccountNumber() {
        Account account = new Account();
        account.setAccountNumber(2727);
        account.setCurrentBalance(500.0);
        Mockito.when(accountRepository.findByAccountNumber(2727)).thenReturn(account);
        Assertions.assertEquals("Data is successfully deleted", customerService.deleteByAccountNumber(2727));
    }

    @Test
    public void transfer_InsufficientFund() {
        Account account = new Account();
        account.setAccountNumber(1234);
        account.setCurrentBalance(500.0);
        Mockito.when(transaction.getFromAccount()).thenReturn(1);
        Mockito.when(accountRepository.findByAccountNumber(1)).thenReturn(account);

        Mockito.when(transaction.getTransferAmount()).thenReturn(2000);
        RuntimeException insufficientFund = Assertions.assertThrows(RuntimeException.class, () -> customerService.transfer(transaction));
        Assertions.assertTrue(insufficientFund.getMessage().equalsIgnoreCase("Insufficient fund"));

    }

    @Test
    public void transfer_AmountSentSuccessfully() {
        Account account = new Account();
        account.setCurrentBalance(500.0);
        account.setAccountNumber(123);
        Mockito.when(transaction.getFromAccount()).thenReturn(1);
        Mockito.when(accountRepository.findByAccountNumber(1)).thenReturn(account);

        Mockito.when(transaction.getTransferAmount()).thenReturn(500);
        Mockito.when(transaction.getToAccount()).thenReturn(1);

        Assertions.assertEquals("Amount Sent Successfully..", customerService.transfer(transaction));
    }

    @Test
    public void getCustomerDetails() {

        Account account = new Account();
        account.setAccountNumber(12345);
        account.setCurrentBalance(1000.0);

        AccountType accountType = new AccountType();
        accountType.setAccountType("Savings");
        account.setAccountType(accountType);

        Customer customer = new Customer();
        customer.setFirstName("Akshay");
        customer.setMiddleName("kumar");
        customer.setLastName("Jha");
        customer.setIdentificationNumber("bvhj");

        Contact contact = new Contact();
        contact.setEmailId("ak@gmail.com");
        contact.setPhone_No(123456789);
        contact.setCity("Pune");
        contact.setAddress("Marunji");
        contact.setState("Maharastra");
        contact.setCountry("India");
        customer.setContact(contact);

        account.setCustomer(customer);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        Mockito.when(accountRepository.findByCustomer_IdentificationNumberAndIsDeletedFalse("bvhj")).thenReturn(List.of(account));
        Assertions.assertNotNull(customerService.getCustomerDetails("bvhj"));
    }

    @Test
    public void getCustomerDetails_IfNoDataFound() {

        Account account = new Account();
        account.setAccountNumber(12345);
        account.setCurrentBalance(1000.0);

        AccountType accountType = new AccountType();
        accountType.setAccountType("Savings");
        account.setAccountType(accountType);

        Customer customer = new Customer();
        customer.setFirstName("Akshay");
        customer.setMiddleName("kumar");
        customer.setLastName("Jha");
        customer.setIdentificationNumber("bvhj");

        Contact contact = new Contact();
        contact.setEmailId("ak@gmail.com");
        contact.setPhone_No(123456789);
        contact.setCity("Pune");
        contact.setAddress("Marunji");
        contact.setState("Maharastra");
        contact.setCountry("India");
        customer.setContact(contact);

        account.setCustomer(customer);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        Mockito.when(accountRepository.findByCustomer_IdentificationNumberAndIsDeletedFalse("bvhj")).thenReturn(new ArrayList<>());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerDetails("bvhj"));
    }

    @Test
    public void updateCustomerTest() {
        Customer customer = new Customer();
        customer.setIdentificationNumber("bvhj");
        customer.setFirstName("Akshay");
        customer.setMiddleName("Kumar");
        customer.setLastName("Jha");
        Mockito.when(customerRepository.findByIdentificationNumber("bvhj")).thenReturn(customer);
        CustomerUpdate customerUpdate = new CustomerUpdate();
        customerUpdate.setFirstName("ashish");
        customerUpdate.setField(List.of("firstName"));
        Assertions.assertEquals("Customer updated..", customerService.updateCustomer(customerUpdate, "bvhj"));
    }

    @Test
    public void updateAccountTest(){
        Account account = new Account();
        account.setId(1);
        account.setAccountNumber(123456);
        List<Account> accountList = Arrays.asList(account);
        Mockito.when(accountRepository.findByCustomer_IdentificationNumberAndIsDeletedFalse("123")).thenReturn(accountList);

        AccountUpdate accountUpdate = new AccountUpdate();
        accountUpdate.setAccountNumber(123456);
        accountUpdate.setCurrentBalance(10000);
        accountUpdate.setField(List.of("currentBalance"));
        Assertions.assertEquals("Account updated..",customerService.updateAccount(accountUpdate,"123"));
    }

    @Test
    public void updateContactTest() {
        Customer customer = new Customer();
        customer.setIdentificationNumber("123");
        Contact contact = new Contact();
        contact.setPhone_No(123456);
        contact.setEmailId("ak@gmail.com");
        contact.setAddress("Bksc");
        contact.setCity("Bokaro");
        contact.setState("Jharkhand");
        contact.setCountry("India");
        contact.setId(1);
        customer.setContact(contact);
        Mockito.when(customerRepository.findByIdentificationNumber("123")).thenReturn(customer);
        Mockito.when(contactRepository.findById(1)).thenReturn(Optional.of(contact));
        ContactUpdate contactUpdate = new ContactUpdate();
        contactUpdate.setCity("Pune");
        contactUpdate.setField(List.of("city"));
        Assertions.assertEquals("Contact updated..", customerService.updateContact(contactUpdate, "123"));
    }

    @Test
    public void updateAccountTypeTest(){
        AccountType accountType = new AccountType();
        accountType.setId(1L);
        Mockito.when(accountTypeRepository.findById(1L)).thenReturn(Optional.of(accountType));
        Account account = new Account();
        account.setId(1);
        account.setAccountNumber(123456);
        account.setAccountType(accountType);
        List<Account> accountList = Arrays.asList(account);
        Mockito.when(accountRepository.findByCustomer_IdentificationNumberAndIsDeletedFalse("123")).thenReturn(accountList);

        AccountTypeUpdate accountTypeUpdate = new AccountTypeUpdate();
        accountTypeUpdate.setAccountNumber(123456);
        accountTypeUpdate.setAccountType("SAVINGS");
        accountTypeUpdate.setField(List.of("accountType"));
        Assertions.assertEquals("AccountType updated..",customerService.updateAccountType(accountTypeUpdate,"123"));

    }

}