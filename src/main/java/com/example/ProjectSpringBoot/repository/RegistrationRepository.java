package com.example.ProjectSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ProjectSpringBoot.model.Registration;

public interface RegistrationRepository extends JpaRepository<Registration,Long> {

    @Query( value = "select * from registration where email = ?1",nativeQuery = true)
    Registration getByEmail(String email);
    
}
