package com.example.ProjectSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ProjectSpringBoot.model.Audit;

public interface AuditRepository extends JpaRepository<Audit,Integer>{
    
}
