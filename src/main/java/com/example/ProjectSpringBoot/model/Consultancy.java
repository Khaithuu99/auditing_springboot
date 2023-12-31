package com.example.ProjectSpringBoot.model;

import java.time.LocalDate;
import java.util.UUID;

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
public class Consultancy {
    @Id
    @GeneratedValue(generator = "unique-id-generator")
    @GenericGenerator(name = "unique-id-generator", strategy = "com.example.ProjectSpringBoot.Services.UniqueIdGenerator")
    

    private long consultancyId;
    private LocalDate FinancialYear;
    private String consultancyType;
    private String consultancyScope;
    private String consultancyObjective; 
    private String expert; 
    private String budget;
    private String feeStructure;

     private String approvalStatus;
    private String contractStatus;
    private String engagementDate;

    @ManyToOne
    private Client client;

}
