package com.example.ProjectSpringBoot.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ProjectSpringBoot.model.Audit;
import com.example.ProjectSpringBoot.model.Consultancy;

public interface AuditRepository extends JpaRepository<Audit,Long>{

    @Query(value = "SELECT * FROM audit a where a.client_client_id = ?1", nativeQuery=true)
    List<Audit> findByClientId(long clientId);
    
}
