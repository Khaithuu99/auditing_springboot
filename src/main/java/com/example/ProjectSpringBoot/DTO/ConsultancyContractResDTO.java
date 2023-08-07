package com.example.ProjectSpringBoot.DTO;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class ConsultancyContractResDTO extends ContractResponseDTO {
// special from client related to consultancy
    private String firmName;
    private String firmType;
    private String firmEmail;
    private String firmAddress;
    private String firmPhone;
 
    // special from consultancy

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
