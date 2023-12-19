package com.example.webappovcharenkolab5.dto;

import lombok.Data;

@Data
public class RegistrationUserDto {

    private String username;

    private String password;

    private String confirmPassword;
}
