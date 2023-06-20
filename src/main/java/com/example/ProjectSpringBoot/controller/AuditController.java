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
        auditResponseDTO.setFirstName(audit.getClient().getFirstName());
        auditResponseDTO.setLastName(audit.getClient().getLastName());
        auditResponseDTO.setEmail(audit.getClient().getEmail());
        auditResponseDTO.setAddress(audit.getClient().getAddress());
        auditResponseDTO.setPhone(audit.getClient().getPhone());
        auditResponseDTO.setFirmName(audit.getFirmName());
        auditResponseDTO.setFirmType(audit.getFirmType());
        auditResponseDTO.setPlace(audit.getPlace());
        auditResponseDTO.setFinancialYear(audit.getFinancialYear());
        auditResponseDTO.setAreaAudit(audit.getAreaAudit());

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
        return auditRepository.save(audit);
    }

       // update audit
       @PutMapping("/audit/{id}")
    public ResponseEntity<?> updateAudit(@PathVariable("id") int id, @RequestBody Audit audit) {
        Audit updateAudit = auditRepository.findById(id)
        .orElseThrow(() ->new ResourceNotFoundException("audit not found"));
      
            updateAudit.setFirmName(audit.getFirmName());
            updateAudit.setFirmType(audit.getFirmType());
            updateAudit.setPlace(audit.getPlace());
            updateAudit.setFinancialYear(audit.getFinancialYear());
            updateAudit.setAreaAudit(audit.getAreaAudit());
            auditRepository.save(audit);
        
            Audit audit2 = auditRepository.save(updateAudit);

            return ResponseEntity.ok(audit2);
    }

    //get  by ID
    @GetMapping("/audit/{id}")
    public ResponseEntity<Audit> getAuditById(@PathVariable Integer id) {
        Audit audit = auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Id"));
        return ResponseEntity.ok(audit);
    }

    
    //delete audit
    @DeleteMapping("/audit/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteAudit(@PathVariable("id") Integer id) {
        Audit findAudit = auditRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Audit not found"));
        auditRepository.delete(findAudit);

        Map<String,Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
 
    }
    


}