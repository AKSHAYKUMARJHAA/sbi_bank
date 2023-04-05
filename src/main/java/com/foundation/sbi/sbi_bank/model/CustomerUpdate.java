package com.foundation.sbi.sbi_bank.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class CustomerUpdate {
        private String firstName;
        private String middleName;
        private String lastName;
        private List<String> field;

}

