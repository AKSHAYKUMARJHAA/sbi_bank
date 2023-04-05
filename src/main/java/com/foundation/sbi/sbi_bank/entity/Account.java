package com.foundation.sbi.sbi_bank.entity;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
@Entity
@Setter
@Getter
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "account_number", nullable = false, unique = true)
    private int accountNumber;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Customer.class)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(targetEntity =AccountType.class, cascade = CascadeType.ALL)
    @JoinColumn(name="account_type_id", referencedColumnName="id")
    private AccountType accountType;

    @Column(name="current_balance",nullable = false)
    private Double currentBalance;

    @Column(name="created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name="updated_date")
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Column(name="is_deleted")
    private boolean isDeleted;
}
