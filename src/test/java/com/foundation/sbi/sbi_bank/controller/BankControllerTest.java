
package com.foundation.sbi.sbi_bank.controller;

import com.foundation.sbi.sbi_bank.model.*;
import com.foundation.sbi.sbi_bank.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@ExtendWith(MockitoExtension.class)
class BankControllerTest {

        @Mock
        private CustomerService customerService;

        @InjectMocks
        private BankController bankController;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.initMocks(this);
        }



        @Test
        void addCustomerDetails() {
                // Arrange
                CustomerDetails customerDetails = new CustomerDetails();
                Mockito.when(customerService.addCustomerDetails(Mockito.any(CustomerDetails.class))).thenReturn("success");

                // Act
                ResponseEntity<String> responseEntity = bankController.addCustomerDetails(customerDetails);

                // Assert
                Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
                Assertions.assertEquals("success", responseEntity.getBody());
        }

        @Test
        void deleteByAccountNumber() {
                // Arrange
                Mockito.when(customerService.deleteByAccountNumber(123456)).thenReturn("success");

                // Act
                ResponseEntity<String> responseEntity = bankController.deleteByAccountNumber(123456);

                // Assert
                Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
                Assertions.assertEquals("success", responseEntity.getBody());
        }

        @Test
        void transfer() {
                // Arrange
                Transaction transaction = new Transaction();
                Mockito.when(customerService.transfer(Mockito.any(Transaction.class))).thenReturn("success");

                // Act
                String result = bankController.transfer(transaction);

                // Assert
                Assertions.assertEquals("success", result);
        }

        @Test
        void updateCustomer() {
                // Arrange
                CustomerUpdate customerUpdate = new CustomerUpdate();
                Mockito.when(customerService.updateCustomer(Mockito.any(CustomerUpdate.class), Mockito.anyString())).thenReturn("success");

                // Act
                String result = bankController.updateCustomer(customerUpdate, "123456789");

                // Assert
                Assertions.assertEquals("success", result);
        }

        @Test
        void updateAccount() {
                // Arrange
                AccountUpdate accountUpdate = new AccountUpdate();
                Mockito.when(customerService.updateAccount(Mockito.any(AccountUpdate.class), Mockito.anyString())).thenReturn("success");

                // Act
                String result = bankController.updateAccount(accountUpdate, "123456789");

                // Assert
                Assertions.assertEquals("success", result);
        }

        @Test
        void updateContact() {
                // Arrange
                ContactUpdate contactUpdate = new ContactUpdate();
                Mockito.when(customerService.updateContact(Mockito.any(ContactUpdate.class), Mockito.anyString())).thenReturn("success");

                // Act
                String result = bankController.updateContact(contactUpdate, "123456789");

                // Assert
                Assertions.assertEquals("success", result);
        }

        @Test
        void updateAccountType() {
                // Arrange
                AccountTypeUpdate accountTypeUpdate = new AccountTypeUpdate();
                Mockito.when(customerService.updateAccountType(Mockito.any(AccountTypeUpdate.class), Mockito.anyString())).thenReturn("success");
                String result = bankController.updateAccountType(accountTypeUpdate, "123456788");
                Assertions.assertEquals("success", result);
        }
}
