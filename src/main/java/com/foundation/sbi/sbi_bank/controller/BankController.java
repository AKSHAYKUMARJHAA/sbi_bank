package com.foundation.sbi.sbi_bank.controller;

import com.foundation.sbi.sbi_bank.exception.ResourceNotFoundException;
import com.foundation.sbi.sbi_bank.model.*;
import com.foundation.sbi.sbi_bank.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/customer")
public class BankController {
   private CustomerService customerService;
    public BankController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping("/{idn}")
    public ResponseEntity<CustomerDetails> getCustomerDetails(@PathVariable String idn) throws ResourceNotFoundException {
        CustomerDetails customerDetails = customerService.getCustomerDetails(idn);
        if (customerDetails == null) {
            throw new ResourceNotFoundException("No Customer Found!!!");
        }
        return ResponseEntity.ok(customerDetails);
    }
    @PostMapping("/customer-details")
    public ResponseEntity<String> addCustomerDetails(@RequestBody CustomerDetails customerDetails) {
        String result = customerService.addCustomerDetails(customerDetails);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<String> deleteByAccountNumber(@PathVariable int accountNumber) {
        String result = customerService.deleteByAccountNumber(accountNumber);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody Transaction transaction) {
        return customerService.transfer(transaction);
    }
    @PutMapping("/update-customer/{idn}")
    public String updateCustomer(@RequestBody CustomerUpdate customerUpdate, @PathVariable String idn){
       return customerService.updateCustomer(customerUpdate, idn);
    }
    @PutMapping("/update-account/{idn}")
    public String updateAccount(@RequestBody AccountUpdate accountUpdate,@PathVariable String idn){
       return customerService.updateAccount(accountUpdate,idn);
    }

    @PutMapping("/update-contact/{idn}")
    public String updateContact(@RequestBody ContactUpdate contactUpdate,@PathVariable String idn){
       return customerService.updateContact(contactUpdate,idn);
    }

    @PutMapping("/update-accountType/{idn}")
    public String updateAccountType(@RequestBody AccountTypeUpdate accountTypeUpdate,@PathVariable String idn){
        return customerService.updateAccountType(accountTypeUpdate,idn);
    }

}
