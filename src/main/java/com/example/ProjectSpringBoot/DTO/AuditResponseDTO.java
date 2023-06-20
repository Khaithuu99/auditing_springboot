package com.example.ProjectSpringBoot.DTO;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Setter
@Getter
public class AuditResponseDTO {
    private long clientId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private Integer auditId;
    private String FirmName;
    private String FirmType;
    private String Place;
    private Date FinancialYear;
    private String AreaAudit;
}
