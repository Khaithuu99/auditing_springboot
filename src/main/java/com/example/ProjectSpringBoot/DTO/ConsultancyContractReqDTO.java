package com.example.ProjectSpringBoot.DTO;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class ConsultancyContractReqDTO {
     private long contractId;

    private String contractTitle;
    private String contractDescription;
    private String contractType;

    // Other contract details
    private LocalDate startDate;
    private LocalDate endDate;
    private String termsAndConditions;
    private String pricingDetails;
    private String additionalNotes;

    private long consultancyId;

}
