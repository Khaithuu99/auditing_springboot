package com.example.ProjectSpringBoot.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long contractId;

    private String contractTitle;
    private String contractDescription;
    private String contractType;

    // Other contract details
    private LocalDate startDate;
    private LocalDate endDate;
    private String termsAndConditions;
    private String pricingDetails;
    private String additionalNotes;

    @OneToOne
    @JoinColumn(unique = true)
    private Audit audit;

    @OneToOne
    @JoinColumn(unique = true)

    private Accounting accounting;

    @OneToOne
    @JoinColumn(unique = true)

    private Consultancy consultancy;
}
