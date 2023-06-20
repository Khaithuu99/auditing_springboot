package com.example.ProjectSpringBoot.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table
public class Consultancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int consultancyId;
    private String FirmName;
    private String FirmType;
    private String Place;
    private Date FinancialYear;
    private String consultancyType;

    @ManyToOne
    private Client client;

}
