package com.example.ProjectSpringBoot.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class ConsultantResponseDTO {
    private long clientId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private String FirmName;
    private String FirmType;
    private String Place;
    private Date FinancialYear;
    private String consultancyType;

}
