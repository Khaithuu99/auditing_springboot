package com.example.ProjectSpringBoot.DTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class AccountContractResDTO extends ContractResponseDTO{
// special from client related to accounting
    private long accountId;
    private String firmName;
    private String firmType;
    private String firmEmail;
    private String firmAddress;
    private String firmPhone;
    
    // special from accounting

    private LocalDate startingDate;
    private LocalDate endingDate;
    private String accountingPeriod;

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
  
}
