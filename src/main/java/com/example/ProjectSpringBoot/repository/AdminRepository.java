package com.example.ProjectSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ProjectSpringBoot.model.Admin;

public interface AdminRepository extends JpaRepository<Admin,Long>{

    @Query( value = "select * from admin where email = ?1",nativeQuery = true)
    Admin getByEmail(String email);
    
}
