package com.example.ProjectSpringBoot.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.example.ProjectSpringBoot.exception.ResourceNotFoundException;
import com.example.ProjectSpringBoot.model.Admin;
import com.example.ProjectSpringBoot.repository.AdminRepository;

@CrossOrigin
@RestController
@RequestMapping("api/")
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/admin")
    public List<Admin> getAllAudits() {
        return adminRepository.findAll();
    }

    @PostMapping("/admin")
    public Admin addAdmin(@RequestBody Admin admin) {
        String pass =admin.getPassword();
        String encodedPassword = Base64.getEncoder().encodeToString(pass.getBytes());
        admin.setPassword(encodedPassword);
        return adminRepository.save(admin);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable("id") long id, @RequestBody Admin admin) {
        Admin updateAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Id"));

        updateAdmin.setFirstName(admin.getFirstName());
        updateAdmin.setLastName(admin.getLastName());
        updateAdmin.setEmail(admin.getEmail());
        updateAdmin.setAddress(admin.getAddress());
        updateAdmin.setPhone(admin.getPhone());

        Admin admin2 = adminRepository.save(updateAdmin);

        return ResponseEntity.ok(admin2);

    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<Admin> getAuditById(@PathVariable long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Id"));
        return ResponseEntity.ok(admin);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteAdmin(@PathVariable("id") long id) {
        Admin findAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
        adminRepository.delete(findAdmin);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);

    }

     @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@RequestBody Admin admin){
        Admin admin1 = adminRepository.getByEmail(admin.getEmail());
        String pass = admin.getPassword();
        String encryptedPass = Base64.getEncoder().encodeToString(pass.getBytes());

        if(admin1.getPassword().equals(encryptedPass)){
            return ResponseEntity.ok(admin1);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
