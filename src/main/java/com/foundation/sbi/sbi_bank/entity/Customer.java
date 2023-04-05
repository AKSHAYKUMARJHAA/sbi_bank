package com.foundation.sbi.sbi_bank.entity;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@Table(name = "customer")
@Where(clause = "is_deleted = false")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = Contact.class, orphanRemoval = true)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @Column(name="first_name")
    private String firstName;

    @Column(name="middle_name")
    private String middleName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="identification_number", nullable = false, unique = true)
    private String identificationNumber;

    @Column(name="created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name="updated_date")
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Column(name="is_deleted")
    private boolean isDeleted;

}
