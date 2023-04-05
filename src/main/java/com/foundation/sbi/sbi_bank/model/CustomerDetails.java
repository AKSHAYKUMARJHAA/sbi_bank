package com.foundation.sbi.sbi_bank.model;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
@Getter
@Setter
public class   CustomerDetails {
    private String firstName;
    private String middleName;
    private String lastName;
    @NotNull
    private String identificationNumber;
    private AccountDetails accountDetails;
    private ContactDetails contactDetails;

}