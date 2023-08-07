package com.example.ProjectSpringBoot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long clientId;
    private String firmName;
    private String firmType;
    @Column(unique = true)
    private String firmEmail;
    private String firmAddress;
    private String firmPhone;
    private String firmPassword;
}
