package com.example.ProjectSpringBoot.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ProjectSpringBoot.model.Consultancy;

public interface ConsultantRepository extends JpaRepository<Consultancy,Long>{

    @Query(value = "SELECT * FROM consultancy c where c.client_client_id = ?1", nativeQuery=true)
    List<Consultancy> findByClientId(long clientId);
    
}





















