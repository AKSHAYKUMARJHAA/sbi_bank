package com.foundation.sbi.sbi_bank.model;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.util.List;
@Getter
@Setter
public class AccountUpdate {
        private int currentBalance;
        @NotNull
        private int accountNumber;
        private List<String> field;

}



