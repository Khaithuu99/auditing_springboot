package com.example.ProjectSpringBoot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProjectSpringBoot.DTO.AuditRequestDTO;
import com.example.ProjectSpringBoot.DTO.AuditResponseDTO;
import com.example.ProjectSpringBoot.exception.ResourceNotFoundException;
import com.example.ProjectSpringBoot.model.Audit;
import com.example.ProjectSpringBoot.model.Audit;
import com.example.ProjectSpringBoot.model.Client;
import com.example.ProjectSpringBoot.repository.AuditRepository;



@CrossOrigin
@RestController
@RequestMapping("api/")
public class AuditController {

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private ModelMapper modelMapper;

    
    // get all audit
    @GetMapping("/audit")
    public List<AuditResponseDTO> getAllAudit() {
       List<AuditResponseDTO> list = new ArrayList<>();
       for(Audit audit: auditRepository.findAll()){
        AuditResponseDTO auditResponseDTO = modelMapper.map(audit,AuditResponseDTO.class);
        auditResponseDTO.setFirmEmail(audit.getClient().getFirmEmail());
        auditResponseDTO.setFirmAddress(audit.getClient().getFirmAddress());
        auditResponseDTO.setFirmPhone(audit.getClient().getFirmPhone());
        auditResponseDTO.setFirmName(audit.getClient().getFirmName());
        auditResponseDTO.setFirmType(audit.getClient().getFirmType());
        auditResponseDTO.setFinancialYear(audit.getFinancialYear());
        auditResponseDTO.setAreaAudit(audit.getAreaAudit());
        auditResponseDTO.setAuditingType(audit.getAuditingType());
        auditResponseDTO.setAuditingStandard(audit.getAuditingStandard());
        auditResponseDTO.setFinancialStatement(audit.getFinancialStatement());
        auditResponseDTO.setPreviousAudit(audit.getPreviousAudit());
        auditResponseDTO.setAuditId(audit.getAuditId());


            list.add(auditResponseDTO);
       }
       return list;
    }

    // get all audit by client id
    @GetMapping("/audit/client/{id}")
    public List<AuditResponseDTO> getAllAuditByClientId(@PathVariable long id) {
       List<AuditResponseDTO> list = new ArrayList<>();
       for(Audit audit: auditRepository.findByClientId(id)){
        AuditResponseDTO auditResponseDTO = modelMapper.map(audit,AuditResponseDTO.class);
        auditResponseDTO.setFirmEmail(audit.getClient().getFirmEmail());
        auditResponseDTO.setFirmAddress(audit.getClient().getFirmAddress());
        auditResponseDTO.setFirmPhone(audit.getClient().getFirmPhone());
        auditResponseDTO.setFirmName(audit.getClient().getFirmName());
        auditResponseDTO.setFirmType(audit.getClient().getFirmType());
        auditResponseDTO.setFinancialYear(audit.getFinancialYear());
        auditResponseDTO.setAreaAudit(audit.getAreaAudit());
        auditResponseDTO.setAuditingType(audit.getAuditingType());
        auditResponseDTO.setAuditingStandard(audit.getAuditingStandard());
        auditResponseDTO.setFinancialStatement(audit.getFinancialStatement());
        auditResponseDTO.setPreviousAudit(audit.getPreviousAudit());
        auditResponseDTO.setAuditId(audit.getAuditId());


            list.add(auditResponseDTO);
       }
       return list;
    }

    
    // post audit
    @PostMapping("/audit")
    public Audit addAudit(@RequestBody AuditRequestDTO auditRequestDTO) {
        Client client = new Client();

        client.setClientId(auditRequestDTO.getClientId());

        Audit audit = modelMapper.map(auditRequestDTO, Audit.class);
        audit.setClient(client);

        audit.setApprovalStatus("Not Approved");
        audit.setContractStatus("Not Generated");
        audit.setEngagementDate("Not Set");

        return auditRepository.save(audit);
    }

       // update audit
       @PutMapping("/audit/{id}")
    public ResponseEntity<?> updateAudit(@PathVariable("id") long id, @RequestBody Audit audit) {
        Audit updateAudit = auditRepository.findById(id)
        .orElseThrow(() ->new ResourceNotFoundException("audit not found"));
      
            updateAudit.setFinancialYear(audit.getFinancialYear());
            updateAudit.setAreaAudit(audit.getAreaAudit());
            updateAudit.setAuditingType(audit.getAuditingType());
            updateAudit.setAuditingStandard(audit.getAuditingStandard());
            updateAudit.setFinancialStatement(audit.getFinancialStatement());
            updateAudit.setPreviousAudit(audit.getPreviousAudit());

        
            Audit audit2 = auditRepository.save(updateAudit);

            return ResponseEntity.ok(audit2);
    }

    //get  by ID
    @GetMapping("/audit/{id}")
    public ResponseEntity<Audit> getAuditById(@PathVariable long id) {
        Audit audit = auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Id"));
        return ResponseEntity.ok(audit);
    }

    
    //delete audit
    @DeleteMapping("/audit/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteAudit(@PathVariable("id") long id) {
        Audit findAudit = auditRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Audit not found"));
        auditRepository.delete(findAudit);

        Map<String,Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
 
    }

    
    @PutMapping("/audit/approvalStatus/{id}")
public ResponseEntity<?> updateApprovalStatus(@PathVariable("id") Long id) {
    Audit updateAudit = auditRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("account not found"));

    updateAudit.setApprovalStatus("Approved");

    Audit updatedAudit = auditRepository.save(updateAudit);

    return ResponseEntity.ok(updatedAudit);
}

 @PutMapping("/audit/engagementDate/{id}")
public ResponseEntity<?> updateEngagementDate(@PathVariable("id") Long id, @RequestBody String engagementDate) {
    Audit updateAudit = auditRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("account not found"));

    updateAudit.setEngagementDate(engagementDate);

    Audit updatedAudit = auditRepository.save(updateAudit);

    return ResponseEntity.ok(updatedAudit);
}


@PutMapping("/audit/contractStatus/{id}")
public ResponseEntity<?> updateContractStatus(@PathVariable("id") Long id) {
    Audit updateAudit = auditRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("account not found"));

    updateAudit.setContractStatus("Agreed and Submitted");

    Audit updatedAudit = auditRepository.save(updateAudit);

    return ResponseEntity.ok(updatedAudit);
}


}