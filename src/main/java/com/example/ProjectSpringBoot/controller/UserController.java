package com.example.ProjectSpringBoot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.example.ProjectSpringBoot.exception.ResourceNotFoundException;
import com.example.ProjectSpringBoot.model.Users;
import com.example.ProjectSpringBoot.repository.UsersRepository;


@CrossOrigin
@RestController
@RequestMapping("api/")
public class UserController {
    @Autowired
    private  UsersRepository usersRepository;

    @GetMapping("/users")
    public List<Users> getAllAudits() {
        return usersRepository.findAll();
    }

    @PostMapping("/users")
    public Users addUsers(@RequestBody Users users) {
        return usersRepository.save(users);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUsers(@PathVariable("id") int id, @RequestBody Users users) {
        Users updateUsers = usersRepository.findById(id)
        .orElseThrow(() ->new ResourceNotFoundException("User not found"));

        updateUsers.setRole(users.getRole());
        updateUsers.setUserName(users.getUserName());
        updateUsers.setPassword(users.getPassword());

        usersRepository.save(users);
        
        Users users2 = usersRepository.save(updateUsers);

        return ResponseEntity.ok(users2);

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Users> getUsersById(@PathVariable int id) {
        Users users = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Id"));
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteUsers(@PathVariable("id") int id) {
        Users findUsers = usersRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Users not found"));
        usersRepository.delete(findUsers);

        Map<String,Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
 
    }
    
}
