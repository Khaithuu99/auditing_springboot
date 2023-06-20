package com.example.ProjectSpringBoot.DTO;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class RegistrationRequestDTO {
    private long id;
    private String name;
    private String userName;
    private String email;
    private String address;
    private String phone;
    private String password;
    
    
}
