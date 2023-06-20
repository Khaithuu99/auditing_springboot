package com.example.ProjectSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ProjectSpringBoot.model.Users;

public interface UsersRepository extends JpaRepository<Users,Integer>{
    
}
