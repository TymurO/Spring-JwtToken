package com.example.webappovcharenkolab5.controllers;

import com.example.webappovcharenkolab5.dto.JwtRequest;
import com.example.webappovcharenkolab5.dto.RegistrationUserDto;
import com.example.webappovcharenkolab5.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/reg")
    public ResponseEntity<?> createUser(RegistrationUserDto registrationUserDto) {
        return authService.createUser(registrationUserDto);
    }

    @PostMapping("/log/out")
    public ResponseEntity<?> logout(@CookieValue("jwt") String jwt) {
       return authService.logout(jwt);
    }

    @GetMapping("/reg")
    public String getRegFrom() {
        return "reg";
    }

    @GetMapping("/reg/username")
    public String getRegUsernameForm() {
        return "regUsername";
    }

    @GetMapping("/reg/password")
    public String getRegPasswordForm() {
        return "regPassword";
    }

    @GetMapping("/auth/error")
    public String getLogErrorForm() {
        return "loginError";
    }

    @GetMapping("/log")
    public String getLogForm() {
        return "login";
    }
}
