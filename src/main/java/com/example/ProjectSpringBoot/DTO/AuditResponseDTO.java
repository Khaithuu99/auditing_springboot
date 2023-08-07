package com.example.ProjectSpringBoot.DTO;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Setter
@Getter
public class AuditResponseDTO {

    private long auditId;
    private String firmName;
    private String firmType;
    private String firmEmail;
    private String firmAddress;
    private String firmPhone;

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
