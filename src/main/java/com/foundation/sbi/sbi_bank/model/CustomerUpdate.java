package com.foundation.sbi.sbi_bank.model;

import java.util.List;

public class CustomerUpdate {
        private String firstName;
        private String middleName;
        private String lastName;


        private List<String> field;

        // Getters and setters for the fields
        public String getFirstName() {
            return firstName;
        }
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        public String getMiddleName() {
            return middleName;
        }
        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }
        public String getLastName() {
            return lastName;
        }
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        public List<String> getField() {
            return field;
        }
        public void setField(List<String> field) {
            this.field = field;
        }

}

