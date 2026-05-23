package com.tcs.mealmatrix.service;

import com.tcs.mealmatrix.config.AdminInitializer;
import com.tcs.mealmatrix.dto.AuthResponse;
import com.tcs.mealmatrix.dto.LoginRequest;
import com.tcs.mealmatrix.dto.SignupRequest;
import com.tcs.mealmatrix.constant.Role;
import com.tcs.mealmatrix.model.AppUser;
import com.tcs.mealmatrix.repository.UserRepository;
import com.tcs.mealmatrix.security.CustomUserDetailsService;
import com.tcs.mealmatrix.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final AdminInitializer adminInitializer;

    public String signup(SignupRequest signupRequest) {
        if(adminInitializer.isAdminEmail(signupRequest.getEmail())) {
            throw new IllegalArgumentException("Email address reserved for ADMIN");
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        AppUser appUser = AppUser.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .phone(signupRequest.getPhone())
                .address(signupRequest.getAddress())
                .role(Role.ROLE_USER)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(appUser);
        return "Doctor Signup Successful";
    }

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()
        ));
        System.out.println("admin login service1");
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getEmail());
        String token = jwtService.generateToken(userDetails);
        System.out.println("admin login service1");
        return new AuthResponse(token);
    }
}
