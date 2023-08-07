package com.example.ProjectSpringBoot.DTO;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ConsultantRequestDTO {
    
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

    private long clientId;

}
