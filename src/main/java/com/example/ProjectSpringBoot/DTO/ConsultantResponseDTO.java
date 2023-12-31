package com.example.ProjectSpringBoot.DTO;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class ConsultantResponseDTO {

    private String firmName;
    private String firmType;
    private String firmEmail;
    private String firmAddress;
    private String firmPhone;
 
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



}
