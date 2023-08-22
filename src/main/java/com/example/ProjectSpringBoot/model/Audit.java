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

import com.example.ProjectSpringBoot.DTO.EngagementDTO;

import lombok.Data;

@Data
@Entity
@Table
public class Audit {

     @Id
    @GeneratedValue(generator = "unique-id-generator")
    @GenericGenerator(name = "unique-id-generator", strategy = "com.example.ProjectSpringBoot.Services.UniqueIdGenerator")
    

    private long auditId;
    private LocalDate FinancialYear;
    private String AreaAudit;
    private String auditingType;
    private String auditingStandard;
    private String financialStatement;
    private String previousAudit;

     private String approvalStatus;
    private String contractStatus;
    private String engagementDate;

    @ManyToOne
    private Client client;

}
