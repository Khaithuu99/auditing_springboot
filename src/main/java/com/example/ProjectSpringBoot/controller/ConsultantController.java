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

import com.example.ProjectSpringBoot.DTO.ConsultantRequestDTO;
import com.example.ProjectSpringBoot.DTO.ConsultantResponseDTO;
import com.example.ProjectSpringBoot.DTO.EngagementDTO;
import com.example.ProjectSpringBoot.exception.ResourceNotFoundException;
import com.example.ProjectSpringBoot.model.Consultancy;
import com.example.ProjectSpringBoot.model.Client;
import com.example.ProjectSpringBoot.repository.ConsultantRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ConsultantController {
    
    @Autowired
    private ConsultantRepository  consultantRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/consultancy/count")
    public int countCnsultancy() {
        return consultantRepository.findAll().size();
    }
    
    // get all consultant
    @GetMapping("/consultancy")
    public List<ConsultantResponseDTO> getAllAudit() {
       List<ConsultantResponseDTO> list = new ArrayList<>();
       for(Consultancy consultancy: consultantRepository.findAll()){
        ConsultantResponseDTO consultantResponseDTO = modelMapper.map(consultancy,ConsultantResponseDTO.class);
        consultantResponseDTO.setFirmEmail(consultancy.getClient().getFirmEmail());
        consultantResponseDTO.setFirmAddress(consultancy.getClient().getFirmAddress());
        consultantResponseDTO.setFirmPhone(consultancy.getClient().getFirmPhone());
        consultantResponseDTO.setFirmName(consultancy.getClient().getFirmName());
        consultantResponseDTO.setFirmType(consultancy.getClient().getFirmType());
        consultantResponseDTO.setFinancialYear(consultancy.getFinancialYear());
        consultantResponseDTO.setConsultancyType(consultancy.getConsultancyType());
        consultantResponseDTO.setConsultancyScope(consultancy.getConsultancyScope());
        consultantResponseDTO.setConsultancyObjective(consultancy.getConsultancyObjective());
        consultantResponseDTO.setExpert(consultancy.getExpert());
        consultantResponseDTO.setBudget(consultancy.getBudget());
        consultantResponseDTO.setFeeStructure(consultancy.getFeeStructure());

            list.add(consultantResponseDTO);
       }
       return list;
    }



        // get all consultant by client id
    @GetMapping("/consultancy/client/{id}")
    public List<ConsultantResponseDTO> getAllAuditByClientId(@PathVariable long id) {
       List<ConsultantResponseDTO> list = new ArrayList<>();
       for(Consultancy consultancy: consultantRepository.findByClientId(id)){
        ConsultantResponseDTO consultantResponseDTO = modelMapper.map(consultancy,ConsultantResponseDTO.class);
        consultantResponseDTO.setFirmEmail(consultancy.getClient().getFirmEmail());
        consultantResponseDTO.setFirmAddress(consultancy.getClient().getFirmAddress());
        consultantResponseDTO.setFirmPhone(consultancy.getClient().getFirmPhone());
        consultantResponseDTO.setFirmName(consultancy.getClient().getFirmName());
        consultantResponseDTO.setFirmType(consultancy.getClient().getFirmType());
        consultantResponseDTO.setFinancialYear(consultancy.getFinancialYear());
        consultantResponseDTO.setConsultancyType(consultancy.getConsultancyType());
        consultantResponseDTO.setConsultancyScope(consultancy.getConsultancyScope());
        consultantResponseDTO.setConsultancyObjective(consultancy.getConsultancyObjective());
        consultantResponseDTO.setExpert(consultancy.getExpert());
        consultantResponseDTO.setBudget(consultancy.getBudget());
        consultantResponseDTO.setFeeStructure(consultancy.getFeeStructure());

            list.add(consultantResponseDTO);
       }
       return list;
    }



    @PostMapping("/consultancy")
    public Consultancy addConsultancy(@RequestBody ConsultantRequestDTO consultantRequestDTO) {
        Client client = new Client();

        client.setClientId(consultantRequestDTO.getClientId());

        Consultancy consultancy = modelMapper.map(consultantRequestDTO, Consultancy.class);
        consultancy.setClient(client);

         consultancy.setApprovalStatus("Not Approved");
        consultancy.setContractStatus("Not Generated");
        consultancy.setEngagementDate("Not Set");

        return consultantRepository.save(consultancy);
    }
       // update
    @PutMapping("/consultancy/{id}")
    public ResponseEntity<?> updateConsultant(@PathVariable("id") long id, @RequestBody Consultancy consultant) {
        Consultancy updateConsultant = consultantRepository.findById(id)
        .orElseThrow(() ->new ResourceNotFoundException("consultant not found"));
            updateConsultant.setFinancialYear(consultant.getFinancialYear());
            updateConsultant.setConsultancyType(consultant.getConsultancyType());
            updateConsultant.setConsultancyScope(consultant.getConsultancyScope());
            updateConsultant.setConsultancyObjective(consultant.getConsultancyObjective());
            updateConsultant.setExpert(consultant.getExpert());
            updateConsultant.setBudget(consultant.getBudget());
            updateConsultant.setFeeStructure(consultant.getFeeStructure());
         
            Consultancy consultant2 = consultantRepository.save(updateConsultant);

            return ResponseEntity.ok(consultant2);
    }

    @GetMapping("/consultancy/{id}")
    public ResponseEntity<Consultancy> getConsultantById(@PathVariable long id) {
        Consultancy consultant = consultantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Id"));
        return ResponseEntity.ok(consultant);
    }

    @DeleteMapping("/consultancy/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteAudit(@PathVariable("id") long id) {
        Consultancy findConsultant = consultantRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Consultant not found"));
        consultantRepository.delete(findConsultant);

        Map<String,Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
 
    }


    @PutMapping("/consultancy/approvalStatus/{id}")
public ResponseEntity<?> updateApprovalStatus(@PathVariable("id") Long id) {
    Consultancy updateConsultancy = consultantRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("account not found"));

    updateConsultancy.setApprovalStatus("Approved");

    Consultancy updatedConsultancy = consultantRepository.save(updateConsultancy);

    return ResponseEntity.ok(updatedConsultancy);
}

 @PutMapping("/consultancy/engagementDate/{id}")
public ResponseEntity<?> updateEngagementDate(@PathVariable("id") Long id, @RequestBody EngagementDTO engagementDTO) {
    Consultancy updateConsultancy = consultantRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("account not found"));

    updateConsultancy.setEngagementDate(engagementDTO.getEngagementDate());

    Consultancy updatedConsultancy = consultantRepository.save(updateConsultancy);

    return ResponseEntity.ok(updatedConsultancy);
}

@PutMapping("/consultancy/contractStatus/{id}")
public ResponseEntity<?> updateContractStatus(@PathVariable("id") Long id) {
    Consultancy updateConsultancy = consultantRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("account not found"));

    updateConsultancy.setContractStatus("Agreed and Submitted");

    Consultancy updatedConsultancy = consultantRepository.save(updateConsultancy);

    return ResponseEntity.ok(updatedConsultancy);
}

@PutMapping("/consultancy/contractCancel/{id}")
public ResponseEntity<?> cancelContract(@PathVariable("id") Long id) {
    Consultancy updateConsultancy = consultantRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("consultancy not found"));

    updateConsultancy.setContractStatus("Cancelled");

    return ResponseEntity.ok(consultantRepository.save(updateConsultancy));
}


}
