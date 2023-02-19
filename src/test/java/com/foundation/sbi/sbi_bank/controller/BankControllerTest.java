package com.foundation.sbi.sbi_bank.controller;

import com.foundation.sbi.sbi_bank.model.CustomerDetails;
import com.foundation.sbi.sbi_bank.model.Transaction;
import com.foundation.sbi.sbi_bank.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BankControllerTest {
        @Mock
        private CustomerService customerService;
        @InjectMocks
        private BankController bankController;
        static String identificationNumber;


        @BeforeEach
        void setUp() {
            bankController = new BankController(customerService);
            identificationNumber = "BVHPJ";
        }
        @Test
        void getCustomerDetailsTest() {

                CustomerDetails customerDetails = new CustomerDetails();
                Mockito.when(customerService.getCustomerDetails(identificationNumber)).thenReturn(customerDetails);
                ResponseEntity<CustomerDetails> responseEntity = bankController.getCustomerDetails(identificationNumber);
                assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
                assertEquals(responseEntity.getBody(), customerDetails);
        }

        @Test
        public void testGetCustomerDetailsNotFound() {
                String idn = "BVHPJ";
                Mockito.when(customerService.getCustomerDetails(idn)).thenReturn(null);
                ResponseEntity<CustomerDetails> responseEntity = bankController.getCustomerDetails(idn);
                assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        }

        @Test
        void addCustomerDetailsTest() {

                CustomerDetails customerDetails = new CustomerDetails();
                Mockito.when(customerService.addCustomerDetails(customerDetails)).thenReturn("Success");
                ResponseEntity<String> response = bankController.addCustomerDetails(customerDetails);

                assertEquals(response, "Success");
        }
        @Test
        void deleteByAccountNumberTest() {

                int accountNumber = 12345;
                Mockito.when(customerService.deleteByAccountNumber(accountNumber)).thenReturn("Success");
                ResponseEntity<String> response = bankController.deleteByAccountNumber(accountNumber);
                assertEquals(response, "Success");
        }
        @Test
        void transferTest() {

                Transaction transaction = new Transaction();
                bankController.transfer(transaction);

        }
}
