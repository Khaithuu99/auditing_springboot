package com.example.ProjectSpringBoot.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ProjectSpringBoot.model.Accounting;
import com.example.ProjectSpringBoot.model.Audit;

public interface AccountingRepository extends JpaRepository<Accounting, Long> {
    @Query(value = "SELECT * FROM accounting a where a.client_client_id = ?1", nativeQuery=true)
    List<Accounting> findByClientId(long clientId);
    
}
