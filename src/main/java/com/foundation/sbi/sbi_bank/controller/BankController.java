package com.foundation.sbi.sbi_bank.controller;

import com.foundation.sbi.sbi_bank.model.CustomerDetails;
import com.foundation.sbi.sbi_bank.model.Transaction;
import com.foundation.sbi.sbi_bank.service.CustomerService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/customer")
public class BankController {
   private CustomerService customerService;
        public BankController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping("/customer-details")
    public String addCustomerDetails(@RequestBody CustomerDetails customerDetails){
        return customerService.addCustomerDetails(customerDetails);
    }
    @GetMapping("/{idn}")
    public Object getCustomerDetails(@PathVariable String idn) {
        return customerService.getCustomerDetails(idn);
    }

    @DeleteMapping("/{accountNumber}")
    public String deleteByAccountNumber(@PathVariable int accountNumber){
            return customerService.deleteByAccountNumber(accountNumber);
    }
    @PostMapping("/transfer")
    public void transfer(@RequestBody Transaction transaction) {
        customerService.transfer(transaction);
    }

}
