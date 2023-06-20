package com.example.ProjectSpringBoot.controller;

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
import com.example.ProjectSpringBoot.model.Client;

import com.example.ProjectSpringBoot.repository.ClientRepository;


@CrossOrigin
@RestController
@RequestMapping("api/")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/client")
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @PostMapping("/client")
    public Client addClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PutMapping("/client/{id}")
    public ResponseEntity<?> updateClient(@PathVariable("id") Long id, @RequestBody Client client) {
        Client updateClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Id"));

                updateClient.setFirstName(client.getFirstName());
                updateClient.setLastName(client.getLastName());
                updateClient.setEmail(client.getEmail());
                updateClient.setAddress(client.getAddress());
                updateClient.setPhone(client.getPhone());
       
        
                clientRepository.save(client);
                
                Client client2 = clientRepository.save(updateClient);
        
                return ResponseEntity.ok(client2);
        
    }
  

    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Id"));
                return ResponseEntity.ok(client);
 }

 @DeleteMapping("/client/{id}")
 public ResponseEntity<Map<String,Boolean>> deleteAdmin(@PathVariable("id") Long id) {
    Client findClient = clientRepository.findById(id)
     .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
     clientRepository.delete(findClient);

     Map<String,Boolean> response = new HashMap<>();
     response.put("Deleted", Boolean.TRUE);
     return ResponseEntity.ok(response);

 }


    @PostMapping("/client/login")
    public ResponseEntity<?> clientLogin(@RequestBody Client client){
        Client client1 = clientRepository.getByEmail(client.getEmail());
        if(client1.getPassword().equals(client.getPassword())){
            return ResponseEntity.ok(client1);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
