package com.foundation.sbi.sbi_bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SbiBankApplication {
	public static void main(String[] args) {
		SpringApplication.run(SbiBankApplication.class, args);
	}

}
