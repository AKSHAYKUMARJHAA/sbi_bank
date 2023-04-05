package com.foundation.sbi.sbi_bank.model;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class AccountTypeUpdate {
    private String accountType;
    @NotNull
    private int accountNumber;
    private List<String> field;
}
