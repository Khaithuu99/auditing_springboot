package com.example.ProjectSpringBoot.DTO;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class AuditRequestDTO {
    private long clientId;

    private String FirmName;
    private String FirmType;
    private String Place;
    private Date FinancialYear;
    private String AreaAudit;
}
