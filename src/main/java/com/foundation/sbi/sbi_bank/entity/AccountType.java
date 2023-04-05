package com.foundation.sbi.sbi_bank.entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Setter
@Getter
@Table(name = "account_type")
public class AccountType {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;
    @Column(name="type")
    private String accountType;
    @Column(name="created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;
    @Column(name="updated_date")
    @UpdateTimestamp
    private LocalDateTime updatedDate;
    @Column(name="is_deleted")
    private boolean isDeleted;

}
