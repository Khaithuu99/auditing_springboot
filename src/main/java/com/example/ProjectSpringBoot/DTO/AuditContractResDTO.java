package com.example.ProjectSpringBoot.DTO;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class AuditContractResDTO extends ContractResponseDTO {

    // special from client related to Audit 
    private long auditId;
    private String firmName;
    private String firmType;
    private String firmEmail;
    private String firmAddress;
    private String firmPhone;

        // special from Audit 

    private LocalDate FinancialYear;
    private String AreaAudit;
    private String auditingType;
    private String auditingStandard;
    private String financialStatement;
    private String previousAudit;

    private String approvalStatus;
    private String contractStatus;
    private String engagementDate;

}
