package com.example.ProjectSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ProjectSpringBoot.model.Admin;

public interface AdminRepository extends JpaRepository<Admin,Long>{
    
}
