package com.example.ProjectSpringBoot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ProjectSpringBoot.model.Audit;

public interface AuditRepository extends JpaRepository<Audit,Long>{

    @Query(value = "SELECT * FROM audit a where a.client_client_id = ?1", nativeQuery=true)
    List<Audit> findByClientId(long clientId);
    
}
