package com.example.ProjectSpringBoot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.ProjectSpringBoot.DTO.RegistrationRequestDTO;
import com.example.ProjectSpringBoot.DTO.RegistrationResponseDTO;
import com.example.ProjectSpringBoot.exception.ResourceNotFoundException;
import com.example.ProjectSpringBoot.model.Registration;
import com.example.ProjectSpringBoot.repository.RegistrationRepository;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class RegistrationController {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/registration")
    public List<RegistrationResponseDTO> getAllRegistration() {
       List<RegistrationResponseDTO> list = new ArrayList<>();
       for(Registration registration: registrationRepository.findAll()){
            RegistrationResponseDTO registrationResponseDTO = modelMapper.map(registration,RegistrationResponseDTO.class);
            registrationResponseDTO.setName(registration.getName());
            registrationResponseDTO.setUserName(registration.getUserName());
            registrationResponseDTO.setEmail(registration.getEmail());
            registrationResponseDTO.setAddress(registration.getAddress());
            registrationResponseDTO.setPhone(registration.getPhone());

            list.add(registrationResponseDTO);
       }
       return list;
    }

    @PostMapping("/registration")
    public Registration addRegistration(@RequestBody RegistrationRequestDTO registrationRequestDTO) {

        Registration registration = modelMapper.map(registrationRequestDTO, Registration.class);
 
        return registrationRepository.save(registration);
    }

    @PutMapping("/registration/{id}")
    public ResponseEntity<?> updateRegistration(@PathVariable("id") Long id, @RequestBody Registration registration) {
    Registration updateReg = registrationRepository.findById(id)
        .orElseThrow(() ->new ResourceNotFoundException("consultant not found"));
           
            updateReg.setName(registration.getName());
            updateReg.setUserName(registration.getUserName());
            updateReg.setEmail(registration.getEmail());
            updateReg.setAddress(registration.getAddress());
            updateReg.setPhone(registration.getPhone());
            updateReg.setPassword(registration.getPassword());
            
            registrationRepository.save(registration);
         
            Registration registration2 = registrationRepository.save(updateReg);

            return ResponseEntity.ok(registration2);
    }

    @GetMapping("/registration/{id}")
    public ResponseEntity<?> getRegistrationById(@PathVariable Long id) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Id"));


        RegistrationResponseDTO registrationResponseDTO = new RegistrationResponseDTO();
                registrationResponseDTO.setName(registration.getName());
                registrationResponseDTO.setUserName(registration.getUserName());
                registrationResponseDTO.setEmail(registration.getEmail());
                registrationResponseDTO.setAddress(registration.getAddress());
                registrationResponseDTO.setPhone(registration.getPhone());
       
        
            return ResponseEntity.ok(registrationResponseDTO);
    }

    @DeleteMapping("/registration/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteRegistration(@PathVariable("id") Long id) {
        Registration findRegistration = registrationRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Audit not found"));
        registrationRepository.delete(findRegistration);

        Map<String,Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
 
    }

    @PostMapping("/registration/login")
    public ResponseEntity<?> adminlogin(@RequestBody Registration registration){
        Registration registration1 = registrationRepository.getByEmail(registration.getEmail());
        if(registration1.getPassword().equals(registration1.getPassword())){
            return ResponseEntity.ok(registration1);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }



}
