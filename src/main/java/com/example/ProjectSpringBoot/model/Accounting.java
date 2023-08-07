package com.example.ProjectSpringBoot.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table
public class Accounting {

    @Id
    @GeneratedValue(generator = "unique-id-generator")
    @GenericGenerator(name = "unique-id-generator", strategy = "com.example.ProjectSpringBoot.Services.UniqueIdGenerator")
    
    private long accountId;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private String accountingPeriod;

    @ElementCollection
    private List<String> financialStatements;
    private Boolean needBalanceSheet;
    private Boolean needIncomeStatement;
    private Boolean needcashFlow;
    private Boolean needChangeInEquity;

    private String accountingStandard;
    private String businessNature;
    private String previousFinancialStatement;

    private String approvalStatus;
    private String contractStatus;
    private String engagementDate;

    @ManyToOne
    private Client client;
}
