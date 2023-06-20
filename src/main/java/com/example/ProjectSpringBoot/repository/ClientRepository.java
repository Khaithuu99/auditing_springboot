package com.example.ProjectSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ProjectSpringBoot.model.Client;

public interface ClientRepository extends JpaRepository<Client,Long> {

     @Query( value = "select * from client where email = ?1",nativeQuery = true)
    Client getByEmail(String email);
    
    
}
