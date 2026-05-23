package com.tcs.mealmatrix.controller;

import com.tcs.mealmatrix.dto.AuthResponse;
import com.tcs.mealmatrix.dto.LoginRequest;
import com.tcs.mealmatrix.dto.SignupRequest;
import com.tcs.mealmatrix.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest signupRequest) {
        String message = authService.signup(signupRequest);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("Admin login controller");
        AuthResponse authResponse = authService.login(loginRequest);
        System.out.println("Admin login controller");
        return ResponseEntity.ok(authResponse);
    }
}

