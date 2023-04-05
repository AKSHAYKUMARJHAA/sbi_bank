package com.foundation.sbi.sbi_bank.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class ContactUpdate {
        private String emailId;
        private int phoneNo;
        private String address;
        private String city;
        private String state;
        private String country;
        private List<String> field;

}
