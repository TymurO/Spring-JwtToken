package com.example.webappovcharenkolab5.services;

import com.example.webappovcharenkolab5.dto.JwtRequest;
import com.example.webappovcharenkolab5.dto.RegistrationUserDto;
import com.example.webappovcharenkolab5.exception.AppError;
import com.example.webappovcharenkolab5.models.User;
import com.example.webappovcharenkolab5.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDetailsService userDetailsService;

    private final JwtTokenUtils jwtTokenUtils;

    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException exception) {
//            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect login or password"), HttpStatus.UNAUTHORIZED);
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/auth/error")).build();
        }
        User user = userDetailsService.getUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Set-Cookie", "jwt=" + token + "; Max-Age=86400");
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).location(URI.create("http://localhost:8080/students/u")).build();
    }

    public ResponseEntity<?> createUser(RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
//            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Password do not match"), HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/reg/password")).build();
        }
        else if (userDetailsService.getUserByUsername(registrationUserDto.getUsername()) != null) {
//            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "User exists"), HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/reg/username")).build();
        }
        userDetailsService.registration(registrationUserDto);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/log")).build();
    }

    public ResponseEntity<?> logout(String jwt) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Set-Cookie", "jwt=" + jwt + "; Path=/; Max-Age=0");
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).location(URI.create("http://localhost:8080/log")).build();
    }
}
