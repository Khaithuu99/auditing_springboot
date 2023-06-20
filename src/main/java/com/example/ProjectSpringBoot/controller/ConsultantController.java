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
import com.example.ProjectSpringBoot.exception.ResourceNotFoundException;
import com.example.ProjectSpringBoot.model.Client;
import com.example.ProjectSpringBoot.model.Consultancy;
import com.example.ProjectSpringBoot.repository.ConsultantRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ConsultantController {
    
    @Autowired
    private ConsultantRepository  consultantRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    // get all consultant
    @GetMapping("/consultancy")
    public List<ConsultantResponseDTO> getAllAudit() {
       List<ConsultantResponseDTO> list = new ArrayList<>();
       for(Consultancy consultancy: consultantRepository.findAll()){
        ConsultantResponseDTO consultantResponseDTO = modelMapper.map(consultancy,ConsultantResponseDTO.class);
        consultantResponseDTO.setFirstName(consultancy.getClient().getFirstName());
        consultantResponseDTO.setLastName(consultancy.getClient().getLastName());
        consultantResponseDTO.setEmail(consultancy.getClient().getEmail());
        consultantResponseDTO.setAddress(consultancy.getClient().getAddress());
        consultantResponseDTO.setPhone(consultancy.getClient().getPhone());
        consultantResponseDTO.setFirmName(consultancy.getFirmName());
        consultantResponseDTO.setFirmType(consultancy.getFirmType());
        consultantResponseDTO.setPlace(consultancy.getPlace());
        consultantResponseDTO.setFinancialYear(consultancy.getFinancialYear());
        consultantResponseDTO.setConsultancyType(consultancy.getConsultancyType());

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
        return consultantRepository.save(consultancy);
    }
       // update
    @PutMapping("/consultancy/{id}")
    public ResponseEntity<?> updateConsultant(@PathVariable("id") int id, @RequestBody Consultancy consultant) {
        Consultancy updateConsultant = consultantRepository.findById(id)
        .orElseThrow(() ->new ResourceNotFoundException("consultant not found"));
           
            updateConsultant.setFirmName(consultant.getFirmName());
            updateConsultant.setFirmType(consultant.getFirmType());
            updateConsultant.setPlace(consultant.getPlace());
            updateConsultant.setFinancialYear(consultant.getFinancialYear());
            updateConsultant.setConsultancyType(consultant.getConsultancyType());
            consultantRepository.save(consultant);
         
            Consultancy consultant2 = consultantRepository.save(updateConsultant);

            return ResponseEntity.ok(consultant2);
    }

    @GetMapping("/consultancy/{id}")
    public ResponseEntity<Consultancy> getConsultantById(@PathVariable Integer id) {
        Consultancy consultant = consultantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Id"));
        return ResponseEntity.ok(consultant);
    }

    @DeleteMapping("/consultancy/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteAudit(@PathVariable("id") Integer id) {
        Consultancy findConsultant = consultantRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Consultant not found"));
        consultantRepository.delete(findConsultant);

        Map<String,Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
 
    }

}
